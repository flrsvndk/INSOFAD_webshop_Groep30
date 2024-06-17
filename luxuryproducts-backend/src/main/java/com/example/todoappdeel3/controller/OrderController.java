package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.OrderDAO;
import com.example.todoappdeel3.repositories.UserRepository;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/orders")
public class OrderController {

    private final OrderDAO orderDAO;
    private final UserRepository userRepository;
    private final UserService userService;

    public OrderController(OrderDAO orderDAO, UserRepository userRepository, UserService userService) {
        this.orderDAO = orderDAO;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Secured({"ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<List<PlacedOrder>> getAllOrders(){
        System.out.println(userService.getUser().getRole());
        return ResponseEntity.ok(this.orderDAO.getAllOrders());
    }


    @GetMapping("/myOrders")
    public ResponseEntity<List<PlacedOrder>> getOrdersByUserPrincipal(Principal principal) {
        if (principal == null) {
            return ResponseEntity.badRequest().build();
        }
        String userEmail = principal.getName();
        CustomUser user = userRepository.findByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<PlacedOrder> orders = user.getOrders();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlacedOrder> getOrderById(@RequestHeader UUID orderId){
        return ResponseEntity.ok(this.orderDAO.getOrderById(orderId));
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        this.orderDAO.saveOrderWithProducts(orderDTO);
        return ResponseEntity.ok().body("{\"message\": \"Order created successfully\"}");
    }
    @PutMapping("processing")
    public ResponseEntity<PlacedOrder> setProcessing(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(this.orderDAO.setProcessing(orderDTO));
    }

    @PutMapping("confirmed")
    public ResponseEntity<PlacedOrder> setConfirmed(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(this.orderDAO.setConfirmed(orderDTO));
    }

    @PutMapping("out-for-delivery")
    public ResponseEntity<PlacedOrder> setOutForDelivery(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(this.orderDAO.setOutForDelivery(orderDTO));
    }

    @PutMapping("delivered")
    public ResponseEntity<PlacedOrder> setDelivered(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(this.orderDAO.setDelivered(orderDTO));
    }

}

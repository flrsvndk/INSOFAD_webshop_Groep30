package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.OrderDAO;
import com.example.todoappdeel3.dao.UserRepository;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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




    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        this.orderDAO.saveOrderWithProducts(orderDTO);
        return ResponseEntity.ok().body("{\"message\": \"Order created successfully\"}");
    }

}

package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.OrderDAO;
import com.example.todoappdeel3.dao.UserRepository;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.PlacedOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/orders")
public class OrderController {

    private final OrderDAO orderDAO;
    private final UserRepository userRepository;

    public OrderController(OrderDAO orderDAO, UserRepository userRepository) {
        this.orderDAO = orderDAO;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<PlacedOrder>> getAllOrders(){
        return ResponseEntity.ok(this.orderDAO.getAllOrders());
    }


//    @GetMapping("/myOrders")
//    public ResponseEntity<List<PlacedOrder>> getOrdersByUserPrincipal(Principal principal) {
//        if (principal == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        String userEmail = principal.getName();
//        CustomUser user = userRepository.findByEmail(userEmail);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        List<PlacedOrder> orders = user.getOrders();
////                this.orderDAO.getOrdersByUserId(user.getId());
//
//        // Voorbeeld: Stel dat je 'totalProducts' al hebt ingesteld in je OrderDAO of ergens anders
//        // Anders, hier zou je logica toevoegen om 'totalProducts' te berekenen voor elke bestelling.
//        // Bijvoorbeeld, voor elke bestelling, tel het aantal producten en stel 'totalProducts' in.
//        // Dit is een eenvoudige demonstratie die ervan uitgaat dat de totalen al berekend zijn.
//
//        return ResponseEntity.ok(orders);
//    }




    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        this.orderDAO.saveOrderWithProducts(orderDTO);
        return ResponseEntity.ok().body("{\"message\": \"Order created successfully\"}");
    }

}

package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.config.JWTUtil;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.models.Product;
import com.example.todoappdeel3.services.OrderService;
import com.example.todoappdeel3.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.hibernate.query.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class OrderDAO {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final UserService userService;

    public OrderDAO(OrderRepository orderRepository, OrderService orderService, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.userService = userService;
    }

    public List<PlacedOrder> getAllOrders(){
        return  this.orderRepository.findAll();
    }

    public PlacedOrder getOrderById(UUID orderId){
        Optional<PlacedOrder> optionalOrder = this.orderRepository.findById(orderId);

        if (optionalOrder.isPresent()){
            return optionalOrder.get();
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No Order found with that Id"
        );
    }

    @Transactional
    public UUID saveOrderWithProducts(OrderDTO orderDTO) {
        CustomUser user = userService.getUser();

        PlacedOrder placedOrder = orderService.createOrder(orderDTO, user);

        this.orderRepository.save(placedOrder);

        return placedOrder.getId();
    }
}

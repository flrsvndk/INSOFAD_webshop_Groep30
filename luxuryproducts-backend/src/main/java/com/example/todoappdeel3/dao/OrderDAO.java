package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.repositories.OrderRepository;
import com.example.todoappdeel3.services.OrderService;
import com.example.todoappdeel3.services.UserService;
import com.example.todoappdeel3.utils.StaticDetails;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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

    public PlacedOrder setProcessing(OrderDTO orderDTO) {

        // Other processing logic

        return this.updateOrderStatus(orderDTO.id, StaticDetails.ORDER_PROCESSING);
    }

    public PlacedOrder setConfirmed(OrderDTO orderDTO) {

        // Other confirmed logic

        return this.updateOrderStatus(orderDTO.id, StaticDetails.ORDER_CONFIRMED);
    }

    public PlacedOrder setOutForDelivery(OrderDTO orderDTO) {

        // Other ... logic

        return this.updateOrderStatus(orderDTO.id, StaticDetails.ORDER_OUT_FOR_DELIVERY);
    }

    public PlacedOrder setDelivered(OrderDTO orderDTO) {

        // Other delivered logic

        return this.updateOrderStatus(orderDTO.id, StaticDetails.ORDER_DELIVERED);
    }

    private PlacedOrder updateOrderStatus(UUID id, String status) {

        Optional<PlacedOrder> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        PlacedOrder order = orderOptional.get();
        order.setStatus(status);
        orderRepository.save(order);
        return order;
    }
}

package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.OrderItemDTO;
import com.example.todoappdeel3.models.OrderItem;
import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class OrderItemDAO {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderItemDAO(OrderItemRepository orderItemRepository, ProductRepository isbnRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = isbnRepository;
    }


    public OrderItem createOrderItem(OrderItemDTO orderItemDTO, PlacedOrder order){
        Optional<Product> isbnOptional = this.productRepository.findById(orderItemDTO.productId);
        if (isbnOptional.isPresent()) {
            if (orderItemDTO.quantity > 0) {
                OrderItem orderItem = new OrderItem(orderItemDTO.quantity, order, isbnOptional.get());
                this.orderItemRepository.save(orderItem);
                return orderItem;
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "no amount specified"
            );
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Isbn was not found"
        );
    }
}

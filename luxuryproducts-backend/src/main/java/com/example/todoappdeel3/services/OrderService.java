package com.example.todoappdeel3.services;

import com.example.todoappdeel3.dao.AdressDAO;
import com.example.todoappdeel3.dao.OrderDAO;
import com.example.todoappdeel3.dao.OrderItemDAO;
import com.example.todoappdeel3.dao.ProductRepository;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.dto.OrderItemDTO;
import com.example.todoappdeel3.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderItemDAO orderItemDAO;
    private final ProductRepository productRepository;
    private final AdressDAO adressDAO;

    public OrderService(OrderItemDAO orderItemDAO, ProductRepository productRepository, AdressDAO adressDAO) {
        this.orderItemDAO = orderItemDAO;
        this.productRepository = productRepository;
        this.adressDAO = adressDAO;
    }

    public PlacedOrder createOrder(OrderDTO orderDTO, CustomUser user){
        double totalOrderSum = 0.00;

        List<OrderItem> orderItems = new ArrayList<>();

        LocalDateTime orderDate = LocalDateTime.now();
        Adress adress = adressDAO.createAdress(orderDTO.adressDTO);

        PlacedOrder customerOrder = new PlacedOrder(
            0.00, orderDate, orderDTO.notes, user, adress
        );

        for (OrderItemDTO orderItemDTO : orderDTO.orderItems) {
            OrderItem orderItem = orderItemDAO.createOrderItem(orderItemDTO, customerOrder);
            orderItem.setQuantity(this.checkProductQuanity(orderItem.getProduct().getId(), orderItem.getQuantity()));
            totalOrderSum += orderItem.getProduct().getPrice() * orderItem.getQuantity();
            orderItems.add(orderItem);
        }
        customerOrder.setOrderItems(orderItems);
        customerOrder.setTotalProductsPrice(totalOrderSum);

        return customerOrder;
    }

    private int checkProductQuanity(UUID productId, int quanity) {
        Optional<Product> orderedProduct = this.productRepository.findById(productId);
        if (orderedProduct.isPresent()){
            Product product = orderedProduct.get();
            if (quanity < product.getStock()) {
                product.setStock(product.getStock() - quanity);
            } else {
                quanity = product.getStock();
                product.setStock(0);
//                product.setAvailable(false);
            }
            return quanity;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No Product found for that Id"
        );
    }
}

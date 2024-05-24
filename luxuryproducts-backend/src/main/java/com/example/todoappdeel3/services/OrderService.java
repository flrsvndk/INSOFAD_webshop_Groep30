package com.example.todoappdeel3.services;

import com.example.todoappdeel3.dao.*;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.dto.OrderItemDTO;
import com.example.todoappdeel3.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    private final OrderItemDAO orderItemDAO;
    private final AdressDAO adressDAO;
    private final ProductSpecificationTypesRepository productSpecificationTypesRepository;

    public OrderService(OrderItemDAO orderItemDAO, AdressDAO adressDAO, ProductSpecificationTypesRepository productSpecificationTypesRepository) {
        this.orderItemDAO = orderItemDAO;
        this.adressDAO = adressDAO;
        this.productSpecificationTypesRepository = productSpecificationTypesRepository;
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
            orderItem.setQuantity(this.checkProductQuanity(orderItem.getProductType().getId(), orderItem.getQuantity()));

            totalOrderSum +=  orderItem.getProductType().getPrice() * orderItem.getQuantity();
            orderItems.add(orderItem);
        }
        customerOrder.setOrderItems(orderItems);
        customerOrder.setTotalProductsPrice(totalOrderSum);

        return customerOrder;
    }

    private int checkProductQuanity(UUID productId, int quanity) {
        Optional<ProductSpecificationType> orderedProduct = this.productSpecificationTypesRepository.findById(productId);
        if (orderedProduct.isPresent()){
            ProductSpecificationType product = orderedProduct.get();
            if (quanity < product.getStock()) {
                product.setStock(product.getStock() - quanity);
            } else {
                quanity = product.getStock();
                product.setStock(0);
            }
            return quanity;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No Product found for that Id"
        );
    }
}

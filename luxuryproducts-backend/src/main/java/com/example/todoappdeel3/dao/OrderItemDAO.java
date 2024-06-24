package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.OrderItemDTO;
import com.example.todoappdeel3.models.OrderItem;
import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.models.ProductSpecificationType;
import com.example.todoappdeel3.repositories.OrderItemRepository;
import com.example.todoappdeel3.repositories.ProductSpecificationTypesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class OrderItemDAO {
    private final OrderItemRepository orderItemRepository;
    private final ProductSpecificationTypesRepository productSpecificationTypesRepository;

    public OrderItemDAO(OrderItemRepository orderItemRepository, ProductSpecificationTypesRepository productSpecificationTypesRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productSpecificationTypesRepository = productSpecificationTypesRepository;
    }

    public OrderItem createOrderItem(OrderItemDTO orderItemDTO, PlacedOrder order){
        Optional<ProductSpecificationType> typeOptional = this.productSpecificationTypesRepository.findById(orderItemDTO.typeId);
        if (typeOptional.isPresent()) {
            if (orderItemDTO.quantity > 0) {
                if(typeOptional.get().getSubSpecification() == null) {
                    OrderItem orderItem = new OrderItem(orderItemDTO.quantity, order, typeOptional.get());
//                this.orderItemRepository.save(orderItem);
                    return orderItem;
                }
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Not the correct specification specified"
                );

            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "no amount specified"
            );
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Product was not found"
        );
    }
}

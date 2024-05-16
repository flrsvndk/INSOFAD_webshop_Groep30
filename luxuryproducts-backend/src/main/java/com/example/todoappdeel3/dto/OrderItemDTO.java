package com.example.todoappdeel3.dto;

import java.util.UUID;

public class OrderItemDTO {
    public int quantity;
    public UUID productId;

    public OrderItemDTO(int quantity, UUID productId) {
        this.quantity = quantity;
        this.productId = productId;
    }
}

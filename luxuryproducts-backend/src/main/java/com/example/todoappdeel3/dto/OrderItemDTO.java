package com.example.todoappdeel3.dto;

import java.util.UUID;

public class OrderItemDTO {
    public int quantity;
    public UUID typeId;

    public OrderItemDTO(int quantity, UUID typeId) {
        this.quantity = quantity;
        this.typeId = typeId;
    }
}

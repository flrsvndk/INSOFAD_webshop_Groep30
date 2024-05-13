package com.example.todoappdeel3.dto;

public class OrderItemDTO {
    public long orderId;
    public int quanity;
    public long productId;

    public OrderItemDTO(long orderId, int quanity, long productId) {
        this.orderId = orderId;
        this.quanity = quanity;
        this.productId = productId;
    }
}

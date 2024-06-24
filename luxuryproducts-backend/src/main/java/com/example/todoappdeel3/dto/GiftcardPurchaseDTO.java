package com.example.todoappdeel3.dto;


public class GiftcardPurchaseDTO {
    public String message;
    public Number price;
    public String ownerEmail;

    public GiftcardPurchaseDTO(String message, Number price, String ownerEmail) {
        this.message = message;
        this.price = price;
        this.ownerEmail = ownerEmail;
    }
}

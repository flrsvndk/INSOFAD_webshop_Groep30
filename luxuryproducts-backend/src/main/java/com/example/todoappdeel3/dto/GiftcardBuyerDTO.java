package com.example.todoappdeel3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class GiftcardBuyerDTO {
    public long id;
    public String message;
    public Number price;
    public String ownerEmail;

    public GiftcardBuyerDTO(long id, String message, Number price, String ownerEmail, String buyerEmail) {
        this.id = id;
        this.message = message;
        this.price = price;
        this.ownerEmail = ownerEmail;
    }
}

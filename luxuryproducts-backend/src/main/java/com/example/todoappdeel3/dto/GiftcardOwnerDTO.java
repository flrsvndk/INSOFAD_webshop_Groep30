package com.example.todoappdeel3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class GiftcardOwnerDTO {
    public long id;
    public String message;
    public Number price;
    public Number value;
    public String buyerEmail;
    public String ownerEmail;


    public GiftcardOwnerDTO( long id, String message, Number price, Number value, String buyerEmail, String ownerEmail) {
        this.id = id;
        this.message = message;
        this.price = price;
        this.value = value;
        this.buyerEmail = buyerEmail;
        this.ownerEmail = ownerEmail;
    }
}

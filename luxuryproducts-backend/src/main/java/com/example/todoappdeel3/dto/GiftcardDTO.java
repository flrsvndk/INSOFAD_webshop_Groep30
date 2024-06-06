package com.example.todoappdeel3.dto;

import com.example.todoappdeel3.models.CustomUser;
import com.fasterxml.jackson.annotation.JsonAlias;

public class GiftcardDTO {
    public String message;
    public Number price;
    public Number value;
    public CustomUser buyer;
    public CustomUser owner;

    public GiftcardDTO(String message, Number price, Number value, CustomUser buyer, CustomUser owner) {
        this.message = message;
        this.price = price;
        this.value = value;
        this.buyer = buyer;
        this.owner = owner;
    }
}

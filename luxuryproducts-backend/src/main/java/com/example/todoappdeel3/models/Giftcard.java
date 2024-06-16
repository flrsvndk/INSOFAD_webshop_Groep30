package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Giftcard {
    @Id
    @GeneratedValue
    private long id;
    @Column(length = 500)
    private String message;
    private Number price;
    private Number value;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private CustomUser buyer;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private CustomUser owner;


    public Giftcard() {
    }

    public Giftcard(String message, Number price, Number value, CustomUser buyer, CustomUser owner) {
        this.message = message;
        this.price = price;
        this.value = value;
        this.buyer = buyer;
        this.owner = owner;
    }


    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public CustomUser getBuyer() {
        return buyer;
    }

    public void setBuyer(CustomUser buyer) {
        this.buyer = buyer;
    }

    public CustomUser getOwner() {
        return owner;
    }

    public void setOwner(CustomUser owner) {
        this.owner = owner;
    }
}

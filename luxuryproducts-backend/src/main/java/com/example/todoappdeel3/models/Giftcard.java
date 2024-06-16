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

     /*
    Maps the many-to-one relationship between giftcard and its buyer, jsonbackreference so that we do not get an
    infinite dependency loop in the request. Cascasdetype merge so the product is able to create a category if we
    seed the data to the database. Without the merge you get a persistence race condition.
    */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private CustomUser buyer;

    /*
   Maps the many-to-one relationship between giftcard and its owner, jsonbackreference so that we do not get an
   infinite dependency loop in the request. Cascasdetype merge so the product is able to create a category if we
   seed the data to the database. Without the merge you get a persistence race condition.
   */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private CustomUser owner;


    //needed by JPA to create the entity must be present no arg constructor
    public Giftcard() {
    }

    public Giftcard(String message, Number price, Number value, CustomUser buyer, CustomUser owner) {
        this.message = message;
        this.price = price;
        this.value = value;
        this.buyer = buyer;
        this.owner = owner;
    }

    //getters and setters are needed to map all the properties to the database by JPA, could
    //also be solved by making the properties public but gives less control over the properties.


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

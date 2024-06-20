package com.example.todoappdeel3.models;

import com.example.todoappdeel3.utils.StaticDetails;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class PlacedOrder {
    @Id
    @UuidGenerator
    private UUID id;

    private LocalDateTime orderDate;
    private String notes;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference(value = "user-placed-order")
    private CustomUser user;

    @OneToMany(mappedBy = "placedOrder")
    @JsonManagedReference(value = "placed-order-order-items")
    private List<OrderItem> orderItems;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonManagedReference(value = "adress-placed-order")
    private Adress adress;

    private String status;

    @ManyToOne
    @JsonBackReference(value = "promocode-placed-order")
    private Promocode promocode;

    private double totalPriceBeforePromocode;
    private double totalPriceAfterPromocode;


    public PlacedOrder() {
    }

    public PlacedOrder(LocalDateTime orderDate,
                       String notes,
                       CustomUser user,
                       Adress adress,
                       Promocode promocode,
                       double totalPriceBeforePromocode,
                       double totalPriceAfterPromocode) {
        this.orderDate = orderDate;
        this.notes = notes;
        this.user = user;
        this.adress = adress;
        this.status = StaticDetails.ORDER_PENDING;
        this.promocode = promocode;
        this.totalPriceBeforePromocode = totalPriceBeforePromocode;
        this.totalPriceAfterPromocode = totalPriceAfterPromocode;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Promocode getPromocode() {
        return promocode;
    }

    public void setPromocode(Promocode promocode) {
        this.promocode = promocode;
    }

    public double getTotalPriceBeforePromocode() {
        return totalPriceBeforePromocode;
    }

    public void setTotalPriceBeforePromocode(double totalPriceBeforePromocode) {
        this.totalPriceBeforePromocode = totalPriceBeforePromocode;
    }

    public double getTotalPriceAfterPromocode() {
        return totalPriceAfterPromocode;
    }

    public void setTotalPriceAfterPromocode(double totalPriceAfterPromocode) {
        this.totalPriceAfterPromocode = totalPriceAfterPromocode;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
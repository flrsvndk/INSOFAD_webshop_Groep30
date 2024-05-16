package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class PlacedOrder {
    @Id
    @UuidGenerator
    private UUID id;

    private Double totalProductsPrice;
    private LocalDateTime orderDate;
    private String notes;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private CustomUser user;

    @OneToMany(mappedBy = "placedOrder")
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonManagedReference
    private Adress adress;

    public PlacedOrder() {
    }

    public PlacedOrder(Double totalProductsPrice, LocalDateTime orderDate, String notes, CustomUser user, Adress adress) {
        this.totalProductsPrice = totalProductsPrice;
        this.orderDate = orderDate;
        this.notes = notes;
        this.user = user;
        this.adress = adress;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Double getTotalProductsPrice() {
        return totalProductsPrice;
    }

    public void setTotalProductsPrice(Double totalProductsPrice) {
        this.totalProductsPrice = totalProductsPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
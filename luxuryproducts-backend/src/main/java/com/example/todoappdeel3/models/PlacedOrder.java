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

    @Column(nullable = true)
    private String infix;

    private String name;
    private String last_name;
    private String zipcode;
    private int houseNumber;
    private String houseNumberAddition;
    private Double totalProductsPrice;
    private LocalDateTime orderDate;

    @Column(nullable = true)
    private String notes;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private CustomUser user;

//    @ManyToMany
//    @JoinTable(name = "product_order",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id"))
//    private Set<Product> products = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public PlacedOrder() {
    }

    public PlacedOrder(String infix, String name, String last_name, String zipcode, int houseNumber, String houseNumberAddition, Double totalProductsPrice, LocalDateTime orderDate, String notes, CustomUser user) {
        this.infix = infix;
        this.name = name;
        this.last_name = last_name;
        this.zipcode = zipcode;
        this.houseNumber = houseNumber;
        this.houseNumberAddition = houseNumberAddition;
        this.totalProductsPrice = totalProductsPrice;
        this.orderDate = orderDate;
        this.notes = notes;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Number getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
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

    public Double getTotalProductsPrice() {
        return totalProductsPrice;
    }

    public void setTotalProductsPrice(Double totalProductsPrice) {
        this.totalProductsPrice = totalProductsPrice;
    }

    public String getHouseNumberAddition() {
        return houseNumberAddition;
    }

    public void setHouseNumberAddition(String houseNumberAddition) {
        this.houseNumberAddition = houseNumberAddition;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
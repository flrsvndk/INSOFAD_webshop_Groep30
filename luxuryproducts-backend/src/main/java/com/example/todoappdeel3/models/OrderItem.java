package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private long id;

    private int quantity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private PlacedOrder placedOrder;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonManagedReference
    private ProductSpecificationType productType;

    public OrderItem() {
    }

    public OrderItem(int quantity, PlacedOrder placedOrder, ProductSpecificationType productType) {
        this.quantity = quantity;
        this.placedOrder = placedOrder;
        this.productType = productType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PlacedOrder getPlacedOrder() {
        return placedOrder;
    }

    public void setPlacedOrder(PlacedOrder placedOrder) {
        this.placedOrder = placedOrder;
    }

    public ProductSpecificationType getProductType() {
        return productType;
    }

    public void setProductType(ProductSpecificationType productType) {
        this.productType = productType;
    }
}

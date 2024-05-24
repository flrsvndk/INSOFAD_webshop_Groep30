package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class ProductSpecificationType {
    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private int stock;
    private String imgUrl;
    private Double price;
    private UUID productId;

    @OneToMany(mappedBy = "productType")
    @JsonBackReference
    private List<OrderItem> orderItemList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private ProductSpecification productSpecification;

    @OneToOne
    @JsonManagedReference
    private ProductSpecification subSpecification;

    public ProductSpecificationType(){}


    public ProductSpecificationType(String name, int stock, String imgUrl, Double price, UUID productId) {
        this.name = name;
        this.stock = stock;
        this.imgUrl = imgUrl;
        this.price = price;
        this.productId = productId;
    }

    public ProductSpecificationType(String name, ProductSpecification productSpecification, ProductSpecification subSpecification, UUID productId) {
        this.name = name;
        this.productSpecification = productSpecification;
        this.subSpecification = subSpecification;
        this.productId = productId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public ProductSpecification getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
    }

    public ProductSpecification getSubSpecification() {
        return subSpecification;
    }

    public void setSubSpecification(ProductSpecification subSpecification) {
        this.subSpecification = subSpecification;
    }
}

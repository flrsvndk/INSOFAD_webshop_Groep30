package com.example.todoappdeel3.dto;

import java.util.UUID;

public class TypeDTO {
    public UUID id;
    public String name;
    public Double price;
    public int stock;
    public String imgUrl;
    public ProductSpecificationsDTO subSpecification;

    public TypeDTO(String name, Double price, int stock, String imgUrl, ProductSpecificationsDTO productSpecificationsDTO) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imgUrl = imgUrl;
        this.subSpecification = productSpecificationsDTO;
    }
}

package com.example.todoappdeel3.dto;

import java.util.UUID;

public class TypeDTO {
    public UUID id;
    public String typeName;
    public Double price;
    public int stock;
    public String imgUrl;
    public ProductSpecificationsDTO subSpecification;

    public TypeDTO() {
    }

    public TypeDTO(String name, Double price, int stock, String imgUrl, ProductSpecificationsDTO productSpecificationsDTO) {
        this.typeName = name;
        this.price = price;
        this.stock = stock;
        this.imgUrl = imgUrl;
        this.subSpecification = productSpecificationsDTO;
    }
}

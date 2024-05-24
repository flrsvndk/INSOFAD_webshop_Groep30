package com.example.todoappdeel3.dto;

public class TypeDTO {
    public String name;
    public Double price;
    public int stock;
    public String imgUrl;
    public ProductSpecificationsDTO productSpecificationsDTO;

    public TypeDTO(String name, Double price, int stock, String imgUrl, ProductSpecificationsDTO productSpecificationsDTO) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imgUrl = imgUrl;
        this.productSpecificationsDTO = productSpecificationsDTO;
    }
}

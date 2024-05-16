package com.example.todoappdeel3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProductDTO {
    public String name;
    public String description;
    public Double price;
    public String imgURL;
    public int stock;
    public String groupset;
    public String material;
    public String wheels;

    public ProductDTO(String name, String description, Double price, String imgURL, String groupset, String material, String wheels, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgURL = imgURL;
        this.groupset = groupset;
        this.material = material;
        this.wheels = wheels;
        this.stock = stock;
    }
}

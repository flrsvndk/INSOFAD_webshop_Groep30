package com.example.todoappdeel3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProductDTO {
    public String name;
    public String description;
    public Number price;
    public String imgURL;
    public String groupset;
    public String material;
    public String wheels;

    public ProductDTO(String name, String description, Number price, String imgURL, String groupset, String material, String wheels) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgURL = imgURL;
        this.groupset = groupset;
        this.material = material;
        this.wheels = wheels;
    }
}

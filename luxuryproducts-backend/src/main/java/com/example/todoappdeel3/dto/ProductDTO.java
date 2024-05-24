package com.example.todoappdeel3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.UUID;

public class ProductDTO {
    public String name;
    public String description;
    public CategoryDTO categoryDTO;
    public long categoryId;
    public ProductSpecificationsDTO productSpecificationsDTO;

    public ProductDTO(String name, String description, CategoryDTO categoryDTO, long categoryId, ProductSpecificationsDTO productSpecificationsDTO) {
        this.name = name;
        this.description = description;
        this.categoryDTO = categoryDTO;
        this.categoryId = categoryId;
        this.productSpecificationsDTO = productSpecificationsDTO;
    }
}

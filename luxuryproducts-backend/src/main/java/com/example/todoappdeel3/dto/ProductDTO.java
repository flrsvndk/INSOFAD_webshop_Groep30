package com.example.todoappdeel3.dto;

import com.example.todoappdeel3.models.Category;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.UUID;

public class ProductDTO {
    public String name;
    public String description;
    public CategoryDTO category;
    public long categoryId;
    public ProductSpecificationsDTO productSpecificationsDTO;

    public ProductDTO(String name, String description, CategoryDTO category, long categoryId, ProductSpecificationsDTO productSpecificationsDTO) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.categoryId = categoryId;
        this.productSpecificationsDTO = productSpecificationsDTO;
    }
}

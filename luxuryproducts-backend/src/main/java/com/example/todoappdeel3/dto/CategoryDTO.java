package com.example.todoappdeel3.dto;

import com.example.todoappdeel3.models.Product;

import java.util.List;
import java.util.UUID;

public class CategoryDTO {
    public String name;
    public List<UUID> productIds;

    public CategoryDTO(String name) {
        this.name = name;
    }
}

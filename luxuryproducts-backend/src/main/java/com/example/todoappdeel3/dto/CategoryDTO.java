package com.example.todoappdeel3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CategoryDTO {
    public String name;

    public CategoryDTO(String name) {
        this.name = name;
    }
}

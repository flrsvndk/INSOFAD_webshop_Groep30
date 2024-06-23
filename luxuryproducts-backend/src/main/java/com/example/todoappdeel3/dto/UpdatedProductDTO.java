package com.example.todoappdeel3.dto;

import java.util.UUID;

public class UpdatedProductDTO {
    public UUID id;
    public String name;
    public String description;
    public CategoryDTO category;
    public long categoryId;
    public ProductSpecificationsDTO productSpecification;

    public UpdatedProductDTO(UUID id, String name, String description, CategoryDTO category, long categoryId, ProductSpecificationsDTO productSpecificationsDTO) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.categoryId = categoryId;
        this.productSpecification = productSpecificationsDTO;
    }
}

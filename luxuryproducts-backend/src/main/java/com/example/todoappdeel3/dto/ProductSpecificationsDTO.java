package com.example.todoappdeel3.dto;

import java.util.List;
import java.util.UUID;

public class ProductSpecificationsDTO {
    public UUID id;
    public String name;
    public List<TypeDTO> types;

    public ProductSpecificationsDTO(String name, List<TypeDTO> typesDTO) {
        this.name = name;
        this.types = typesDTO;
    }
}

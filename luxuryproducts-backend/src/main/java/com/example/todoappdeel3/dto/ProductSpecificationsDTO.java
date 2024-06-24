package com.example.todoappdeel3.dto;

import java.util.List;
import java.util.UUID;

public class ProductSpecificationsDTO {
    public UUID id;
    public String specificationName;
    public List<TypeDTO> types;

    public ProductSpecificationsDTO() {
    }

    public ProductSpecificationsDTO(String name, List<TypeDTO> typesDTO) {
        this.specificationName = name;
        this.types = typesDTO;
    }
}

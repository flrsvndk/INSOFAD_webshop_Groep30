package com.example.todoappdeel3.dto;

import java.util.List;

public class ProductSpecificationsDTO {
    public String name;
    public List<TypeDTO> typesDTO;

    public ProductSpecificationsDTO(String name, List<TypeDTO> typesDTO) {
        this.name = name;
        this.typesDTO = typesDTO;
    }
}

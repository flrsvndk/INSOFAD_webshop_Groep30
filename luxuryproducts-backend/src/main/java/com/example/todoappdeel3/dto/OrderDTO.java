package com.example.todoappdeel3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class OrderDTO{
    public String notes;
    public List<OrderItemDTO> orderItems;
    public AdressDTO adressDTO;

    public OrderDTO(String notes, List<OrderItemDTO> orderItems, AdressDTO adressDTO) {
        this.notes = notes;
        this.orderItems = orderItems;
        this.adressDTO = adressDTO;
    }
}
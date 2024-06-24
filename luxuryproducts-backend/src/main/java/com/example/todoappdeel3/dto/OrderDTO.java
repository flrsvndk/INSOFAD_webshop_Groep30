package com.example.todoappdeel3.dto;

import java.util.List;
import java.util.UUID;

public class OrderDTO{
    public String notes;
    public List<OrderItemDTO> orderItems;
    public AdressDTO adressDTO;
    public PromocodeDTO promocode;

    public UUID id;

    public OrderDTO() {
    }

    public OrderDTO(String notes, List<OrderItemDTO> orderItems, AdressDTO adressDTO, PromocodeDTO promocode) {
        this.notes = notes;
        this.orderItems = orderItems;
        this.adressDTO = adressDTO;
        this.promocode = promocode;
    }
}
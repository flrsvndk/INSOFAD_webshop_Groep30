package com.example.todoappdeel3.dto;

import java.util.List;
import java.util.UUID;

public class OrderDTO{
    public String notes;
    public List<OrderItemDTO> orderItems;
    public AdressDTO adressDTO;
    public PromocodeDTO promocode;

    public UUID id;
}
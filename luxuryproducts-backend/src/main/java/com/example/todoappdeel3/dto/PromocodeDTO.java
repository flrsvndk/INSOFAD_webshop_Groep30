package com.example.todoappdeel3.dto;

import java.util.UUID;

public class PromocodeDTO {
    public UUID id;
    public String name;
    public String description;
    public int percentageOff;
    public int maxUsages;
    public int usages;
    public double totalPriceOrders;
    public boolean available;
    public boolean dedicatedPromocode;
    public String dedicatedUserEmail;
}

package com.example.todoappdeel3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;
import java.util.UUID;

public class RetourRequestDTO {
    @JsonAlias("order_item_ids")
    public List<Long> orderItemIds;
    @JsonAlias("readon_id")
    public UUID reasonId;
    public String comment;
    @JsonAlias("order_id")
    public UUID orderId;
    public UUID id;
}
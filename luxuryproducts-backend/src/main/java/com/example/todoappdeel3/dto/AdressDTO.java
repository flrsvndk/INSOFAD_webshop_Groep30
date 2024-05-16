package com.example.todoappdeel3.dto;


public class AdressDTO {
    public String zipcode;
    public int houseNumber;
    public String houseNumberAddition;

    public AdressDTO(String zipcode, int houseNumber, String houseNumberAddition) {
        this.zipcode = zipcode;
        this.houseNumber = houseNumber;
        this.houseNumberAddition = houseNumberAddition;
    }
}

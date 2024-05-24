package com.example.todoappdeel3.dto;

public class CustomUserDTO {
    public String name;
    public String infix;
    public String lastName;
    public String email;
    public String imgUrl;
    public AdressDTO adress;

    public CustomUserDTO(String name, String infix, String lastName, String email, String imgUrl, AdressDTO adress) {
        this.name = name;
        this.infix = infix;
        this.lastName = lastName;
        this.email = email;
        this.imgUrl = imgUrl;
        this.adress = adress;
    }
}

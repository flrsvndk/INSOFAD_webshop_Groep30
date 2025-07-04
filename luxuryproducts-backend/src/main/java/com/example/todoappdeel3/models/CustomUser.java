package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity(name = "Users")
public class CustomUser {
    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private String infix;
    private String lastName;
    private String email;
    private String password;
    private String imgUrl = "https://st3.depositphotos.com/6672868/13701/v/450/depositphotos_137014128-stock-illustration-user-profile-icon.jpg";
    private String role = "USER";


    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-placed-order")
    private List<PlacedOrder> placedOrders;

    @OneToOne
    @JsonManagedReference(value = "user-adress")
    private Adress adress;

    public CustomUser() {
    }

    public CustomUser(String name, String infix, String lastName, String email, String password) {
        this.name = name;
        this.infix = infix;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public CustomUser(String name, String infix, String lastName, String email, String password, String imgUrl) {
        this.name = name;
        this.infix = infix;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.imgUrl = imgUrl;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PlacedOrder> getPlacedOrders() {
        return placedOrders;
    }

    public void setPlacedOrders(List<PlacedOrder> placedOrders) {
        this.placedOrders = placedOrders;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public List<PlacedOrder> getOrders() {
        return placedOrders;
    }

    public void setOrders(List<PlacedOrder> placedOrders) {
        this.placedOrders = placedOrders;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}


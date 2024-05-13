package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
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


    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<PlacedOrder> placedOrders;

    public CustomUser() {
    }

    public CustomUser(String name, String infix, String lastName, String email, String password) {
        this.name = name;
        this.infix = infix;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Set<PlacedOrder> getOrders() {
        return placedOrders;
    }

    public void setOrders(Set<PlacedOrder> placedOrders) {
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


package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private Number price;
    private String description;
    private String imgURL;
    private String groupset;
    private String material;
    private String wheels;



    //needed by JPA to create the entity must be present no arg constructor
    public Product() {
    }

    public Product(String name, Number price, String description, String imgURL, String groupset, String material, String wheels) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgURL = imgURL;
        this.groupset = groupset;
        this.material = material;
        this.wheels = wheels;
    }

    //getters and setters are needed to map all the properties to the database by JPA, could
    //also be solved by making the properties public but gives less control over the properties.

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getPrice() { return price; }

    public void setPrice(Number price) { this.price = price; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getGroupset() {
        return groupset;
    }

    public void setGroupset(String groupset) {
        this.groupset = groupset;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getWheels() {
        return wheels;
    }

    public void setWheels(String wheels) {
        this.wheels = wheels;
    }
}

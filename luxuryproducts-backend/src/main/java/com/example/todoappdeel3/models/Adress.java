package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class Adress {
    @Id
    @UuidGenerator
    private UUID id;

    private String zipCode;
    private int houseNumber;
    private String houseNumberAddition;

    @OneToOne
    @JsonBackReference(value = "user-adress")
    private CustomUser customUser;

    @OneToMany(mappedBy = "adress")
    @JsonBackReference(value = "adress-placed-order")
    private List<PlacedOrder> placedOrder;

    public Adress(String zipcode, int houseNumber, String houseNumberAddition) {
        this.zipCode = zipcode;
        this.houseNumber = houseNumber;
        this.houseNumberAddition = houseNumberAddition;
    }

    public Adress() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getZipcode() {
        return zipCode;
    }

    public void setZipcode(String zipcode) {
        this.zipCode = zipcode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseNumberAddition() {
        return houseNumberAddition;
    }

    public void setHouseNumberAddition(String houseNumberAddition) {
        this.houseNumberAddition = houseNumberAddition;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public List<PlacedOrder> getPlacedOrder() {
        return placedOrder;
    }

    public void setPlacedOrder(List<PlacedOrder> placedOrder) {
        this.placedOrder = placedOrder;
    }
}

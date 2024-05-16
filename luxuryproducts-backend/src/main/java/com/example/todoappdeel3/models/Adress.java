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

    private String zipcode;
    private int houseNumber;
    private String houseNumberAddition;

    @OneToOne
    @JsonBackReference
    private CustomUser customUser;

    @OneToMany(mappedBy = "adress")
    @JsonBackReference
    private List<PlacedOrder> placedOrder;

    public Adress(String zipcode, int houseNumber, String houseNumberAddition) {
        this.zipcode = zipcode;
        this.houseNumber = houseNumber;
        this.houseNumberAddition = houseNumberAddition;
    }

    public Adress() {
    }
}

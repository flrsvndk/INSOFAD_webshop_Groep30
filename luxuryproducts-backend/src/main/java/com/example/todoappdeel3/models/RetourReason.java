package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class RetourReason {

    @Id
    @GeneratedValue
    private UUID id;
    private String reason;

    public RetourReason() {
    }

    public RetourReason(String reason) {
        this.reason = reason;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
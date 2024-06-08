package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "retour_request")
public class RetourRequest {

    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private CustomUser user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private PlacedOrder order;
    @OneToMany
    @JoinTable(
            name="retour_request_products",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonAlias("retoured_products")
    private Set<OrderItem> retouredProducts;
    @JsonAlias("date_time")
    private LocalDateTime dateTime;
    private String reason;
    private String note;
    private String state;

    public RetourRequest() {
    }

    public RetourRequest(CustomUser user, PlacedOrder order, Set<OrderItem> retouredProducts, LocalDateTime dateTime, String reason, String note, String state) {
        this.user = user;
        this.order = order;
        this.retouredProducts = retouredProducts;
        this.dateTime = dateTime;
        this.reason = reason;
        this.note = note;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public PlacedOrder getOrder() {
        return order;
    }

    public void setOrder(PlacedOrder order) {
        this.order = order;
    }

    public Set<OrderItem> getRetouredProducts() {
        return retouredProducts;
    }

    public void setRetouredProducts(Set<OrderItem> retouredProducts) {
        this.retouredProducts = retouredProducts;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
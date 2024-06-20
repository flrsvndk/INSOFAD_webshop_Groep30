package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
public class Promocode {
    @Id
    @UuidGenerator
    private UUID id;
    private String name;
    private String description;
    private int percentageOff;
    private int maxUsages;
    private int usages;
    private double totalPriceOrders;
    private boolean available;
    private boolean dedicatedPromocode;
    private String dedicatedUserEmail;

    @OneToMany(mappedBy = "promocode")
    @JsonManagedReference(value = "promocode-placed-order")
    private Set<PlacedOrder> placedOrders;

    public Promocode() {
    }

    public Promocode(String name, String description, int percentageOff, int maxUsages, int usages, double totalPriceOrders, boolean available, boolean dedicatedPromocode, String dedicatedUserEmail) {
        this.name = name;
        this.description = description;
        this.percentageOff = percentageOff;
        this.maxUsages = maxUsages;
        this.usages = usages;
        this.totalPriceOrders = totalPriceOrders;
        this.available = available;
        this.dedicatedPromocode = dedicatedPromocode;
        this.dedicatedUserEmail = dedicatedUserEmail;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getUsages() {
        return usages;
    }

    public int getMaxUsages() {
        return maxUsages;
    }

    public void setUsages(int usages) {
        this.usages = usages;
    }

    public void setTotalPriceOrders(double totalPriceOrders) {
        this.totalPriceOrders = totalPriceOrders;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPercentageOff(int percentageOff) {
        this.percentageOff = percentageOff;
    }

    public void setMaxUsages(int maxUsages) {
        this.maxUsages = maxUsages;
    }

    public void setDedicatedPromocode(boolean dedicatedPromocode) {
        this.dedicatedPromocode = dedicatedPromocode;
    }

    public void setDedicatedUserEmail(String dedicatedUserEmail) {
        this.dedicatedUserEmail = dedicatedUserEmail;
    }

    public String getDescription() {
        return description;
    }

    public int getPercentageOff() {
        return percentageOff;
    }

    public double getTotalPriceOrders() {
        return totalPriceOrders;
    }

    public boolean isDedicatedPromocode() {
        return dedicatedPromocode;
    }

    public String getDedicatedUserEmail() {
        return dedicatedUserEmail;
    }

    public Set<PlacedOrder> getPlacedOrders() {
        return placedOrders;
    }

    public void setPlacedOrders(Set<PlacedOrder> placedOrders) {
        this.placedOrders = placedOrders;
    }
}

package com.example.todoappdeel3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class ProductSpecification {
    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "productSpecification")
    @JsonManagedReference
    private List<ProductSpecificationType> types;

    @OneToOne
    @JsonBackReference
    private ProductSpecificationType type;

    @OneToOne
    @JsonBackReference
    private Product product;

    public ProductSpecification() {
    }

    public ProductSpecification(String name) {
        this.name = name;
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

    public List<ProductSpecificationType> getTypes() {
        return types;
    }

    public void setTypes(List<ProductSpecificationType> types) {
        this.types = types;
    }

    public ProductSpecificationType getType() {
        return type;
    }

    public void setType(ProductSpecificationType type) {
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

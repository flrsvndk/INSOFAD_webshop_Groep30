package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.models.Product;
import com.example.todoappdeel3.models.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, UUID> {
}

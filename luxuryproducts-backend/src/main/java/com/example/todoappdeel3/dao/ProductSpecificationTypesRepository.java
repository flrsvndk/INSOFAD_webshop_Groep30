package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.models.ProductSpecificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSpecificationTypesRepository extends JpaRepository<ProductSpecificationType, UUID> {
    ProductSpecificationType findByName(String name);
}

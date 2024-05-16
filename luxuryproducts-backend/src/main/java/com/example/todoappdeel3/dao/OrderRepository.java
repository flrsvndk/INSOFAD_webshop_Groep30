package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.models.Product;
import jakarta.persistence.Entity;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<PlacedOrder, UUID> {
}

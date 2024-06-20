package com.example.todoappdeel3.repositories;

import com.example.todoappdeel3.models.Promocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PromocodeRepository extends JpaRepository<Promocode, UUID> {
}

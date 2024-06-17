package com.example.todoappdeel3.repositories;

import com.example.todoappdeel3.models.RetourRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RetourRequestRepository extends JpaRepository<RetourRequest, UUID> {
}

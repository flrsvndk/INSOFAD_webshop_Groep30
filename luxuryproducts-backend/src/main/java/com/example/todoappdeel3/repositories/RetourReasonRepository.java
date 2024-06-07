package com.example.todoappdeel3.repositories;

import com.example.todoappdeel3.models.RetourReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RetourReasonRepository extends JpaRepository<RetourReason, UUID> {
}

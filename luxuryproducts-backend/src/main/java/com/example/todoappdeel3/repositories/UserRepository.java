package com.example.todoappdeel3.repositories;

import com.example.todoappdeel3.models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, UUID> {
    CustomUser findByEmail(String email);
    Optional<List<CustomUser>> findAllByRoleIsNot(String role);
}

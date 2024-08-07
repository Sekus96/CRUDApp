package com.example.CRUDApp.repositories;

import com.example.CRUDApp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findById(int id);
    Boolean existsByUsername(String username);
}

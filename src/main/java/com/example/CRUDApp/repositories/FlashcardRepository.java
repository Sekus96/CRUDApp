package com.example.CRUDApp.repositories;

import com.example.CRUDApp.entities.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

    Optional<Flashcard> findByName(String name);
}

package com.example.CRUDApp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "flashcards")
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String nameInEnglish;
    private String image;
    @ManyToMany(mappedBy = "flashcards", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses; // Relacja z kursami

}

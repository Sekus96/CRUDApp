package com.example.CRUDApp.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
//    private double price;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "course_flashcard", // nazwa tabeli łączącej
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"), // Kolumna odnosząca się do flashcard
            inverseJoinColumns = @JoinColumn(name = "flashcard_id", referencedColumnName = "id")) // Kolumna odnosząca się do course
    private List<Flashcard> flashcards;
}

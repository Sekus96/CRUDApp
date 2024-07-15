package com.example.CRUDApp.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "NAZWA")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
}

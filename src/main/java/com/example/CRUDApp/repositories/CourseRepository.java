package com.example.CRUDApp.repositories;

import com.example.CRUDApp.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Serializable> {
    List<Course> findById(Course byId);

    Optional<Course> findByName(String name);
}

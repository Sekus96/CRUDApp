package com.example.CRUDApp.services;

import com.example.CRUDApp.entities.Course;
import com.example.CRUDApp.entities.UserEntity;

import java.util.List;

public interface CourseService {


    String upsert(Course course, Integer id);

    public String addCourse(Course course);

    public Course getById(Integer id);

    public Course findById(Integer id);

    public List<Course> getAllCoursees();

    public boolean deleteById(Integer id);

    public void updateCourse(Course course, Integer id);

    void save(Course updatedCourse);

//    UserEntity findByUsername(String name);

    boolean existsById(Integer id);
}

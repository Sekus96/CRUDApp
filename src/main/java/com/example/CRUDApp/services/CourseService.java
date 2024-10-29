package com.example.CRUDApp.services;

import com.example.CRUDApp.dto.CourseDto;
import com.example.CRUDApp.entities.Course;
import com.example.CRUDApp.entities.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {


    String upsert(Course course, Integer id);

    public ResponseEntity<String> addCourse(CourseDto courseDto);

    public Course getById(Integer id);

    public Course findById(Integer id);

    public List<CourseDto> getAllCourses();

//    public boolean deleteById(Integer id);

    public ResponseEntity<String> updateCourse(Course course, Integer id);

    void save(Course updatedCourse);

    ResponseEntity<String> deleteCourse(Integer id);

    boolean existsById(Integer id);

    ResponseEntity<Course> getCourseById(Integer id);

    List<Course> findById(List<Integer> courseIds);
}
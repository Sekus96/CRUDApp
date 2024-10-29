package com.example.CRUDApp.controllers;

import com.example.CRUDApp.dto.CourseDto;
import com.example.CRUDApp.entities.Role;
import com.example.CRUDApp.entities.UserEntity;
import com.example.CRUDApp.repositories.CourseRepository;
import com.example.CRUDApp.services.CourseService;
import com.example.CRUDApp.entities.Course;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/CRUDApp")
@Tag(name = "Courses Controller", description = "Endpoints for managing courses")
public class CourseRestController {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Get all courses", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/get")
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @Operation(summary = "Get course by ID", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/get/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Integer id) {
        return courseService.getCourseById(id);
    }

    @Operation(summary = "Create new course", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/post")
    public ResponseEntity<String> addCourse(@RequestBody CourseDto courseDto) {
        return courseService.addCourse(courseDto);
    }

    @Operation(summary = "Update course by ID", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/put/{id}")
    public ResponseEntity<String> updateCourse(@RequestBody Course courseBody, @PathVariable Integer id) {
        return courseService.updateCourse(courseBody, id);
    }

    @Operation(summary = "Delete course by ID", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/detele/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id) {
        return courseService.deleteCourse(id);
    }
}

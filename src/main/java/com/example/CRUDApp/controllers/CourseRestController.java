package com.example.CRUDApp.controllers;

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
@Tag(name = "Course Controller", description = "Endpoints for managing courses")
public class CourseRestController {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private CourseService courseService;

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/secure")
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> allCourses = courseService.getAllCoursees();
        return new ResponseEntity<>(allCourses, HttpStatus.OK);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/secure/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Integer id){
        Course course = courseService.getById(id);
        if(courseService!=null){
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/post")
    public ResponseEntity<String> addCourse(@RequestBody Course course){
        courseService.addCourse(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/put/{id}")
    public ResponseEntity<String> updateCourse(@RequestBody Course courseBody, @PathVariable Integer id){
//        courseService.updateCourse(courseBody, id);
        Course existingCourse = courseService.findById(id);

        if (existingCourse == null) {
            return ResponseEntity.notFound().build();
        }

        existingCourse.setName(courseBody.getName());
        existingCourse.setPrice(courseBody.getPrice());

        courseService.save(existingCourse);

        return ResponseEntity.ok("Zasób o ID " + id + " został zaktualizowany");
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/detele/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id){
        if(!courseService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            courseService.deleteById(id);
            return ResponseEntity.ok("Course with ID " + id + " deleted successfully");
        }
    }
}

package com.example.CRUDApp.services;

import com.example.CRUDApp.dto.CourseDto;
import com.example.CRUDApp.dto.FlashcardDto;
import com.example.CRUDApp.entities.Flashcard;
import com.example.CRUDApp.repositories.CourseRepository;
import com.example.CRUDApp.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoursServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepo;


    @Override
    public String upsert(Course course, Integer id) {
        Optional<Course> findById = courseRepo.findById(course.getId());
        if(findById.isPresent()) {
            return "fail";
        } else {
            courseRepo.save(course);
        }
        return "success";
    }

    @Override
    public ResponseEntity<String> addCourse(CourseDto courseDto) {
        Optional<Course> findByName = courseRepo.findByName(courseDto.getName());
        if (findByName.isPresent()) {
            return new ResponseEntity<>("Course already exists", HttpStatus.CONFLICT);
        } else {

            Course course = new Course();
            course.setName(courseDto.getName());

            courseRepo.save(course); // Zapisz kurs, ID generuje się automatycznie
            return new ResponseEntity<>("Course created successfully", HttpStatus.CREATED);
        }
    }
    @Override
    public void save(Course updatedCourse) {
        courseRepo.save(updatedCourse);
    }


    @Override
    public Course getById(Integer id) {
        Optional<Course> findById = courseRepo.findById(id);

        return findById.orElse(null);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepo.findAll();

        List<CourseDto> courseDtos = new ArrayList<>();

        for (Course course : courses) {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(course.getId());
            courseDto.setName(course.getName());

            // Mapowanie fiszek do DTO
            List<FlashcardDto> flashcardDtos = new ArrayList<>();
            for (Flashcard flashcard : course.getFlashcards()) {
                FlashcardDto flashcardDto = new FlashcardDto();
                flashcardDto.setId(flashcard.getId());
                flashcardDto.setName(flashcard.getName());
                flashcardDto.setNameInEnglish(flashcard.getNameInEnglish());
                flashcardDto.setImage(flashcard.getImage());

                flashcardDtos.add(flashcardDto);
            }

            courseDto.setFlashcard(flashcardDtos);
            courseDtos.add(courseDto);
        }

        if (courseDtos.isEmpty()) {
            return new ArrayList<>();
        } else {
            return courseDtos;
        }
    }

    @Override
    public Course findById(Integer id) {
        return courseRepo.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<String> updateCourse(Course course, Integer id) {
        Optional<Course> existingCourseOptional = courseRepo.findById(id);

        if (existingCourseOptional.isPresent()) {
            Course existingCourse = existingCourseOptional.get();
            existingCourse.setName(course.getName());
//            existingCourse.setPrice(course.getPrice());
            courseRepo.save(existingCourse);
            return ResponseEntity.ok("Kurs o ID " + id + " został zaktualizowany pomyślnie.");
        } else {
            return ResponseEntity.status(404).body("Kurs o ID " + id + " nie został znaleziony.");
        }
    }

    @Override
    public ResponseEntity<String> deleteCourse(Integer id) {
        if (!courseRepo.existsById(id)) {
            return ResponseEntity.status(404).body("Course with ID " + id + " not found.");
        } else {
            courseRepo.deleteById(id);
            return ResponseEntity.ok("Course with ID " + id + " deleted successfully.");
        }
    }

//    @Override
//    public boolean deleteById(Integer id) {
//
//        boolean isRemoved = false;
//
//        if(courseRepo.existsById(id)){
//            courseRepo.deleteById(id);
//            isRemoved = true;
//        }
//        return isRemoved;
//    }

    @Override
    public boolean existsById(Integer id) {
        return courseRepo.existsById(id);
    }

    @Override
    public List<Course> findById(List<Integer> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            return new ArrayList<>();
        }
        return courseRepo.findAllById(courseIds);
    }

    @Override
    public ResponseEntity<Course> getCourseById(Integer id) {
        Course course = getById(id);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

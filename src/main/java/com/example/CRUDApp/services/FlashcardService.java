package com.example.CRUDApp.services;

import com.example.CRUDApp.dto.CourseDto;
import com.example.CRUDApp.dto.FlashcardDto;
import com.example.CRUDApp.entities.Course;
import com.example.CRUDApp.entities.Flashcard;
import com.example.CRUDApp.repositories.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private CourseService courseService;

    public List<FlashcardDto> getAllFlashcards() {
        List<Flashcard> flashcards = flashcardRepository.findAll();

        List<FlashcardDto> flashcardDtos = new ArrayList<>();

        for (Flashcard flashcard : flashcards) {
            FlashcardDto flashcardDto = new FlashcardDto();
            flashcardDto.setId(flashcard.getId());
            flashcardDto.setName(flashcard.getName());
            flashcardDto.setNameInEnglish(flashcard.getNameInEnglish());
            flashcardDto.setImage(flashcard.getImage());

            // Mapowanie kursów do DTO
            List<Integer> courseIds = new ArrayList<>();
            for (Course course : flashcard.getCourses()) {
                courseIds.add(course.getId());  // Zbieramy tylko ID kursów
            }
            flashcardDto.setCourseIds(courseIds);

            flashcardDtos.add(flashcardDto);
        }

        if (flashcardDtos.isEmpty()) {
            return new ArrayList<>();
        } else {
            return flashcardDtos;
        }
    }

    public ResponseEntity<String> addFlashcard(FlashcardDto flashcardDto) {
        Optional<Flashcard> findByName = flashcardRepository.findByName(flashcardDto.getName());

        if (findByName.isPresent()) {
            return new ResponseEntity<>("Flashcard already exists", HttpStatus.CONFLICT);
        }

        Flashcard flashcard = new Flashcard();
        flashcard.setName(flashcardDto.getName());
        flashcard.setNameInEnglish(flashcardDto.getNameInEnglish());
        flashcard.setImage(flashcardDto.getImage());

        List<Integer> courseIds = flashcardDto.getCourseIds();
        List<Course> foundCourses = courseService.findById(courseIds);

        if (!foundCourses.isEmpty()) {
            flashcard.setCourses(foundCourses);

            for (int i = 0; i < foundCourses.size(); i++) {
                Course course = foundCourses.get(i);
                course.getFlashcards().add(flashcard);
            }

            flashcardRepository.save(flashcard);
            return new ResponseEntity<>("Flashcard has been created successfully and assigned to courses.", HttpStatus.CREATED);
        } else {

            flashcardRepository.save(flashcard);
            return new ResponseEntity<>("Flashcard created, but no courses found with the provided IDs.", HttpStatus.CREATED);
        }
    }
}

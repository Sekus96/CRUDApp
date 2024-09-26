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

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    public ResponseEntity<String> addFlashcard(FlashcardDto flashcardDto) {
        Optional<Flashcard> findByName = flashcardRepository.findByName(flashcardDto.getName());
        if (findByName.isPresent()) {
            return new ResponseEntity<>("Flash already exists", HttpStatus.CONFLICT);
        } else {

            Flashcard flashcard = new Flashcard();
            flashcard.setName(flashcardDto.getName());
            flashcard.setNameInEnglish(flashcardDto.getNameInEnglish());
            flashcard.setImage(flashcardDto.getImage());

            flashcardRepository.save(flashcard);
            return new ResponseEntity<>("Flashcard has been created successfully", HttpStatus.OK);

        }
    }
}

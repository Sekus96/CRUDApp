package com.example.CRUDApp.controllers;

import com.example.CRUDApp.dto.CourseDto;
import com.example.CRUDApp.dto.FlashcardDto;
import com.example.CRUDApp.entities.Flashcard;
import com.example.CRUDApp.repositories.FlashcardRepository;
import com.example.CRUDApp.services.FlashcardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Flashcards")
@Tag(name = "Flashcards Controller", description = "Endpoints for managing flashcards")
public class FlashcardController {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private FlashcardService flashcardService;

    @Operation(summary = "Get all flashcards", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/getCard")
    public ResponseEntity<List<Flashcard>> getAllFlashcards(){
        return ResponseEntity.ok(flashcardService.getAllFlashcards());
    }

    @Operation(summary = "Create new flashcard", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/postCard")
    public ResponseEntity<String> addFlashcard(@RequestBody FlashcardDto flashcardDto) {
        return flashcardService.addFlashcard(flashcardDto);
    }
}

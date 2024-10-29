package com.example.CRUDApp.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseDto {

    private Integer id;
    private String name;
    private List<FlashcardDto> flashcard = new ArrayList<>();
}

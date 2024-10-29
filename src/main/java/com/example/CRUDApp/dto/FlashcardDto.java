package com.example.CRUDApp.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlashcardDto {

    private Integer id;
    private String name;
    private String nameInEnglish;
    private String image;
    private List<Integer> courseIds = new ArrayList<>();
//    private List<CourseDto> courses = new ArrayList<>();
}

package com.fsa.cursus.model.response;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseResponse {
    private Long courseId;
    private String name;
    private String description;
    private String image;
    private String summary;
    private int totalDuration;
    private double price;

    private AccountResponse account;
    private CategoryResponse category;
}

package com.fsa.cursus.model.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponse {

    // Khoá Học
    private Long courseId;
    private String name;
    private String description;
    private String image;
    private String summary;
    private int totalDuration;
    private double price;

    // Tài Khoản
    private AccountResponse account;

}

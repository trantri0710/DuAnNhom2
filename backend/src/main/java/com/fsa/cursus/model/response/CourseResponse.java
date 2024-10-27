package com.fsa.cursus.model.response;


<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponse {

    // Khoá Học
=======
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseResponse {
>>>>>>> f262a97ea9e41f16d5fca3b13bb2d211a606eaac
    private Long courseId;
    private String name;
    private String description;
    private String image;
    private String summary;
    private int totalDuration;
    private double price;

<<<<<<< HEAD
    // Tài Khoản
    private AccountResponse account;

=======
    private AccountResponse account;
    private CategoryResponse category;
>>>>>>> f262a97ea9e41f16d5fca3b13bb2d211a606eaac
}

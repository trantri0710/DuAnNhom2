package com.fsa.cursus.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CourseRequest implements Serializable {

    // Khoá Học
    private Long courseId;
    private String name;
    private String description;
    private String image;
    private String summary;
    private int totalDuration;
    private double price;

    private Long teacherId;
}

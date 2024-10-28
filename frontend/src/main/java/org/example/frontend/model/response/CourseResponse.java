package org.example.frontend.model.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponse {

    private Long courseId;
    private String name;
    private String description;
    private String image;
    private String summary;
    private int totalDuration;
    private double price;

}

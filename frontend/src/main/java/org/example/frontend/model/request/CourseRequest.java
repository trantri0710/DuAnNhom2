package org.example.frontend.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

    private Long courseId;
    private String name;
    private String description;
    private String image;
    private String summary;
    private int totalDuration;
    private double price;

}

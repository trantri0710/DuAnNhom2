package com.fsa.cursus.model.request;

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

}

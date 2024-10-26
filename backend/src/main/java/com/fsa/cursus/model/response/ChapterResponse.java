package com.fsa.cursus.model.response;

import com.fsa.cursus.model.entity.Course;
import com.fsa.cursus.model.entity.Lesson;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChapterResponse implements Serializable {
    private Long chapterId;
    private String title;
    private String description;
    private Course course;

    private List<Lesson> lessons = new ArrayList<>();
}

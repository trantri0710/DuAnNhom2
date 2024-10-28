package com.fsa.cursus.model.request;

import com.fsa.cursus.model.entity.Course;
import com.fsa.cursus.model.entity.Lesson;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChapterRequest {
    private Long chapterId;
    private String title;
    private String description;

    private List<LessonRequest> lessons = new ArrayList<>();
    private Long courseId;
}

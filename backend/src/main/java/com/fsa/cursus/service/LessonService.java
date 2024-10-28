package com.fsa.cursus.service;


import com.fsa.cursus.model.entity.Lesson;
import com.fsa.cursus.model.request.LessonRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonService {
    public List<Lesson> findAll();
    Page<Lesson> getAllLessons(Pageable pageable);
    Lesson getLessonById(Long lessonId);
    Lesson saveLesson(LessonRequest lesson);
    boolean deleteLessonById(Long lessonId);
}

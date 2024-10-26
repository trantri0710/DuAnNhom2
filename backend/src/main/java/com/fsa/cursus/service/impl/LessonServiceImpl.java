package com.fsa.cursus.service.Impl;


import com.fsa.cursus.model.entity.Lesson;
import com.fsa.cursus.repository.LessonRepository;
import com.fsa.cursus.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public List<Lesson> findAll(){
        return lessonRepository.findAll();
    }

    @Override
    public Page<Lesson> getAllLessons(Pageable pageable){
        return lessonRepository.findAll(pageable);
    }

    @Override
    public Lesson getLessonById(Long lessonId){
        return lessonRepository.findById(lessonId).orElse(null);
    }

    @Override
    public Lesson saveLesson(Lesson lesson){
        return lessonRepository.save(lesson);
    }

    @Override
    public boolean deleteLessonById(Long lessonId){
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        if(lesson != null){
            lessonRepository.delete(lesson);
            return true;
        }
        return false;
    }

}

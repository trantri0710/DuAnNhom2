package com.fsa.cursus.service.impl;


import com.fsa.cursus.exception.DataNotFoundException;
import com.fsa.cursus.model.entity.Chapter;
import com.fsa.cursus.model.entity.Lesson;
import com.fsa.cursus.model.request.LessonRequest;
import com.fsa.cursus.repository.LessonRepository;
import com.fsa.cursus.service.ChapterService;
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

    @Autowired
    private ChapterService chapterService;

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
    public Lesson saveLesson(LessonRequest request){
        Lesson lesson = getLessonById(request.getLessonId());
        // nếu chưa có lesson thì tạo mới
        if (lesson == null) {
            lesson = new Lesson();
        }

        // gán vào lesson: chuyen Request > Entity
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setVideo(request.getVideo());
        lesson.setDuration(request.getDuration());
        lesson.setStatus(request.isStatus());

        // lấy theo chapterId
        // bắt exception
        Chapter chapter = chapterService.getChapterById(request.getChapterId());
        if (chapter == null) {
            throw new DataNotFoundException("ChapterId Not Found");
        }

        lesson.setChapter(chapter);
        // lưu lesson
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

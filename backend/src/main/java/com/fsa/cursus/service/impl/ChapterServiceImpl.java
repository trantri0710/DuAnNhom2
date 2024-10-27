package com.fsa.cursus.service.impl;


import com.fsa.cursus.exception.DataNotFoundException;
import com.fsa.cursus.model.entity.Chapter;
import com.fsa.cursus.model.request.ChapterRequest;
import com.fsa.cursus.repository.ChapterRepository;
import com.fsa.cursus.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public List<Chapter> findAll(){
        return chapterRepository.findAll();
    }

    @Override
    public Page<Chapter> getAllChapters(Pageable pageable){
        return chapterRepository.findAll(pageable);
    }

    @Override
    public Chapter getChapterById(Long chapterId){
        return chapterRepository.findById(chapterId).orElse(null);
    }

    @Override
    public Chapter saveChapter(ChapterRequest request){
        Chapter chapter = getChapterById(request.getChapterId());
        if (chapter == null) {
            chapter = new Chapter();
        }

        // chuyen Request > Entity
        chapter.setTitle(request.getTitle());
        chapter.setDescription(request.getDescription());

//        Course course = chapterService.findById(request.getCourseId())
//        if (course != null) {
//            throw new DataNotFoundException("CourseId Not Found");
//        }
//            chapter.setCourse(course);

        return chapterRepository.save(chapter);
    }

    @Override
    public boolean deleteChapterById(Long chapterId){
        Chapter chapter = chapterRepository.findById(chapterId).orElse(null);
        if(chapter != null){
            chapterRepository.delete(chapter);
            return true;
        }
        return false;
    }
}

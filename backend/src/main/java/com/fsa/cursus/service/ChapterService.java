package com.fsa.cursus.service;



import com.fsa.cursus.model.entity.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChapterService {
    public List<Chapter> findAll();
    Page<Chapter> getAllChapters(Pageable pageable);
    Chapter getChapterById(Long chapterId);
    Chapter saveChapter(Chapter chapter);
    boolean deleteChapterById(Long chapterId);
}

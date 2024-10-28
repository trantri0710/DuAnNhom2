package com.fsa.cursus.service;



import com.fsa.cursus.model.entity.Chapter;
import com.fsa.cursus.model.request.ChapterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChapterService {
    public List<Chapter> findAll();
    Page<Chapter> getAllChapters(Pageable pageable);
    Chapter getChapterById(Long chapterId);
    Chapter saveChapter(ChapterRequest request);
    boolean deleteChapterById(Long chapterId);
}

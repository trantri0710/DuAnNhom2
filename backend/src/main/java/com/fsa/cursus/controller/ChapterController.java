package com.fsa.cursus.controller;


import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.entity.Chapter;
import com.fsa.cursus.model.entity.Course;
import com.fsa.cursus.model.mapper.ChapterMapper;
import com.fsa.cursus.model.request.ChapterRequest;
import com.fsa.cursus.model.response.ApiResponse;
import com.fsa.cursus.security.CustomUserDetails;
import com.fsa.cursus.service.ChapterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/chapters")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @Autowired
    private ChapterMapper chapterMapper;

    // Lấy tất cả danh sách theo page
    @GetMapping
    public ResponseEntity<ApiResponse> getAll(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer size){
        Pageable pageable = PageRequest.of(currentPage -1, size);
        Page<Chapter> chapterPage = chapterService.getAllChapters(pageable);


        ApiResponse response = new ApiResponse();

        response.ok("OK", chapterMapper.convertToDTO(chapterPage.getContent()));
        response.setPaginationMetadata(chapterPage.getTotalElements(),
                chapterPage.getTotalPages(),
                chapterPage.getNumber(),
                chapterPage.getSize());

        return ResponseEntity.ok(response);  // Trả về danh sách khóa học dưới dạng JSON

    }

    // Lấy thông tin theo chapter ID
    @GetMapping(value = "/{chapterId}")
    public ResponseEntity<ApiResponse> getChapterById(@PathVariable Long chapterId){
        Chapter chapter = chapterService.getChapterById(chapterId);

        if(chapter == null) {
            // trả về 404
            return ResponseEntity.notFound().build();
        }
        // trả về 200
        ApiResponse response = new ApiResponse();
        response.ok("OK", chapterMapper.convertToDTO(chapter));

        return ResponseEntity.ok(response);

    }

    // Tạo mới thông tin chapter
    @PostMapping
    public ResponseEntity createAndUpdateChapter(@Valid @RequestBody ChapterRequest request,
                                     BindingResult bindingResult){
        // Nếu có lỗi
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>("Lỗi nhập liệu", HttpStatus.BAD_REQUEST);
        }
        // Dùng service để lưu Chapter mới vào csdl
        Chapter result = chapterService.saveChapter(request);
        // Trả về Chapter mới tạo với trạng thái 200
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    // Cập nhật thông tin của chapter
//    @PutMapping
//    public ResponseEntity<Chapter> updateChapter(@RequestBody ChapterRequest chapter, @PathVariable Long chapterId){
//        Chapter result = chapterService.getChapterById(chapterId);
//        if (result == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        result.setChapterId(chapter.getChapterId());
//        result.setTitle(chapter.getTitle());
//        result.setDescription(chapter.getDescription());
//        result.setLessons(chapter.getLessons());
//        result.setCourse(chapter.getCourse());
//
//        chapterService.saveChapter(result);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @DeleteMapping(value = "/{chapterId}")
    public ResponseEntity<Chapter> deleteChapter(@PathVariable Long chapterId){
        boolean temp = chapterService.deleteChapterById(chapterId);
        if (temp) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}

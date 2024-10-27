package com.fsa.cursus.controller;



import com.fsa.cursus.model.entity.Lesson;
import com.fsa.cursus.model.response.ApiResponse;
import com.fsa.cursus.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/lessons")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    // Lấy tất cả danh sách theo paging
    @GetMapping
    public ResponseEntity<ApiResponse> getAll(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer size){
        Pageable pageable = PageRequest.of(currentPage -1, size);
        Page<Lesson> lessonPage = lessonService.getAllLessons(pageable);

        //Tạo đối tượng ApiRespone để trả v kết quả
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(lessonPage.getContent());
        apiResponse.setPaginationMetadata(lessonPage.getTotalElements(), lessonPage.getTotalPages(), currentPage, size);
        // Trả về phản hồi với trạng thái HTTP OK (200)
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // Lấy thông tin theo lessonID
    @GetMapping(value = "/{lessonId}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long lessonId){
        Lesson lesson = lessonService.getLessonById(lessonId);

        if(lesson == null) {
            // trả về 404
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        // trả về 200
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    // Tạo mới thông tin Lesson
    @PostMapping
    public ResponseEntity createLesson(@Valid @RequestBody Lesson lesson,
                                     BindingResult bindingResult){
        // Nếu có lỗi
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>("Lỗi nhập liệu", HttpStatus.BAD_REQUEST);
        }
        // Dùng service để lưu Lesson mới vào csdl
        Lesson result = lessonService.saveLesson(lesson);
        // Trả về Lesson mới tạo với trạng thái 200
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Cập nhật thông tin của lesson
    @PutMapping
    public ResponseEntity<Lesson> updateLesson(@RequestBody Lesson lesson, @PathVariable Long lessonId){
        Lesson result = lessonService.getLessonById(lessonId);
        if (result == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        result.setLessonId(lesson.getLessonId());
        result.setChapter(lesson.getChapter());
        result.setTitle(lesson.getTitle());
        result.setContent(lesson.getContent());
        result.setVideo(lesson.getVideo());
        result.setDuration(lesson.getDuration());

        lessonService.saveLesson(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{lessonId}")
    public ResponseEntity<Lesson> deleteLesson(@PathVariable Long lessonId){
        boolean temp = lessonService.deleteLessonById(lessonId);
        if (temp) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}

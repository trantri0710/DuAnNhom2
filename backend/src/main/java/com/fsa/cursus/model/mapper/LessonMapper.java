package com.fsa.cursus.model.mapper;

import com.fsa.cursus.model.entity.Lesson;
import com.fsa.cursus.model.response.LessonResponse;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonMapper {
    // Phương thức này chuyển đổi một entity Lesson thành một LessonResponse DTO
    public LessonResponse convertToDTO(Lesson lesson){
        if (lesson == null) {
            // Nếu lesson null, trả về null để tránh lỗi NullPointerException
            return null;
        }

        // Tạo một đối tượng LessonResponse mới
        LessonResponse lessonResponse = new LessonResponse();
        // Gán các giá trị từ entity Lesson vào DTO
        lessonResponse.setLessonId(lesson.getLessonId());
        lessonResponse.setTitle(lessonResponse.getTitle());
        lessonResponse.setContent(lessonResponse.getContent());
        lessonResponse.setVideo(lessonResponse.getVideo());
        lessonResponse.setDuration(lesson.getDuration());
        lessonResponse.setStatus(lessonResponse.isStatus());

        // Trả về đối tượng LessonResponse sau khi đã gán đầy đủ dữ liệu
        return lessonResponse;
    }

    public List<LessonResponse> convertToDTO(List<Lesson> lessonList){
        if (lessonList == null) {
            return null;
        }
        List<LessonResponse> lessonResponseList = new ArrayList<>();
        // Duyệt qua từng đối tượng Lesson trong danh sách và chuyển đổi sang LessonResponse
        for (Lesson lesson : lessonList) {
            // Gọi phương thức chuyển đổi từng lesson
            LessonResponse lessonResponse = convertToDTO(lesson);
            // Thêm kết quả vào danh sách kết quả
            lessonResponseList.add(lessonResponse);
        }
        // Trả về danh sách LessonResponse đã được chuyển đổi từ Lesson entity
        return lessonResponseList;
    }
}

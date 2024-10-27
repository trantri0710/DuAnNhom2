package com.fsa.cursus.model.mapper;

import com.fsa.cursus.model.entity.Chapter;
import com.fsa.cursus.model.response.ChapterResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChapterMapper {
    // Phương thức này chuyển đổi một entity Chapter thành một ChapterResponse DTO
    public ChapterResponse convertToDTO(Chapter chapter){
        if (chapter == null) {
            // Nếu chapter null, trả về null để tránh lỗi NullPointerException
            return null;
        }

        // Tạo một đối tượng ChapterResponse mới
        ChapterResponse chapterResponse = new ChapterResponse();
        // Gán các giá trị từ entity Chapter vào DTO
        chapterResponse.setChapterId(chapter.getChapterId());
        chapterResponse.setTitle(chapter.getTitle());
        chapterResponse.setCourse(chapter.getCourse());
        chapterResponse.setDescription(chapter.getDescription());
        chapterResponse.setLessons(chapter.getLessons());

        // Trả về đối tượng ChapterResponse sau khi đã gán đầy đủ dữ liệu
        return chapterResponse;
    }

    public List<ChapterResponse> convertToDTO(List<Chapter> chapterList){
        if (chapterList == null) {
            return null;
        }
        List<ChapterResponse> chapterResponseList = new ArrayList<>();
        // Duyệt qua từng đối tượng Chapter trong danh sách và chuyển đổi sang ChapterResponse
        for (Chapter chapter : chapterList) {
            // Gọi phương thức chuyển đổi từng chapter
            ChapterResponse chapterResponse = convertToDTO(chapter);
            // Thêm kết quả vào danh sách kết quả
            chapterResponseList.add(chapterResponse);
        }
        // Trả về danh sách ChapterResponse đã được chuyển đổi từ Chapter entity
        return chapterResponseList;
    }
}

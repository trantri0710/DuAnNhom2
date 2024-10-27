package com.fsa.cursus.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LessonResponse implements Serializable {
    // Bài học
    private Long lessonId;
    private String title;
    private String content;
    private String video;
    private int duration;
    private boolean status;

}

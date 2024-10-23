package com.fsa.cursus.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "lessons")
public class Lesson implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
    private String content;

    @Column(name = "video")
    private String video;

    @Column(name = "duration")  // Đơn vị: phút
    private int duration;

    @ManyToOne
    @JoinColumn(name = "chapter_id")  // Khóa ngoại liên kết với bảng Chapter
    private Chapter chapter;

}

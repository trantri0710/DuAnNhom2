package com.fsa.cursus.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "summary", length = 200)
    private String summary;

    @Column(name = "total_duration")
    private int totalDuration; // Đơn vị: phút

    @Column(name = "price", nullable = false)
    private double price;  // Giá bán

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Chapter> chapters = new ArrayList<>();  // Một khóa học có nhiều chương

    @ManyToOne
    @JoinColumn(name = "account_id")  // Khóa ngoại liên kết với bảng Account
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}

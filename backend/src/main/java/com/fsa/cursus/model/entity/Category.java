package com.fsa.cursus.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long categoryId;

  @Column(name = "category_name")
  private String categoryName;

  @Column(name = "category_status")
  private Boolean categoryStatus;

  /*
   * Relationship
   * */
  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Course> courseList = new ArrayList<Course>();
}

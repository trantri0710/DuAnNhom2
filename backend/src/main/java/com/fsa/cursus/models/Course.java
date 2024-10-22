package com.fsa.cursus.models;

import com.fsa.cursus.models.actor.Instructor;
import com.fsa.cursus.models.actor.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course {

  /*
  *
  * Attributes
  *
  * */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_id")
  private int id;

  @NotBlank
  @Column(name = "course_name")
  private String courseName;

  @NotNull
  @Column(name = "course_price")
  private double price;

  @NotNull
  @Column(name = "totalSell")
  private int totalSell;

  @NotBlank
  @Column(name = "summary")
  private String summary;

  @NotNull
  @Column(name = "course_status")
  private boolean status;

  /*
  *
  * Relations
  *
  * */
  @OneToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToOne
  @JoinColumn(name = "sub_category_id")
  private SubCategory sub_category;

  @ManyToOne
  @JoinColumn(name = "instructor_id")
  private Instructor instructor;

  @OneToMany(mappedBy = "course")
  private Set<Feedback> feedbacks = new HashSet<>();

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(
    name = "course_student",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "student_id")
  )
  private Set<Student> students = new HashSet<>();
}

package com.fsa.cursus.models.actor;

import com.fsa.cursus.models.Course;
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
@Table(name = "student")
public class Student {

  /*
   *
   * Attributes
   *
   * */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id")
  private int id;

  @NotBlank
  @Column(name = "username")
  private String username;

  @NotBlank
  @Column(name = "password")
  private String password;

  @NotNull
  @Column(name = "status")
  private boolean status;

  /*
   *
   * Relations
   *
   * */
  @ManyToMany(mappedBy = "students")
  private Set<Course> courses = new HashSet<>();
}

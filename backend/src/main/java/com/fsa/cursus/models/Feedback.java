package com.fsa.cursus.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {

  /*
   *
   * Attributes
   *
   * */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feedback_id")
  private int id;

  @NotBlank
  @Column(name = "feedback_description")
  private String description;

  /*
  *
  * Relations
  *
  * */
  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;
}

package com.fsa.cursus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fsa.cursus.models.actor.Admin;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sub_category")
public class SubCategory {

  /*
  *
  * Attributes
  *
  * */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sub_category_id")
  private int id;

  @NotBlank
  @Column(name = "sub_category_name")
  private String name;

  @NotNull
  @Column(name = "sub_category_description")
  private String description;

  @NotNull
  @Column(name = "sub_category_status")
  private boolean status;

  /*
  *
  * Relations
  *
  * */
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "admin_id")
  private Admin admin;

  @JsonIgnore
  @OneToOne(mappedBy = "sub_category")
  private Course course;
}

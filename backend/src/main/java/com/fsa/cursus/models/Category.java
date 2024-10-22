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

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {

  /*
  *
  * Attributes
  *
  * */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private int id;

  @NotBlank
  @Column(name = "category_name")
  private String name;

  @NotNull
  @Column(name = "category_description")
  private String description;

  @NotNull
  @Column(name = "category_status")
  private boolean status;

  /*
  *
  * Relations
  *
  * */
  @ManyToOne
  @JoinColumn(name = "admin_id")
  private Admin admin;

  @JsonIgnore
  @OneToMany(mappedBy = "category")
  private Set<SubCategory> subCategories = new HashSet<>();

  @JsonIgnore
  @OneToOne(mappedBy = "category")
  private Course course;
}

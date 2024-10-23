package com.fsa.cursus.model.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

  /*
  *
  * Attributes
  *
  * */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id")
  private Long accountId;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "role", nullable = false)
  private String role;

  @Column(name = "status")
  private boolean status;

  /*
  *
  * Relations
  *
  * */
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<Feedback> feedbacks = new HashSet<>();
}

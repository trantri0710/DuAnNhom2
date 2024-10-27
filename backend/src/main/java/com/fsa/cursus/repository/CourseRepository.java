package com.fsa.cursus.repository;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByAccount(Account account, Pageable pageable);
}

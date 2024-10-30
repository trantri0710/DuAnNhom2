package com.fsa.cursus.service;

import com.fsa.cursus.model.entity.Course;
import com.fsa.cursus.model.request.CourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    Page<Course> getAllCourses(Pageable pageable);

    Course getCourseById(Long courseId);

    Page<Course> getByAccount(Long accountId, Pageable pageable);

    Course saveCourse(CourseRequest request);


}

package org.example.frontend.service;

import org.example.frontend.model.request.CourseRequest;
import org.example.frontend.model.response.ApiResponse;

public interface CourseService{

    ApiResponse getAllCourses(int currentPage, int pageSize, String accessToken);
    ApiResponse countAllCourses(String accessToken);
    ApiResponse getCourseById(Long id, String accessToken);
    ApiResponse updateCourse(CourseRequest courseRequest, String accessToken);
    ApiResponse addCourse(CourseRequest courseRequest, String accessToken);
    ApiResponse getAllCoursesByAccount(int currentPage, int pageSize, String accessToken);
}

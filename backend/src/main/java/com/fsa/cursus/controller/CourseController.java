package com.fsa.cursus.controller;


import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.entity.Course;
import com.fsa.cursus.model.mapper.CourseMapper;
import com.fsa.cursus.model.request.CourseRequest;
import com.fsa.cursus.model.response.ApiResponse;
import com.fsa.cursus.security.CustomUserDetails;
import com.fsa.cursus.service.CourseService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseMapper courseMapper;

    @GetMapping
    public ResponseEntity<ApiResponse> getCourses(@RequestParam(defaultValue = "1") Integer currentPage,
                                                  @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(currentPage - 1, size);
        Page<Course> coursePage = courseService.getAllCourses(pageable);

        ApiResponse apiResponse = new ApiResponse();


        apiResponse.ok("ok", courseMapper.toCourse(coursePage.getContent()));
        apiResponse.setPaginationMetadata(coursePage.getTotalElements(),
                coursePage.getTotalPages(), coursePage.getNumber(), coursePage.getSize());

        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @GetMapping("/account")
    public ResponseEntity<ApiResponse> getByAccount(@RequestParam(defaultValue = "1") Integer currentPage,
                                                    @RequestParam(defaultValue = "10") Integer size, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();

        Pageable pageable = PageRequest.of(currentPage - 1, size);
        Page<Course> coursePage = courseService.getByAccount(account.getAccountId(), pageable);

        ApiResponse response = new ApiResponse();

        response.ok("OK", coursePage.getContent());
        response.setPaginationMetadata(coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                coursePage.getNumber(),
                coursePage.getSize());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> saveCourse(@Valid @RequestBody CourseRequest courseRequest, Authentication authentication,
                                                  BindingResult bindingResult) {
        // validato

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();

        Course course = courseService.saveCourse(courseRequest);

        ApiResponse response = new ApiResponse();

        response.ok("OK", courseMapper.toCourse(course));

        return ResponseEntity.ok(response);
    }



}

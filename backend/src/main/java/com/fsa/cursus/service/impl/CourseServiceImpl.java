package com.fsa.cursus.service.impl;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.entity.Course;
import com.fsa.cursus.model.request.CourseRequest;
import com.fsa.cursus.repository.CourseRepository;
import com.fsa.cursus.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {

        return courseRepository.findAll(pageable);
    }

    @Override
    public Course getCourseById(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            return courseOptional.get();
        }else
        {
            return null;
        }
    }

    @Override
    public Page<Course> getByAccount(Long accountId, Pageable pageable) {
        Account account = new Account();
        account.setAccountId(accountId);

        return courseRepository.findByAccount(account, pageable);
    }

    @Override
    public Course saveCourse(CourseRequest request) {
        Course course = null;
        Account account = new Account();
        if (request.getCourseId() == 0) {
            course = new Course();
            account.setAccountId(request.getTeacherId());
            course.setAccount(account);
            course.setCourseId(0L);
        }else{
            course = getCourseById(request.getCourseId());
        }

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setImage(request.getImage());
        course.setSummary(request.getSummary());
        course.setPrice(request.getPrice());
        course.setTotalDuration(request.getTotalDuration());

        return courseRepository.save(course);
    }
}

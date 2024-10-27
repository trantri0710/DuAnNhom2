package com.fsa.cursus.model.mapper;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.entity.Category;
import com.fsa.cursus.model.entity.Course;
import com.fsa.cursus.model.response.AccountResponse;
import com.fsa.cursus.model.response.CategoryResponse;
import com.fsa.cursus.model.response.CourseResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseMapper {

    public CourseResponse toCourse(Course course) {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setCourseId(course.getCourseId());
        courseResponse.setDescription(course.getDescription());
        courseResponse.setName(course.getName());
        courseResponse.setImage(course.getImage());
        courseResponse.setPrice(course.getPrice());
        courseResponse.setTotalDuration(course.getTotalDuration());
        courseResponse.setSummary(course.getSummary());

        Account account = course.getAccount();
        if (account != null) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setAccountId(account.getAccountId());
            accountResponse.setFullName(account.getFullName());

            courseResponse.setAccount(accountResponse);
        }

        Category category = course.getCategory();
        if (category != null) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryId(category.getCategoryId());
            categoryResponse.setCategoryName(category.getCategoryName());
            categoryResponse.setCategoryStatus(category.getCategoryStatus());
        }
        return courseResponse;
    }

    public List<CourseResponse> toCourse(List<Course> courses) {
        if (courses == null) { return null; }

        List<CourseResponse> courseResponseList = new ArrayList<>();

        for (Course course : courses) {
            CourseResponse courseResponse = toCourse(course);
            courseResponseList.add(courseResponse);
        }
        return courseResponseList;

    }
}

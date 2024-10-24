package com.fsa.cursus.model.response;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.entity.Course;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FeedbackResponse {
    private int feedbackId;
    private String description;
    private int rating;

    private CourseResponse course;
    private AccountResponse account;
}

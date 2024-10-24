package com.fsa.cursus.model.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackRequest {

    private int feedbackId;
    private String feedbackDescription;
    private int feedbackRating;
}

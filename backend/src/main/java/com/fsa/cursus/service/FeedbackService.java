package com.fsa.cursus.service;

import com.fsa.cursus.model.entity.Feedback;
import com.fsa.cursus.model.request.FeedbackRequest;

import java.util.List;

public interface FeedbackService {
    Feedback save(FeedbackRequest feedbackRequest);
    List<Feedback> findAll();
    Feedback findFeedbackById(int id);

}

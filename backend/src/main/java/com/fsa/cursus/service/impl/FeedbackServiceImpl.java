package com.fsa.cursus.service.Impl;

import com.fsa.cursus.model.entity.Feedback;
import com.fsa.cursus.model.request.FeedbackRequest;
import com.fsa.cursus.repository.FeedbackRepository;
import com.fsa.cursus.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback save(FeedbackRequest feedbackRequest) {

        Feedback feedback = null;

        if (feedbackRequest.getFeedbackId() == 0) {
            feedback = new Feedback();
        } else {
            feedback = findFeedbackById(feedbackRequest.getFeedbackId());
        }

        feedback.setDescription(feedbackRequest.getFeedbackDescription());
        feedback.setRating(feedbackRequest.getFeedbackRating());


        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback findFeedbackById(int id) {
        return feedbackRepository.findById(id).orElse(null);
    }
}

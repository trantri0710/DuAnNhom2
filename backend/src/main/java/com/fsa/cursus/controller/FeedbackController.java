package com.fsa.cursus.controller;


import com.fsa.cursus.model.entity.Feedback;
import com.fsa.cursus.model.mapper.FeedbackMapper;
import com.fsa.cursus.model.request.FeedbackRequest;
import com.fsa.cursus.model.response.ApiResponse;
import com.fsa.cursus.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @PostMapping
    public ResponseEntity<ApiResponse> saveFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackService.save(feedbackRequest);

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.ok("ok", feedbackMapper.toFeedbackResponse(feedback));

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateFeedback(@PathVariable int id, @RequestBody FeedbackRequest feedbackRequest) {

        Feedback feedback = feedbackService.findFeedbackById(id);

        ApiResponse apiResponse = new ApiResponse();

        feedback.setRating(feedback.getRating());
        feedback.setDescription(feedback.getDescription());
        feedbackService.save(feedbackRequest);

        apiResponse.ok("ok", feedbackMapper.toFeedbackResponse(feedback));

        return ResponseEntity.ok(apiResponse);
    }
}

package com.fsa.cursus.model.mapper;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.entity.Feedback;
import com.fsa.cursus.model.response.AccountResponse;
import com.fsa.cursus.model.response.FeedbackResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeedbackMapper {

    public FeedbackResponse toFeedbackResponse(Feedback feedback) {

        if (feedback == null) {
            return null;
        }

        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setFeedbackId(feedback.getFeedbackId());
        feedbackResponse.setDescription(feedback.getDescription());
        feedbackResponse.setRating(feedback.getRating());

        Account account = feedback.getAccount();
        if (account != null) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setAccountId(account.getAccountId());
            accountResponse.setFullName(account.getFullName());

            feedbackResponse.setAccount(accountResponse);
        }

        return feedbackResponse;
    }

    public List<FeedbackResponse> toFeedbackResponseList(List<Feedback> feedbackList) {
        if (feedbackList == null) {
            return null;
        }

        List<FeedbackResponse> feedbackResponseList = new ArrayList<>();
        for (Feedback feedback : feedbackList) {
            FeedbackResponse feedbackResponse = toFeedbackResponse(feedback);
            feedbackResponseList.add(feedbackResponse);
        }
        return feedbackResponseList;
    }
}

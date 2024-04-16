package com.example.retrospective.service;

import com.example.retrospective.CustomExceptionHandler;
import com.example.retrospective.model.Feedback;
import com.example.retrospective.model.Retrospective;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RetrospectiveService {
    ResponseEntity<Retrospective> createRetrospective(Retrospective retrospective) throws CustomExceptionHandler.DataNotFoundException;

    Retrospective addFeedbackItem(String name, Feedback feedback) throws CustomExceptionHandler.ParticipantNotAssociatedException, CustomExceptionHandler.DataNotFoundException, CustomExceptionHandler.FeedBackTypeException;

    Retrospective getRetrospectiveByName(String name);

    boolean isParticipantInRetrospective(String retrospectiveName, String participantName);

    Retrospective updateFeedbackItem(String name, Feedback feedback);

    List<Retrospective> searchRetrospectivesByDate(String date, int page, int pageSize);
}
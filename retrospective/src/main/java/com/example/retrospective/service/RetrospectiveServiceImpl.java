package com.example.retrospective.service;
import com.example.retrospective.CustomExceptionHandler;
import com.example.retrospective.RetrospectiveRepository;
import com.example.retrospective.controller.RetrospectiveController;
import com.example.retrospective.model.Feedback;
import com.example.retrospective.model.Retrospective;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class RetrospectiveServiceImpl implements RetrospectiveService {

    private final Map<String, Retrospective> retrospectives = new HashMap<>();
    //RetrospectiveRepository retrorep;

   // public RetrospectiveServiceImpl(RetrospectiveRepository retrorep) {
    //    this.retrorep = retrorep;
    //}
    private final Logger logger = LoggerFactory.getLogger(RetrospectiveController.class);
    @Override
    public ResponseEntity<Retrospective> createRetrospective(Retrospective retrospective) throws CustomExceptionHandler.DataNotFoundException {
        if (retrospective.getName() == null || retrospective.getName().isEmpty()) {
            throw new CustomExceptionHandler.DataNotFoundException("Retrospective cannot be created without a name.");
        }

        if (retrospective.getDate() == null || retrospective.getDate().isEmpty()) {
            throw new CustomExceptionHandler.DataNotFoundException("Retrospective cannot be created without a date.");
        }
        if (retrospective.getParticipants() == null || retrospective.getParticipants().isEmpty()) {
            throw new CustomExceptionHandler.DataNotFoundException("Retrospective cannot be created without a date.");
        }
        String name = retrospective.getName();
        if (retrospectives.containsKey(name)) {
            String errorMessage = STR."Retrospective with name \{name} already exists.";
        }
        retrospectives.put(name, retrospective);
        logger.info("Retrospective created: {}", retrospective);
        return ResponseEntity.ok(retrospective);
    }
    @Override
    public Retrospective addFeedbackItem(String name, Feedback feedback) throws CustomExceptionHandler.ParticipantNotAssociatedException, CustomExceptionHandler.DataNotFoundException, CustomExceptionHandler.FeedBackTypeException {

            Retrospective retrospective = getRetrospectiveByName(name);
            boolean isParticipantInRetrospective = isParticipantInRetrospective(name, feedback.getName());

            if (retrospective == null) {
                logger.error("Retrospective with name {} does not exist.", name);
                throw new CustomExceptionHandler.DataNotFoundException(STR."Retrospective with name \{name} does not exist.");
            }

            if (!isParticipantInRetrospective) {
                logger.error("Participant {} is not associated with the retrospective.", feedback.getName());
                throw new CustomExceptionHandler.ParticipantNotAssociatedException(STR."Participant \{feedback.getName()} is not associated with the retrospective.");
            }

            // Validate feedbackType
            String feedbackType = feedback.getFeedbackType().toLowerCase(); // Convert to lowercase for case-insensitive comparison
            if (!Arrays.asList("positive", "negative", "idea", "praise").contains(feedbackType)) {
                logger.error("Invalid feedback type: {}", feedbackType);
                throw new CustomExceptionHandler.FeedBackTypeException("Invalid feedback type.");
            }

            retrospective.addFeedbackItem(feedback);
            logger.info("Feedback item added to retrospective: {}", retrospective);
        return retrospective;
    }
    @Override
    public Retrospective getRetrospectiveByName(String name) {
        for (Retrospective retrospective : retrospectives.values()) {
            if (retrospective.getName().equals(name)) {
                return retrospective;
            }
        }
        return null; // Return null if the retrospective with the given name is not found
    }
    @Override
    public boolean isParticipantInRetrospective(String retrospectiveName, String participantName) {
        Retrospective retrospective = getRetrospectiveByName(retrospectiveName);
        if (retrospective == null) {
            return false;
        }

        List<String> participants = retrospective.getParticipants();
        return participants != null && participants.contains(participantName);
    }
    @Override
    public Retrospective updateFeedbackItem(String name, Feedback feedback) {
        logger.info("Updating feedback item for retrospective: {}", feedback.getName());
        Retrospective retrospective = getRetrospectiveByName(name);
        if (retrospective == null) {
            logger.error("Retrospective with name {} does not exist.", name);
            throw new IllegalArgumentException(STR."Retrospective with name \{name} does not exist.");
        }
        retrospective.updateFeedbackItem(name, feedback);
        logger.info("Feedback item updated for retrospective: {}", name);
        return retrospective;
    }

    @Override
    public List<Retrospective> searchRetrospectivesByDate(String date, int page, int pageSize) {

        List<Retrospective> allRetrospectives;
        if(date == null){
            allRetrospectives = new ArrayList<>(retrospectives.values());
            logger.debug("Getting all retrospectives. Page: {}, PageSize: {}", page, pageSize);
        } else {
            logger.info("Searching retrospectives for date: {}", date);
            allRetrospectives = new ArrayList<>();
            for (Retrospective retrospective : retrospectives.values()) {
                if (retrospective.getDate().equals(date)) {
                    allRetrospectives.add(retrospective);
                }
            }
        }
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, allRetrospectives.size());
        List<Retrospective> pagedSearchRetrospectives = allRetrospectives.subList(fromIndex, toIndex);
        logger.info("Retrospectives found for date {}: {}", date, allRetrospectives.size());
        return pagedSearchRetrospectives;
    }
}

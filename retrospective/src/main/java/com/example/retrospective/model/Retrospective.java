package com.example.retrospective.model;

import com.example.retrospective.controller.RetrospectiveController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_table") //
public class Retrospective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    private String summary;
    private String date;
    @NotNull(message = "Retrospective cannot be created without a participants.")
    private List<String> participants;
    private List<Feedback> feedbackItems;

    public Retrospective(){

    }

    private final Logger logger = LoggerFactory.getLogger(RetrospectiveController.class);

    // Constructor, getters, and setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public void setFeedback(List<Feedback> feedbackItems) {
        this.feedbackItems = feedbackItems;
    }

    public void addFeedbackItem(Feedback feedback) {
        if (feedbackItems == null) {
            feedbackItems = new ArrayList<>();
        }
        feedbackItems.add(feedback);
    }

    public void updateFeedbackItem(String name, Feedback feedback) {
        if (feedback != null && name != null) {
            for (int i = 0; i < feedbackItems.size(); i++) {
                Feedback currentFeedback = feedbackItems.get(i);
                if (currentFeedback != null && currentFeedback.getName().equals(feedback.getName())) {
                    feedbackItems.set(i, feedback);
                }
            }
        }
    }

    public String getDate() {
        return date;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getName() {
        return name;
    }

    public List<Feedback> getFeedbackItems() {
        return feedbackItems;
    }

}

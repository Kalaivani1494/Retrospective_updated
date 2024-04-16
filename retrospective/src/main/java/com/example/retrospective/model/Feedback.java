package com.example.retrospective.model;

public class Feedback {
    private String name;
    private String body;
    private String feedbackType;

    // Getter methods
    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }
}

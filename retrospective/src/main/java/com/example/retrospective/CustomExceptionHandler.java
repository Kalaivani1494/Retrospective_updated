package com.example.retrospective;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ParticipantNotAssociatedException.class)
    public ResponseEntity<Object> handleParticipantNotAssociatedException(ParticipantNotAssociatedException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    public static class ParticipantNotAssociatedException extends Throwable {
        public ParticipantNotAssociatedException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFound(DataNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    public static class DataNotFoundException extends Throwable {
        public DataNotFoundException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(FeedBackTypeException.class)
    public ResponseEntity<Object> handleFeedBackType(FeedBackTypeException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    public static class FeedBackTypeException extends Throwable {
        public FeedBackTypeException(String message) {
            super(message);
        }
    }

}
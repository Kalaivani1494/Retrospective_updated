package com.example.retrospective;
import com.example.retrospective.CustomExceptionHandler;
import com.example.retrospective.model.Feedback;
import com.example.retrospective.model.Retrospective;
import com.example.retrospective.service.RetrospectiveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RetrospectiveServiceTest {

    @Mock
    private Map<String, Retrospective> retrospectives;

    @InjectMocks
    private RetrospectiveService retrospectiveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRetrospective_WithValidRetrospective() {
        // Arrange
        Retrospective retrospective = new Retrospective();
        retrospective.setName("Retrospective Name");
        retrospective.setDate("2022-01-01");
        retrospective.setParticipants(List.of("Participant 1", "Participant 2"));

        // Act
        ResponseEntity<?> response = retrospectiveService.createRetrospective(retrospective);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateRetrospective_WithoutName() {
        // Arrange
        Retrospective retrospective = new Retrospective();
        retrospective.setDate("2022-01-01");
        retrospective.setParticipants(List.of("Participant 1", "Participant 2"));

        // Act
        ResponseEntity<?> response = retrospectiveService.createRetrospective(retrospective);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Retrospective cannot be created without a name.", response.getBody());
        verify(retrospectives, never()).put(anyString(), any(Retrospective.class));
    }

}

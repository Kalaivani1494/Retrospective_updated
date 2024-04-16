package com.example.retrospective;

import com.example.retrospective.CustomExceptionHandler;
import com.example.retrospective.controller.RetrospectiveController;
import com.example.retrospective.model.Retrospective;
import com.example.retrospective.model.Feedback;
import com.example.retrospective.service.RetrospectiveServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RetrospectiveControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RetrospectiveServiceInterface retrospectiveService;

    @InjectMocks
    private RetrospectiveController retrospectiveController;
    //private MockMvc mockMvc;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(retrospectiveController)
                .setControllerAdvice(new CustomExceptionHandler()) // Include your exception handler advice if needed
                .build();
    }

    @Test
    public void testCreateRetrospective() throws Exception {
        String payload = "{\"name\": \"Retrospective 2\", \"summary\": \"Post release retrospective\", \"date\": \"27/07/2022\", \"participants\": [\"Viktor\", \"Gareth\"]}";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/retrospectives/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testUpdateFeedbackItem() {
        String retrospectiveName = "Retrospective 1";
        Feedback feedback = new Feedback();
        feedback.setName("Participant 1");

        Retrospective expectedRetrospective = new Retrospective();
        expectedRetrospective.setName(retrospectiveName);

        when(retrospectiveService.updateFeedbackItem(eq(retrospectiveName), eq(feedback))).thenReturn(expectedRetrospective);

        ResponseEntity<Retrospective> response = retrospectiveController.updateFeedbackItem(retrospectiveName, feedback);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRetrospective, response.getBody());

        verify(retrospectiveService, times(1)).updateFeedbackItem(eq(retrospectiveName), eq(feedback));
    }

    @Test
    void testGetAllRetrospectives() {
        int page = 0;
        int pageSize = 10;

        List<Retrospective> expectedRetrospectives = Collections.singletonList(new Retrospective());
        when(retrospectiveService.getAllRetrospectives(eq(page), eq(pageSize))).thenReturn(expectedRetrospectives);

        ResponseEntity<List<Retrospective>> response = retrospectiveController.getAllRetrospectives(page, pageSize);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRetrospectives, response.getBody());

        verify(retrospectiveService, times(1)).getAllRetrospectives(eq(page), eq(pageSize));
    }

    @Test
    void testSearchRetrospectivesByDate() {
        String date = "2022-01-01";
        int page = 0;
        int pageSize = 10;

        List<Retrospective> expectedRetrospectives = Collections.singletonList(new Retrospective());
        when(retrospectiveService.searchRetrospectivesByDate(eq(date), eq(page), eq(pageSize))).thenReturn(expectedRetrospectives);

        ResponseEntity<List<Retrospective>> response = retrospectiveController.searchRetrospectivesByDate(date, page, pageSize);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRetrospectives, response.getBody());

        verify(retrospectiveService, times(1)).searchRetrospectivesByDate(eq(date), eq(page), eq(pageSize));
    }
}
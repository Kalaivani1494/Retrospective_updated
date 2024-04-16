package com.example.retrospective.controller;

import com.example.retrospective.CustomExceptionHandler;
import com.example.retrospective.model.Retrospective;
import com.example.retrospective.model.Feedback;
import com.example.retrospective.service.RetrospectiveServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Validated
@RequestMapping("/api/retrospectives")
public class RetrospectiveController {
    private final Logger logger = LoggerFactory.getLogger(RetrospectiveController.class);

    @Autowired
    private RetrospectiveServiceImpl retrospectiveService;
    private Retrospective retrospective;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Retrospective createRetrospective(@RequestBody Retrospective retrospective) throws CustomExceptionHandler.DataNotFoundException {
        logger.info("Creating a new retrospective: {}", retrospective);
        ResponseEntity<Retrospective> createdRetrospective = retrospectiveService.createRetrospective(retrospective);
        logger.info("Retrospective created with Name: {}", retrospective.getName());
        return retrospective;
    }

    @PostMapping("/{name}/feedback")
    public Retrospective addFeedbackItem(@PathVariable String name, @RequestBody Feedback feedback) throws CustomExceptionHandler.DataNotFoundException, CustomExceptionHandler.ParticipantNotAssociatedException, CustomExceptionHandler.FeedBackTypeException {
        logger.info("Adding feedback item for retrospective: {}", name);
        Retrospective updatedRetrospective = retrospectiveService.addFeedbackItem(name, feedback);
        logger.debug("Feedback item added successfully");
        return retrospective;
    }

    @PutMapping("/{name}/feedback/update")
    public Retrospective updateFeedbackItem(@PathVariable String name, @RequestBody Feedback feedback) {
        Retrospective updatedRetrospective = retrospectiveService.updateFeedbackItem(name, feedback);
        return retrospective;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Retrospective>> getAllRetrospectives(@RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("Retrieving all retrospectives");
        List<Retrospective> retrospectives = retrospectiveService.searchRetrospectivesByDate(date,page, pageSize);
        logger.debug("Retrieved {} retrospectives", retrospectives.size());
        return new ResponseEntity<>(retrospectives, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Retrospective>> searchRetrospectivesByDate(@RequestParam(value = "date", required = false) String date,
                                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<Retrospective> retrospectives = retrospectiveService.searchRetrospectivesByDate(date, page, pageSize);
        return new ResponseEntity<>(retrospectives, HttpStatus.OK);
    }
}

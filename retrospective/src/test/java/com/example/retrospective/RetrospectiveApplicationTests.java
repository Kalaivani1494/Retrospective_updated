package com.example.retrospective;

import com.example.retrospective.RetrospectiveControllerTest;
import com.example.retrospective.RetrospectiveServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest()
class RetrospectiveApplicationTests {

	@Test
	void runTests() throws CustomExceptionHandler.ParticipantNotAssociatedException, Exception {
		// Run the tests
		RetrospectiveControllerTest retrospectiveControllerTest = new RetrospectiveControllerTest();
		retrospectiveControllerTest.setUp();
		retrospectiveControllerTest.testCreateRetrospective();
		retrospectiveControllerTest.testUpdateFeedbackItem();
		retrospectiveControllerTest.testGetAllRetrospectives();
		retrospectiveControllerTest.testSearchRetrospectivesByDate();

		RetrospectiveServiceTest retrospectiveServiceTest = new RetrospectiveServiceTest();
		retrospectiveServiceTest.setUp();
		retrospectiveServiceTest.testCreateRetrospective_WithoutName();
		retrospectiveServiceTest.testCreateRetrospective_WithValidRetrospective();
	}

}
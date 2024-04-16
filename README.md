## Retrospective Service
This project is a RESTful web service implemented in Java using Spring Boot. It allows users to capture and query retrospective outcomes, which are used in SCRUM ceremonies to reflect on software delivery during (sprints). The service provides endpoints for creating new retrospectives, adding feedback items to retrospectives, updating feedback items, and searching retrospectives.

## Tools and Libraries Used
Java (IDE- IntelliJ)  
Spring Boot  
Maven  
JUnit and Mockito  
Postman: A tool for testing and interacting with the RESTful endpoints.  

## Installation
Clone the repository from GitHub.  
git clone https://github.com/Kalaivani1494/retrospective.git  
Navigate to the project directory.  
cd retrospective  

Build the project using Maven.  
Load Maven build script  
Run the application using below command: 
  Run RetrospectiveApplication.java  
The application will start running on http://localhost:8080.  

## API Endpoints
## Create New Retrospective
URL: /api/retrospectives/create  
Method: POST  
Request Body:  
json  
{  
  "name": "Retrospective1",  
  "summary": "Summary of the retrospective",  
  "date": "2022-01-01",  
  "participants": ["Participant 1", "Participant 2"],  
}  
Response: 201 (Created)  

## Add Feedback Items to Retrospective
URL: /api/retrospectives/{name}/feedback  
Method: POST  
Request Body:  
json  
{  
  "name": "Participant 1",  
  "body": "Feedback body",  
  "type": "positive"  
}  
Response: 200 (OK)  

## Update Feedback Items
URL: /api/retrospectives/name/feedback/update  
Method: PUT  
Request Body:  
json  
{  
  "name": "Participant 1",  
  "body": "Feedback body",  
  "type": "negative"  
}  
Response: 200 (OK)  

## Search Retrospectives
URL: /api/retrospectives/search  
Method: GET  
Query Parameters:  
date: <date> (Mandatory)  
page: Current page number (optional, default: 0)  
pageSize: Number of retrospectives per page (optional, default: 10)  
Response: 200 (OK)  
## Logging
The application includes basic logging for debugging and error handling purposes. Logs can be found in the console output.  

## Unit Testing
The application includes unit tests to ensure the correctness of the implemented endpoints. You can run the tests using the following command:  
Run RetrospectiveApplicationTests.java  

## Notes
Retrospective data is stored in memory and will be lost on application restart.  
The application follows RESTful principles, using appropriate HTTP methods for each endpoint.  
Pagination is supported for the search endpoints, allowing retrieval of retrospectives in chunks.

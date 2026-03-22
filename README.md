GitHub Access Report Service


Overview
This project is a Spring Boot based backend service that connects to the GitHub REST API and generates a structured report showing which users have access to which repositories within a given organization.
The service helps organizations gain visibility into repository access and simplifies access auditing.


Features
Authenticate with GitHub using a Personal Access Token
Retrieve repositories of a GitHub organization
Fetch collaborators for each repository
Generate aggregated mapping of User → List of Accessible Repositories
Expose a REST API endpoint that returns the access report in JSON format


Technology Stack
Java 17
Spring Boot
Maven
GitHub REST API
Jackson for JSON parsing


How to Run the Project
Step 1 — Clone the repository
Clone the project from GitHub and open it in IntelliJ IDEA.

Step 2 — Run the application
Run the main Spring Boot class named:
GithubAccessServiceApplication


Alternatively, the project can be run using Maven command:
mvn spring-boot:run

The application will start on:
http://localhost:8080


Authentication Configuration
The service uses a GitHub Personal Access Token to authenticate API requests.

To generate the token:
Go to GitHub → Settings → Developer Settings → Personal Access Tokens
Create a token with the repo scope enabled.

The token is currently passed as a query parameter in the API request.


API Endpoint
GET /api/github/access-report

Example request:
http://localhost:8080/api/github/access-report?org=your-organization-name&token=YOUR_TOKEN

Example response:

[
{
"username": "user1",
"repositories": ["repo-alpha", "repo-beta"]
},
{
"username": "user2",
"repositories": ["repo-beta"]
}
]


Design Decisions and Assumptions
The application follows a layered architecture consisting of Controller, Service, and Client layers to maintain clean code organization.
Repository collaborator APIs are called sequentially for simplicity and clarity of implementation.
Jackson ObjectMapper is used for parsing JSON responses from the GitHub API.
Basic exception handling has been implemented to manage API failures.


Scalability Considerations
To support large organizations with hundreds of repositories and thousands of users, the system can be improved by implementing:
Parallel API calls using asynchronous processing
Pagination handling for GitHub API responses
Caching frequently accessed repository data
Retry mechanisms for GitHub rate limit handling


Author
Aryan Tyagi

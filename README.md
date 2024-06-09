# CourseHelper - Backend

The project built with Spring Boot. It leverages OpenAI's GPT-3.5 model to assist users in validating code snippets.

## Prerequisites

Ensure you have the following prerequisites installed on your system:

- Integrated Development Environment (IDE), such as [IntelliJ](https://www.jetbrains.com/idea/download/?source=google&medium=cpc&campaign=EMEA_en_TR_IDEA_Branded&term=intellij&content=619479151433&gclid=CjwKCAiApuCrBhAuEiwA8VJ6JlQbcnH8jIklp-ZEi2X74TRKNA-Jz5cWjWwumwTgZQaHw7auozMrEhoCINEQAvD_BwE&section=windows).
- Java Development Kit (JDK) - You can install it by help of IntelliJ.

## Running the Application

### 1. Clone the repository.
### 2. Navigate to the project directory.
### 3. Run the app:
- You can run it simply by pressing run symobol in the main class "ItuApplication"

OR

- Running from the terminal
1. Open the terminal, then navigate to the root directory of the project.
2. Run the following Gradle command to build and run the application:

```
./gradlew bootRun
```
### 4. Access the Application
Open your web browser and navigate to http://localhost:8080 to access the Course Helper application.

## After Running
After successfully launching Course Helper, and the server is up and running, you can explore it by:

### Swagger for API Documentation

Use swagger to explore the available endpoints and try them out. Find it at:
http://localhost:8080/swagger-ui/index.html

## After Deployment

- You can access the backend side of kanver app by this link:
  http://a5b080a013c0447bfabb7043184ffa04-935611218.us-east-1.elb.amazonaws.com

OR

- Via Swagger : http://a5b080a013c0447bfabb7043184ffa04-935611218.us-east-1.elb.amazonaws.com/swagger-ui/index.html
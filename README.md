# 🌿 Spring Boot App Development Guide

Here's a step-by-step guide to developing a simple CRUD app with Spring Boot and PostgreSQL, from initial setup to testing endpoints, complete with hands-on examples.

## Index

- [Project Setup](#project-setup)
- [Configure PostgreSQL](#configure-postgresql)
- [Define Your Model](#define-your-model)
- [Repository Layer](#repository-layer)
- [Service Layer](#service-layer)
- [Controller Layer](#controller-layer)
- [Run Your Application](#run-your-application)
- [Test Your Application](#test-your-application)

## Project Setup

Use **Spring Initializr** (https://start.spring.io/) to generate your Spring Boot project skeleton.

- **Project:** Choose `Maven`.
- **Language:** Select `Java`.
- **Spring Boot:** Opt for a stable version, such as `3.2.3`.
- **Group:** Use your reversed domain (e.g., com.example).
- **Artifact:** Name it after your project (e.g., myapp).
- **Dependencies:** Include `Spring Web`, `Spring Data JPA`, and `PostgreSQL Driver`.

After downloading the zip file from Spring Initializr, extract it to your preferred location. Open the extracted folder in your IDE (like IntelliJ IDEA, Eclipse, or VS Code). Your IDE should recognize it as a Maven project and load it accordingly. Then, you can start adding your business logic, `entity classes`, `repositories`, `services`, and `controllers` to build your `CRUD` application.

## Configure PostgreSQL

In your `application.properties`, set up the connection to your PostgreSQL database with the correct URL, username, and password.

For PostgreSQL configuration in Spring Boot, set connection details in application.properties: use default port 5432 unless changed during PostgreSQL setup. Specify `database` `name`, `username`, and `password`. Ensure spring.jpa.hibernate.ddl-auto is set for database schema management.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yourDatabase
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword
spring.jpa.hibernate.ddl-auto=update
```

To run your Spring Boot app, navigate to the project's root directory in VS Code's integrated terminal. On Unix-based systems, use `./mvnw spring-boot:run`. For Windows, just `mvnw spring-boot:run` should work.

## Define Your Model

First add `Lombok` to a Spring Boot project, Lombok simplifies Java code by reducing boilerplate, such as getters and setters.

You need to include it as a dependency in your `pom.xml` file if you're using Maven. Add the following lines to the dependencies section:

```xml
 <dependency>
     <groupId>org.projectlombok</groupId>
     <artifactId>lombok</artifactId>
     <optional>true</optional>
 </dependency>
```

Create your entity class(es) in the model package.

```none
src/
└── main/
    └── java/
        └── com/
            └── yourcompany/
                └── yourapplication/
                    ├── model/ or domain/
                    │   └── Exercise.java
                    ├── repository/
                    ├── service/
                    ├── controller/
                    └── YourApplication.java
```

**Example:**

```java
package your.domain.trainerapp.model;

// Use Jakarta Persistence annotations
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

// Use @Entity to mark them as JPA entities.
@Entity

@Data
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
}
```

## Repository Layer

Create interfaces within the repository package, each corresponding to an entity like Exercise. Extend JpaRepository to leverage built-in CRUD operations, thus avoiding manual implementation. Additionally, you can define custom queries by simply declaring method signatures that follow Spring Data JPA naming conventions. Ensure to use the correct import import java.util.List; for collections like `List<Exercise>`, to avoid import conflicts. This layer acts as a bridge between your Java application and the database, facilitating data manipulation and retrieval with minimal boilerplate code.

**Example:**

```java
package your.domain.trainerapp.repository;

import your.domain.trainerapp.model.Exercise;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByName(String name);
    // Custom query methods can be added here
}
```

## Service Layer

In your service layer, organize classes within a `service` package, marking them with `@Service` for Spring's recognition and injecting dependencies like repositories via `@Autowired`. Prioritize constructor-based injection for better immutability and simplified testing, and employ `@NonNull` on parameters to enhance null safety, reducing potential `NullPointerExceptions`. Name services meaningfully, like `ExerciseService`, to reflect their domain-specific roles, ensuring clarity and effective encapsulation of business logic between data access and web layers.

**Example:**

```java
package your.domain.trainerapp.service;

import your.domain.trainerapp.model.Exercise;
import your.domain.trainerapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> findAllExercises() {
        return exerciseRepository.findAll();
    }

    public Optional<Exercise> findExerciseById(@NonNull Long id) {
        return exerciseRepository.findById(id);
    }

    public Exercise saveExercise(@NonNull Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(@NonNull Long id) {
        exerciseRepository.deleteById(id);
    }
}
```

## Controller Layer

In the Controller Layer, define classes with `@RestController` and map CRUD operations to HTTP requests using `@RequestMapping` or specific annotations like `@GetMapping`, `@PostMapping` and `@DeleteMapping`. Controllers act as the entry point for client requests, directing them to appropriate services for business logic execution and responding with the data or results, typically in `JSON` format.

**Example:**

```java
package your.domain.trainerapp.controller;

import your.domain.trainerapp.model.Exercise;
import your.domain.trainerapp.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<Exercise> getAllExercises() {
        return exerciseService.findAllExercises();
    }

    @GetMapping("/{id}")
    public Exercise getExerciseById(@PathVariable @NonNull Long id) {
        return exerciseService.findExerciseById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found with id: " + id));
    }

    @PostMapping
    public Exercise addExercise(@RequestBody @NonNull Exercise exercise) {
        return exerciseService.saveExercise(exercise);
    }

    @DeleteMapping("/{id}")
    public void deleteExercise(@PathVariable @NonNull Long id) {
        exerciseService.deleteExercise(id);
    }

    @PutMapping("/{id}")
    public Exercise updateExercise(@PathVariable @NonNull Long id, @RequestBody @NonNull Exercise exerciseDetails) {
        Exercise exercise = exerciseService.findExerciseById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found with id: " + id));

        exercise.setName(exerciseDetails.getName());
        exercise.setDescription(exerciseDetails.getDescription());

        return exerciseService.saveExercise(exercise);
    }
}
```

## Run Your Application

To run your Spring Boot application, execute `mvn spring-boot:run` in the command line for Maven projects, or use your IDE's run feature on the main class annotated with `@SpringBootApplication`. This starts your application and hosts it on the default port, allowing you to interact with it through your defined endpoints.

After running your application, use Thunder Client extension for VS Code, or any other tool to send HTTP resquest, to test each endpoint by setting up the appropriate HTTP method and including any required parameters or request bodies. This practical approach ensures your endpoints function as expected, providing a straightforward way to verify your application's behavior.

**Example:**

For testing a `POST` request with Thunder Client in VS Code, you'll create a JSON body that matches the Exercise entity's structure. An example JSON body could look like this:

```json
{
  "name": "Push Up",
  "description": "A basic push-up exercise."
}
```

In Thunder Client, set the HTTP method to `POST`, enter the endpoint URL (e.g., http://localhost:8080/exercises), and include this JSON in the request body. This will simulate creating a new Exercise entity in your application.

## Test Your Application

Create test cases in the test directory, use `@SpringBootTest` for comprehensive context loading and `@AutoConfigureMockMvc` to configure `MockMvc`, enabling web layer testing. Write tests to simulate HTTP requests, and validate responses to ensure endpoint functionality. This approach verifies that your application performs as expected, crucial for maintaining software reliability.

**Example:**

```java
package your.domain.trainerapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class ExerciseControllerTest {

     @Autowired
     private MockMvc mockMvc;

     @Test
     public void testAddExercise() throws Exception {
         MediaType mediaType = MediaType.APPLICATION_JSON;
         if (mediaType != null) {
             mockMvc.perform(post("/exercises")
             .contentType(mediaType)
             .content("{\"name\":\"Test Exercise\",\"description\":\"Test Description\"}"))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.name").value("Test Exercise"))
             .andExpect(jsonPath("$.description").value("Test Description"));
         }
     }
}
```

The test sends a `POST` request with a `JSON payload` to add a new exercise and asserts the response to ensure the `API` behaves as expected. This approach allows for comprehensive testing of web layers in isolation from the rest of the application, ensuring the reliability of `RESTful endpoints`.

## Next Steps

This guide provided a foundation for creating a CRUD application with Spring Boot and PostgreSQL. To build on what you've learned, delve deeper into Spring's extensive features and consider integrating additional technologies to broaden the scope and functionality of your projects.

### Consider the following areas:

- **Security**: Implement `Spring Security` to add authentication and authorization to your application. This can help you manage who has access to what parts of your application.

- **API Documentation**: Use tools like `Swagger` or `SpringDoc OpenAPI` to document your REST API. This makes it easier for other developers to understand and consume your API.

- **Advanced Database Operations**: Look into more complex JPA or Hibernate functionalities like `custom queries`, `pagination`, and `sorting` to enhance your data handling capabilities.

- **Containerization**: Use `Docker` to containerize your application. This simplifies deployment and scaling in cloud environments.

- **Frontend Integration**: If your app is backend-focused, consider developing a `frontend` using frameworks like Vue.js, React, or Angular for a full-stack application.

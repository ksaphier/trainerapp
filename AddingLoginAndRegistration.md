# Guide: Adding Login and Registration to Spring Boot API

## Table of Contents

1. [Introduction](#introduction)
2. [Project Setup](#project-setup)
3. [User Entity and Repository](#user-entity-and-repository)
4. [Registration Functionality](#registration-functionality)
5. [Login Functionality](#login-functionality)
6. [Security Configuration](#security-configuration)
7. [Exception Handling](#exception-handling)
8. [Testing the Functionality](#testing-the-functionality)
9. [Conclusion](#conclusion)

## Introduction

In this guide, we will walk you through the process of adding user registration and login functionality to your Spring Boot API. We will cover all the necessary steps, from setting up the project and creating the required entities and repositories, to implementing security measures using JWT (JSON Web Token) and handling exceptions. By the end of this guide, you will have a secure and functional user authentication system integrated into your Spring Boot application.

## Project Setup

### Setting up the project dependencies

Open your `pom.xml` file and add the following dependencies:

```xml
<dependencies>
    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- PostgreSQL Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Spring Boot Starter Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- Lombok for reducing boilerplate code -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- JWT for security -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.2</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.2</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.2</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### Configuring application properties

Create or open the application.properties file in the src/main/resources directory and add the following configuration:

```
spring.application.name=trainer-app
spring.datasource.url=jdbc:postgresql://localhost:5432/trainer-app
spring.datasource.username=username
spring.datasource.password=secretpass
spring.jpa.hibernate.ddl-auto=update

# JWT Configuration
jwt.secret=your_secret_key
jwt.expiration=86400000
```

Replace `your_database_username`, `your_database_password`, and `your_secret_key` with your actual `PostgreSQL` `username`, `password`, and a secret `key` for `JWT` respectively.

## User Entity and Repository

### Creating the User entity

Create a User entity that will represent the users in our application.

Inside the `model` package, create a class named `User.java` with the following content:

```java
package com.ksaphier.trainerapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    // Additional fields can be added as needed
}
```

### Setting up the User repository

Inside the repository package, create an interface named UserRepository.java with the following content:

```java
package com.ksaphier.trainerapp.repository;

import com.ksaphier.trainerapp.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    // Custom query methods can be added here
}
```

This interface will extend JpaRepository to provide CRUD operations for the User entity.

## Registration Functionality

To implement the registration functionality, we need to create a `data transfer object` (DTO) for registration, a `service` to handle the business logic, and a `controller` to handle HTTP requests.

1. Creating the Registration DTO

   Inside the dto package, create a class named `RegistrationRequest.java` with the following content:

   ```java
   package com.ksaphier.trainerapp.dto;

   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class RegistrationRequest {
       private String username;
       private String password;
       private String email;
   }
   ```

2. Implementing the Registration Service

   Inside the service package, create a class named UserService.java with the following content:

### Creating the registration DTO

### Implementing the registration service

### Creating the registration controller

## Login Functionality

### Creating the login DTO

### Implementing the login service

### Creating the login controller

## Security Configuration

### Adding Spring Security dependency

### Configuring Spring Security for JWT

### Implementing JWT token provider

## Exception Handling

### Creating custom exceptions

### Implementing global exception handler

## Testing the Functionality

### Writing unit tests for registration and login

### Writing integration tests for the API endpoints

## Conclusion

### Summary of the steps

### Next steps and further reading

```

```

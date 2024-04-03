# 🌿 Muscle Entity Integration Guide

This guide outlines the addition of the Muscle entity to the application, its many-to-many relationship with the Exercise entity, and the corresponding service, repository, and controller implementations.

## Index

- [Muscle Entity Definition](#muscle-entity-definition)
- [Repository Layer](#repository-layer)
- [Service Layer](#service-layer)
- [Controller Layer](#controller-layer)
- [Many-to-Many Relationship with Exercise](#many-to-many-relationship-with-exercise)

## Muscle Entity Definition

The Muscle entity is defined to represent various muscles that can be targeted by exercises. It has a many-to-many relationship with the Exercise entity.

**Example:**

```java
package com.ksaphier.trainerapp.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Muscle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    // Many-to-Many relationship with Exercise
    @ManyToMany(mappedBy = "muscles")
    private Set<Exercise> exercises;
}
```

## Repository Layer

The MuscleRepository extends JpaRepository and includes a custom method to find muscles by the ID of the exercise they are associated with.

**Example:**

```java
package com.ksaphier.trainerapp.repository;

import com.ksaphier.trainerapp.model.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MuscleRepository extends JpaRepository<Muscle, Long> {
    // Custom method to find muscles by exercise id
    @Query("SELECT m FROM Muscle m JOIN m.exercises e WHERE e.id = :exerciseId")
    List<Muscle> findByExercisesId(Long exerciseId);
}
```

## Service Layer

The MuscleService handles business logic related to muscles, including CRUD operations and fetching muscles associated with a specific exercise.

**Example:**

```java
package com.ksaphier.trainerapp.service;

import com.ksaphier.trainerapp.model.Muscle;
import com.ksaphier.trainerapp.repository.MuscleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MuscleService {

    private final MuscleRepository muscleRepository;

    @Autowired
    public MuscleService(MuscleRepository muscleRepository) {
        this.muscleRepository = muscleRepository;
    }

    public List<Muscle> findAllMuscles() {
        return muscleRepository.findAll();
    }

    public Optional<Muscle> findMuscleById(@NonNull Long id) {
        return muscleRepository.findById(id);
    }

    public Muscle saveMuscle(@NonNull Muscle muscle) {
        return muscleRepository.save(muscle);
    }

    public void deleteMuscle(@NonNull Long id) {
        muscleRepository.deleteById(id);
    }

    public List<Muscle> findMusclesByExerciseId(@NonNull Long exerciseId) {
        return muscleRepository.findByExercisesId(exerciseId);
    }
}
```

## Controller Layer

The MuscleController exposes endpoints for CRUD operations on muscles and fetching muscles associated with a specific exercise.

**Example:**

```java
package com.ksaphier.trainerapp.controller;

import com.ksaphier.trainerapp.model.Muscle;
import com.ksaphier.trainerapp.service.MuscleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/muscles")
public class MuscleController {

    private final MuscleService muscleService;

    @Autowired
    public MuscleController(MuscleService muscleService) {
        this.muscleService = muscleService;
    }

    @GetMapping
    public List<Muscle> getAllMuscles() {
        return muscleService.findAllMuscles();
    }

    @GetMapping("/{id}")
    public Muscle getMuscleById(@PathVariable @NonNull Long id) {
        return muscleService.findMuscleById(id)
                .orElseThrow(() -> new RuntimeException("Muscle not found with id: " + id));
    }

    @PostMapping
    public Muscle addMuscle(@RequestBody @NonNull Muscle muscle) {
        return muscleService.saveMuscle(muscle);
    }

    @DeleteMapping("/{id}")
    public void deleteMuscle(@PathVariable @NonNull Long id) {
        muscleService.deleteMuscle(id);
    }

    @PutMapping("/{id}")
    public Muscle updateMuscle(@PathVariable @NonNull Long id, @RequestBody @NonNull Muscle muscleDetails) {
        Muscle muscle = muscleService.findMuscleById(id)
                .orElseThrow(() -> new RuntimeException("Muscle not found with id: " + id));

        muscle.setName(muscleDetails.getName());
        muscle.setDescription(muscleDetails.getDescription());

        return muscleService.saveMuscle(muscle);
    }

    @GetMapping("/by-exercise/{exerciseId}")
    public List<Muscle> getMusclesByExerciseId(@PathVariable @NonNull Long exerciseId) {
        return muscleService.findMusclesByExerciseId(exerciseId);
    }
}
```

## Many-to-Many Relationship with Exercise

The many-to-many relationship between Muscle and Exercise entities is defined using JPA annotations. This relationship allows an exercise to target multiple muscles, and a muscle can be targeted by multiple exercises. The relationship is managed by a join table in the database, which is handled automatically by Hibernate.

- The @ManyToMany annotation in the Muscle entity uses the mappedBy attribute to designate the Exercise entity as the owning side of the relationship.
- In the Exercise entity, a corresponding Set<Muscle> should be defined without the mappedBy attribute to establish it as the owning side.

This guide provides a foundation for integrating the Muscle entity into your application, emphasizing the many-to-many relationship with the Exercise entity and the corresponding layers of abstraction.

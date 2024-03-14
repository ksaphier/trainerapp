package com.ksaphier.trainerapp.repository;

import com.ksaphier.trainerapp.model.Exercise;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByName(String name);
    // Custom query methods can be added here
}
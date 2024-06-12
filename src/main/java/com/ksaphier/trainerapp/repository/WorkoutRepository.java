package com.ksaphier.trainerapp.repository;

import com.ksaphier.trainerapp.model.Workout;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByName(String name);
    // Custom query methods can be added here
}
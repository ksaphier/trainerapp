package com.ksaphier.trainerapp.repository;

import com.ksaphier.trainerapp.model.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MuscleRepository extends JpaRepository<Muscle, Long> {
    // Custom method to find muscles by exercise name
    @Query("SELECT m FROM Muscle m JOIN m.exercises e WHERE e.id = :exerciseId")
    List<Muscle> findByExercisesId(Long exerciseId);
}
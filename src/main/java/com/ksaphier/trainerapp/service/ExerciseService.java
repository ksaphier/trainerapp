package com.ksaphier.trainerapp.service;

import com.ksaphier.trainerapp.model.Exercise;
import com.ksaphier.trainerapp.repository.ExerciseRepository;
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

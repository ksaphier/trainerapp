package com.ksaphier.trainerapp.service;

import com.ksaphier.trainerapp.model.Exercise;
import com.ksaphier.trainerapp.repository.ExerciseRepository;
import com.ksaphier.trainerapp.repository.WorkoutExerciseRepository;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

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

    @Transactional
    public void deleteExercise(@NonNull Long id) {
        workoutExerciseRepository.deleteByExerciseId(id);

        exerciseRepository.deleteById(id);
    }
}

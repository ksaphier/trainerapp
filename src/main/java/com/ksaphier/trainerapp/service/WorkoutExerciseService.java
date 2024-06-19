package com.ksaphier.trainerapp.service;

import com.ksaphier.trainerapp.repository.WorkoutExerciseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkoutExerciseService {

    @Autowired
    private final WorkoutExerciseRepository workoutExerciseRepository = null;

    @Transactional
    public void deleteExerciseFromWorkout(@NonNull Long id) {
        workoutExerciseRepository.deleteById(id);
    }
}

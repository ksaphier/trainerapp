package com.ksaphier.trainerapp.service;

import com.ksaphier.trainerapp.model.Workout;
import com.ksaphier.trainerapp.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public List<Workout> findAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Optional<Workout> findWorkoutById(@NonNull Long id) {
        return workoutRepository.findById(id);
    }

    public Workout saveWorkout(@NonNull Workout workout) {
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(@NonNull Long id) {
        workoutRepository.deleteById(id);
    }
}

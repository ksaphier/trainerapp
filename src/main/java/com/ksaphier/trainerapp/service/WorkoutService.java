package com.ksaphier.trainerapp.service;

import com.ksaphier.trainerapp.dto.SimplifiedExerciseDto;
import com.ksaphier.trainerapp.dto.WorkoutDetailsDto;
import com.ksaphier.trainerapp.model.Workout;
import com.ksaphier.trainerapp.model.WorkoutExercise;
import com.ksaphier.trainerapp.repository.WorkoutExerciseRepository;
import com.ksaphier.trainerapp.repository.WorkoutRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutDetailsDto getWorkoutDetails(Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));

        List<WorkoutExercise> workoutExercises = workoutExerciseRepository.findByWorkoutId(workoutId);
        List<SimplifiedExerciseDto> exercises = workoutExercises.stream()
                .map(we -> new SimplifiedExerciseDto(
                        we.getExercise().getId(),
                        we.getExercise().getName(),
                        we.getExercise().getDescription(),
                        we.getSeries(),
                        we.getReps(),
                        we.getRest(),
                        we.getWeight()))
                .collect(Collectors.toList());

        return new WorkoutDetailsDto(workout, exercises);
    }

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

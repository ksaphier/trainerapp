package com.ksaphier.trainerapp.service;

import com.ksaphier.trainerapp.dto.AddExerciseToWorkoutRequest;
import com.ksaphier.trainerapp.dto.SimplifiedExerciseDto;
import com.ksaphier.trainerapp.dto.WorkoutDetailsDto;
import com.ksaphier.trainerapp.model.Exercise;
import com.ksaphier.trainerapp.model.Workout;
import com.ksaphier.trainerapp.model.WorkoutExercise;
import com.ksaphier.trainerapp.repository.ExerciseRepository;
import com.ksaphier.trainerapp.repository.WorkoutExerciseRepository;
import com.ksaphier.trainerapp.repository.WorkoutRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                        we.getId(),
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
    ExerciseRepository exerciseRepository;

    public WorkoutExercise addExerciseToWorkout(AddExerciseToWorkoutRequest request) {
        Workout workout = workoutRepository.findById(request.getWorkoutId())
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));

        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setWorkout(workout);
        workoutExercise.setExercise(exercise);
        workoutExercise.setSeries(request.getSeries());
        workoutExercise.setReps(request.getReps());
        workoutExercise.setRest(request.getRest());
        workoutExercise.setWeight(request.getWeight());

        return workoutExerciseRepository.save(workoutExercise);
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

    @Transactional
    public void deleteWorkout(@NonNull Long id) {
        workoutExerciseRepository.deleteByWorkoutId(id);

        workoutRepository.deleteById(id);
    }
}

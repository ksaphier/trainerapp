package com.ksaphier.trainerapp.controller;

import com.ksaphier.trainerapp.dto.AddExerciseToWorkoutRequest;
import com.ksaphier.trainerapp.dto.WorkoutDetailsDto;
import com.ksaphier.trainerapp.model.Workout;
import com.ksaphier.trainerapp.model.WorkoutExercise;
import com.ksaphier.trainerapp.service.JwtTokenProvider;
import com.ksaphier.trainerapp.service.WorkoutExerciseService;
import com.ksaphier.trainerapp.service.WorkoutService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;
    private final WorkoutExerciseService workoutExerciseService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public WorkoutController(WorkoutService workoutService, WorkoutExerciseService workoutExerciseService,
            JwtTokenProvider jwtTokenProvider) {
        this.workoutService = workoutService;
        this.workoutExerciseService = workoutExerciseService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping
    public List<Workout> getAllWorkouts(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        return workoutService.findAllWorkoutsByUser(userId);
    }

    @GetMapping("/{id}")
    public Workout getWorkoutById(@PathVariable @NonNull Long id) {
        return workoutService.findWorkoutById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found with id: " + id));
    }

    @PostMapping
    public Workout addWorkout(@RequestBody @NonNull Workout workout, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        workout.setUserId(userId);
        return workoutService.saveWorkout(workout);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkout(@PathVariable @NonNull Long id) {
        workoutService.deleteWorkout(id);
    }

    @PutMapping("/{id}")
    public Workout updateWorkout(@PathVariable @NonNull Long id, @RequestBody @NonNull Workout workoutDetails) {
        Workout workout = workoutService.findWorkoutById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found with id: " + id));

        workout.setName(workoutDetails.getName());
        workout.setDescription(workoutDetails.getDescription());

        return workoutService.saveWorkout(workout);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<WorkoutDetailsDto> getWorkoutDetails(@PathVariable Long id) {
        WorkoutDetailsDto workoutDetails = workoutService.getWorkoutDetails(id);
        return ResponseEntity.ok(workoutDetails);
    }

    @PostMapping("/addExercise")
    public ResponseEntity<WorkoutExercise> addExerciseToWorkout(@RequestBody AddExerciseToWorkoutRequest request) {
        WorkoutExercise workoutExercise = workoutService.addExerciseToWorkout(request);
        return ResponseEntity.ok(workoutExercise);
    }

    @DeleteMapping("/deleteExercise/{id}")
    public void deleteExerciseFromWorkout(@PathVariable Long id) {
        workoutExerciseService.deleteExerciseFromWorkout(id);
    }
}

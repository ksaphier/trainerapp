package com.ksaphier.trainerapp.controller;

import com.ksaphier.trainerapp.model.Workout;
import com.ksaphier.trainerapp.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public List<Workout> getAllWorkouts() {
        return workoutService.findAllWorkouts();
    }

    @GetMapping("/{id}")
    public Workout getWorkoutById(@PathVariable @NonNull Long id) {
        return workoutService.findWorkoutById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found with id: " + id));
    }

    @PostMapping
    public Workout addWorkout(@RequestBody @NonNull Workout workout) {
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
}
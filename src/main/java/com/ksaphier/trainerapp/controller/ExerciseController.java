package com.ksaphier.trainerapp.controller;

import com.ksaphier.trainerapp.model.Exercise;
import com.ksaphier.trainerapp.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<Exercise> getAllExercises() {
        return exerciseService.findAllExercises();
    }

    @GetMapping("/{id}")
    public Exercise getExerciseById(@PathVariable @NonNull Long id) {
        return exerciseService.findExerciseById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found with id: " + id));
    }

    @PostMapping
    public Exercise addExercise(@RequestBody @NonNull Exercise exercise) {
        return exerciseService.saveExercise(exercise);
    }

    @DeleteMapping("/{id}")
    public void deleteExercise(@PathVariable @NonNull Long id) {
        exerciseService.deleteExercise(id);
    }

    @PutMapping("/{id}")
    public Exercise updateExercise(@PathVariable @NonNull Long id, @RequestBody @NonNull Exercise exerciseDetails) {
        Exercise exercise = exerciseService.findExerciseById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found with id: " + id));

        exercise.setName(exerciseDetails.getName());
        exercise.setDescription(exerciseDetails.getDescription());

        return exerciseService.saveExercise(exercise);
    }
}

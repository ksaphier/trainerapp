package com.ksaphier.trainerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddExerciseToWorkoutRequest {
    private Long workoutId;
    private Long exerciseId;
    private int series;
    private int reps;
    private int rest;
    private int weight;
}
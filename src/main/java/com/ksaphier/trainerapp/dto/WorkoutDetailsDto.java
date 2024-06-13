package com.ksaphier.trainerapp.dto;

import java.util.List;

import com.ksaphier.trainerapp.model.Workout;

import lombok.Data;

@Data
public class WorkoutDetailsDto {
    private Workout details;
    private List<SimplifiedExerciseDto> exercises;

    public WorkoutDetailsDto(Workout workout, List<SimplifiedExerciseDto> exercises) {
        this.details = workout;
        this.exercises = exercises;
    }
}
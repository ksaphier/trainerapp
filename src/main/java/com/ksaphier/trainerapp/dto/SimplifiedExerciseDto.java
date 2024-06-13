package com.ksaphier.trainerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimplifiedExerciseDto {
    private Long id;
    private String name;
    private String description;
    private int series;
    private int reps;
    private int rest;
    private int weight;
}
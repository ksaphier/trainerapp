package com.ksaphier.trainerapp.controller;

import com.ksaphier.trainerapp.model.Muscle;
import com.ksaphier.trainerapp.service.MuscleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/muscles")
public class MuscleController {

    private final MuscleService muscleService;

    @Autowired
    public MuscleController(MuscleService muscleService) {
        this.muscleService = muscleService;
    }

    @GetMapping
    public List<Muscle> getAllMuscles() {
        return muscleService.findAllMuscles();
    }

    @GetMapping("/{id}")
    public Muscle getMuscleById(@PathVariable @NonNull Long id) {
        return muscleService.findMuscleById(id)
                .orElseThrow(() -> new RuntimeException("Muscle not found with id: " + id));
    }

    @PostMapping
    public Muscle addMuscle(@RequestBody @NonNull Muscle muscle) {
        return muscleService.saveMuscle(muscle);
    }

    @DeleteMapping("/{id}")
    public void deleteMuscle(@PathVariable @NonNull Long id) {
        muscleService.deleteMuscle(id);
    }

    @PutMapping("/{id}")
    public Muscle updateMuscle(@PathVariable @NonNull Long id, @RequestBody @NonNull Muscle muscleDetails) {
        Muscle muscle = muscleService.findMuscleById(id)
                .orElseThrow(() -> new RuntimeException("Muscle not found with id: " + id));

        muscle.setName(muscleDetails.getName());
        muscle.setDescription(muscleDetails.getDescription());

        return muscleService.saveMuscle(muscle);
    }

    // New endpoint to get muscles by exercise ID
    @GetMapping("/by-exercise/{exerciseId}")
    public List<Muscle> getMusclesByExerciseId(@PathVariable @NonNull Long exerciseId) {
        return muscleService.findMusclesByExerciseId(exerciseId);
    }
}

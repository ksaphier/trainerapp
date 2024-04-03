package com.ksaphier.trainerapp.service;

import com.ksaphier.trainerapp.model.Muscle;
import com.ksaphier.trainerapp.repository.MuscleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MuscleService {

    private final MuscleRepository muscleRepository;

    @Autowired
    public MuscleService(MuscleRepository muscleRepository) {
        this.muscleRepository = muscleRepository;
    }

    public List<Muscle> findAllMuscles() {
        return muscleRepository.findAll();
    }

    public Optional<Muscle> findMuscleById(@NonNull Long id) {
        return muscleRepository.findById(id);
    }

    public Muscle saveMuscle(@NonNull Muscle muscle) {
        return muscleRepository.save(muscle);
    }

    public void deleteMuscle(@NonNull Long id) {
        muscleRepository.deleteById(id);
    }

    // New method to find muscles by exercise ID
    public List<Muscle> findMusclesByExerciseId(@NonNull Long exerciseId) {
        return muscleRepository.findByExercisesId(exerciseId);
    }
}

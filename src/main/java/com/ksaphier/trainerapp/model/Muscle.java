package com.ksaphier.trainerapp.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Muscle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    // Many-to-Many relationship with Exercise
    @ManyToMany(mappedBy = "muscles")
    private Set<Exercise> exercises;
}

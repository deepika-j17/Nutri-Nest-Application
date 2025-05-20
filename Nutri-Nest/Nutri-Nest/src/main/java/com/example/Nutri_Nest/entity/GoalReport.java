package com.example.Nutri_Nest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class GoalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reportDate;

    @NotNull
    private Double totalCaloriesConsumed;

    @NotNull
    private Double totalCaloriesBurned;

    @NotNull
    private Double weightChange;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

package com.example.Nutri_Nest.entity;

import com.example.Nutri_Nest.enums.ActivityType;
import com.example.Nutri_Nest.enums.Intensity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class FitnessActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Activity type is required")
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ActivityType workoutType; //Cardio, yoga, Muscles, cycling, basics

    @NotNull(message = "Duration is required")
    @Min(value = 5, message = "Duration must be at least 5 minute")
    private Integer duration; //minutes

    @NotNull(message = "Intensity is required")
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Intensity intensity; //low,medium,high

    @NotNull(message = "Number of Activities is required")
    @Min(value = 1, message = "Number of activities must be at least 1")
    private Integer noOfActivities; //count of different exercises performed in one workout session


    @PastOrPresent(message = "Workout date cannot be in the future")
    private LocalDate workoutDate;

    @NotNull(message = "Calories burned is required")
    private Double caloriesBurnt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required")
    private User user;
}

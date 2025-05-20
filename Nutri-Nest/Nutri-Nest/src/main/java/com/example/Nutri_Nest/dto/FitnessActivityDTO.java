package com.example.Nutri_Nest.dto;

import com.example.Nutri_Nest.enums.ActivityType;
import com.example.Nutri_Nest.enums.Intensity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FitnessActivityDTO {

    private Long id;

    @NotNull(message = "Activity type is required")
    private ActivityType workoutType;

    @NotNull(message = "Duration is required")
    @Min(value = 5, message = "Duration must be at least 5 minute")
    private Integer duration;

    @NotNull(message = "Intensity is required")
    private Intensity intensity;

    @NotNull(message = "Number of Activities is required")
    @Min(value = 1, message = "Number of activities must be at least 1")
    private Integer noOfActivities;

    @PastOrPresent(message = "Workout date cannot be in the future")
    private LocalDate workoutDate;

    @NotNull(message = "Calories burned is required")
    private Double caloriesBurnt;

    @NotNull(message = "User id is required")
    private Long userId;
}

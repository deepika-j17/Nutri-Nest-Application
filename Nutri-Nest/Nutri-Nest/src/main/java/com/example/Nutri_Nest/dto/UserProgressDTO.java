package com.example.Nutri_Nest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProgressDTO {
    private Long id;

    @NotNull(message = "Water intake is required")
    private Integer waterIntake;

    @NotNull(message = "Weight is required")
    private Double weightTracking;

    @NotNull(message = "Step count is required")
    private Integer steps;

    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    @NotNull(message = "User is is required")
    private Long userId;

    private Double caloriesBurnt;
}

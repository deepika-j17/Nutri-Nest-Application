package com.example.Nutri_Nest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GoalReportDTO {
    private Long id;
    private LocalDate reportDate;

    @NotNull
    private Double totalCaloriesConsumed;

    @NotNull
    private Double totalCaloriesBurned;

    @NotNull
    private Double weightChange;
    private Long userId;
}

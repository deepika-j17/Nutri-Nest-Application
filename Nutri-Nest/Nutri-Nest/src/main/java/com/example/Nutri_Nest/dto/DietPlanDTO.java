package com.example.Nutri_Nest.dto;

import com.example.Nutri_Nest.enums.MealType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietPlanDTO {

private Long id;

    @NotNull(message = "Meal type is required")
    private MealType mealType;

    private Double totalCalories;
    private Double totalProtein;
    private Double totalFat;
    private Double totalFiber;

    @PastOrPresent(message = "Diet date cannot be in the future")
    private LocalDate dietDate;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Food items are required")
    @NotEmpty(message = "Food items cannot be empty")
    private List<Long> foodItemIds;

}

package com.example.Nutri_Nest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemDTO {

    private Long id;

    @NotNull(message = "Food name is required")
    private String foodName;

    @NotNull(message = "Calories cannot be null")
    private Double calories;

    @NotNull(message = "Protein cannot be null")
    private Double protein;

    @NotNull(message = "Fat cannot be null")
    private Double fat;

    @NotNull(message = "Fiber cannot be null")
    private Double fiber;
}

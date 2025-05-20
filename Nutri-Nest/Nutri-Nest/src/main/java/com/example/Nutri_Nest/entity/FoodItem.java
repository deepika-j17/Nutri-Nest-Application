package com.example.Nutri_Nest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Food name is required")
    private String foodName;

    // Nutritional values per 100g
    @NotNull(message = "Calories cannot be null")
    private Double calories;

    @NotNull(message = "Protein cannot be null")
    private Double protein;

    @NotNull(message = "Fat cannot be null")
    private Double fat;

    @NotNull(message = "Fiber cannot be null")
    private Double fiber;

}

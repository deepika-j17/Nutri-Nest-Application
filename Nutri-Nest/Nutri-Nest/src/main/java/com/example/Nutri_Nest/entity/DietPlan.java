package com.example.Nutri_Nest.entity;

import com.example.Nutri_Nest.enums.MealType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Meal type is required")
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MealType mealType; //breakfast, lunch, dinner, snack

    @Column(name = "totalCalories", nullable = false)
    private Double totalCalories; //daily intake of calories
    @Column(name = "totalFiber", nullable = false)
    private  Double totalFiber;
    @Column(name = "totalFat", nullable = false)
    private Double totalFat;
    @Column(name = "totalProtein", nullable = false)
    private Double totalProtein;

    @PastOrPresent(message = "Diet date cannot be in the future")
    private LocalDate dietDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "dietplan_fooditems",
            joinColumns = @JoinColumn(name = "diet_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "food_item_id")
    )
    private List<FoodItem> foodItems;

}

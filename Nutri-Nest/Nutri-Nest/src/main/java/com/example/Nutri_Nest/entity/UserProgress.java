package com.example.Nutri_Nest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Water intake is required")
    private Integer waterIntake;

    @NotNull(message = "Weight is required")
    private Double weightTracking;

    @NotNull(message = "Step count is required")
    private Integer steps;

    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    private Double caloriesBurnt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required")
    private User user;
}

package com.example.Nutri_Nest.exception;

public class DietPlanNotFoundException extends RuntimeException {
    public DietPlanNotFoundException(String message) {
        super(message);
    }
}


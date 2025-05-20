package com.example.Nutri_Nest.dto;

import com.example.Nutri_Nest.enums.Goal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 12, message = "Age must be at least 12")
    private Integer age;

    @NotNull(message = "Height is required")
    @Positive(message = "Height must be positive")
    private Double height;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;

    @NotNull(message = "Goal is required")
    private Goal goal; //loss,gain,maintain
}

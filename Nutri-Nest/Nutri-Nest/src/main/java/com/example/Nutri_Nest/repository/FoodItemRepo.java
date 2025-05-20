package com.example.Nutri_Nest.repository;
import com.example.Nutri_Nest.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepo extends JpaRepository<FoodItem, Long> {
}

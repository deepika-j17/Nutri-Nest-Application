package com.example.Nutri_Nest.repository;

import com.example.Nutri_Nest.entity.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietPlanRepo extends JpaRepository<DietPlan,Long> {
    List<DietPlan> findByUserId(Long userId);
}

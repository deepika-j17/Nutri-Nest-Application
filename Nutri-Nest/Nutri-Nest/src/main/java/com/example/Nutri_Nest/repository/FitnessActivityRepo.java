package com.example.Nutri_Nest.repository;

import com.example.Nutri_Nest.entity.FitnessActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FitnessActivityRepo extends JpaRepository<FitnessActivity, Long> {
    List<FitnessActivity> findByUserId(Long userId);
}

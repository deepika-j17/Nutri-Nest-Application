package com.example.Nutri_Nest.repository;

import com.example.Nutri_Nest.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProgressRepo extends JpaRepository<UserProgress, Long> {
    List<UserProgress> findByUserId(Long userId);
}

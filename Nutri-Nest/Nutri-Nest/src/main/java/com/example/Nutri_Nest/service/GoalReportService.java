package com.example.Nutri_Nest.service;

import com.example.Nutri_Nest.entity.*;
import com.example.Nutri_Nest.exception.UserNotFoundException;
import com.example.Nutri_Nest.exception.UserProgressNotFoundException;
import com.example.Nutri_Nest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class GoalReportService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserProgressRepo userProgressRepo;

    @Autowired
    private FitnessActivityRepo fitnessActivityRepo;

    @Autowired
    private DietPlanRepo dietPlanRepo;

    @Autowired
    private GoalReportRepo goalReportRepo;

    public GoalReport generateGoalReportForUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        LocalDate today = LocalDate.now();

        // Calories Burnt
        double burntFromProgress = userProgressRepo.findByUserId(userId).stream()
                .filter(p -> p.getDate().equals(today))
                .mapToDouble(UserProgress::getCaloriesBurnt)
                .sum();

        double burntFromActivities = fitnessActivityRepo.findByUserId(userId).stream()
                .filter(f -> f.getWorkoutDate().equals(today))
                .mapToDouble(FitnessActivity::getCaloriesBurnt)
                .sum();

        double totalBurnt = burntFromProgress + burntFromActivities;

        // Calories Consumed
        double totalConsumed = dietPlanRepo.findByUserId(userId).stream()
                .filter(d -> d.getDietDate().equals(today))
                .mapToDouble(DietPlan::getTotalCalories)
                .sum();

        // Weight Change
        UserProgress latestProgress = userProgressRepo.findByUserId(userId).stream()
                .max(Comparator.comparing(UserProgress::getDate))
                .orElseThrow(() -> new UserProgressNotFoundException("No progress found for user ID " + userId));

        double weightChange = latestProgress.getWeightTracking() - user.getWeight();

        // Save Report
        GoalReport report = new GoalReport();
        report.setReportDate(today);
        report.setTotalCaloriesBurned(totalBurnt);
        report.setTotalCaloriesConsumed(totalConsumed);
        report.setWeightChange(weightChange);
        report.setUser(user);

        return goalReportRepo.save(report);
    }

    public List<GoalReport> getAllGoalReports() {
        return goalReportRepo.findAll();
    }

}


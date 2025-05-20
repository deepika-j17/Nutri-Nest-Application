package com.example.Nutri_Nest.controller;

import com.example.Nutri_Nest.entity.GoalReport;
import com.example.Nutri_Nest.exception.UserNotFoundException;
import com.example.Nutri_Nest.service.GoalReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrinest/goalReport")
public class GoalReportController {

    @Autowired
    private GoalReportService goalReportService;

    @PostMapping("/{userId}")
    public ResponseEntity<GoalReport> generateReport(@Valid @PathVariable Long userId) {
        GoalReport goalReportDTO = goalReportService.generateGoalReportForUser(userId);
        if (goalReportDTO == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        return new ResponseEntity<>(goalReportDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GoalReport>> getAllReports() {
        List<GoalReport> reports = goalReportService.getAllGoalReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

}

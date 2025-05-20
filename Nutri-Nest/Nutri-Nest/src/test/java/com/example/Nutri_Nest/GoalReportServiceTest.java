package com.example.Nutri_Nest;

import com.example.Nutri_Nest.service.*;
import com.example.Nutri_Nest.entity.*;
import com.example.Nutri_Nest.exception.UserNotFoundException;
import com.example.Nutri_Nest.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoalReportServiceTest {

    @InjectMocks
    private GoalReportService goalReportService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserProgressRepo userProgressRepo;

    @Mock
    private FitnessActivityRepo fitnessActivityRepo;

    @Mock
    private DietPlanRepo dietPlanRepo;

    @Mock
    private GoalReportRepo goalReportRepo;

    private User user;
    private UserProgress progress;
    private FitnessActivity activity;
    private DietPlan dietPlan;
    private GoalReport savedReport;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setWeight(60.0);

        progress = new UserProgress();
        progress.setUser(user);
        progress.setDate(LocalDate.now());
        progress.setCaloriesBurnt(200.0);
        progress.setWeightTracking(62.0);

        activity = new FitnessActivity();
        activity.setUser(user);
        activity.setWorkoutDate(LocalDate.now());
        activity.setCaloriesBurnt(150.0);

        dietPlan = new DietPlan();
        dietPlan.setUser(user);
        dietPlan.setDietDate(LocalDate.now());
        dietPlan.setTotalCalories(1800.0);

        savedReport = new GoalReport();
        savedReport.setUser(user);
        savedReport.setReportDate(LocalDate.now());
        savedReport.setTotalCaloriesBurned(350.0);
        savedReport.setTotalCaloriesConsumed(1800.0);
        savedReport.setWeightChange(2.0);
    }

    @Test
    void testGenerateGoalReportForUser_Success() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userProgressRepo.findByUserId(1L)).thenReturn(List.of(progress));
        when(fitnessActivityRepo.findByUserId(1L)).thenReturn(List.of(activity));
        when(dietPlanRepo.findByUserId(1L)).thenReturn(List.of(dietPlan));
        when(goalReportRepo.save(any(GoalReport.class))).thenReturn(savedReport);

        GoalReport report = goalReportService.generateGoalReportForUser(1L);

        assertNotNull(report);
        assertEquals(350, report.getTotalCaloriesBurned());
        assertEquals(1800, report.getTotalCaloriesConsumed());
        assertEquals(2.0, report.getWeightChange());
        assertEquals(user, report.getUser());

        verify(goalReportRepo, times(1)).save(any(GoalReport.class));
    }

    @Test
    void testGenerateGoalReportForUser_UserNotFound() {
        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> goalReportService.generateGoalReportForUser(99L));
    }

    @Test
    void testGetAllGoalReports() {
        GoalReport report1 = new GoalReport();
        GoalReport report2 = new GoalReport();

        when(goalReportRepo.findAll()).thenReturn(List.of(report1, report2));

        List<GoalReport> reports = goalReportService.getAllGoalReports();

        assertEquals(2, reports.size());
        verify(goalReportRepo, times(1)).findAll();
    }
}
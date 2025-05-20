package com.example.Nutri_Nest.viewcontroller;

import com.example.Nutri_Nest.entity.GoalReport;
import com.example.Nutri_Nest.service.GoalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GoalReportViewController {

    @Autowired
    private GoalReportService goalReportService;

    @GetMapping("/goalReports/view")
    public String viewGoalReports(Model model) {
        List<GoalReport> reports = goalReportService.getAllGoalReports();
        model.addAttribute("reports", reports);
        return "goal_reports";
    }
}

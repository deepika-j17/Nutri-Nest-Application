//package com.example.Nutri_Nest.viewcontroller;
//import com.example.Nutri_Nest.dto.FitnessActivityDTO;
//import com.example.Nutri_Nest.enums.ActivityType;
//import com.example.Nutri_Nest.enums.Intensity;
//import com.example.Nutri_Nest.service.FitnessActivityService;
//import com.example.Nutri_Nest.service.UserService; // Assumes you have one for fetching users
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/view/fitness")
//public class FitnessActivityViewController {
//
//    @Autowired
//    private FitnessActivityService fitnessService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping
//    public String listFitness(Model model) {
//        model.addAttribute("fitnessList", fitnessService.getAllFitness().getBody());
//        return "fitness/list";
//    }
//
//    @GetMapping("/create")
//    public String showCreateForm(Model model) {
//        model.addAttribute("fitness", new FitnessActivityDTO());
//        model.addAttribute("users", userService.getAllUsers()); // Assumes this returns a list
//        model.addAttribute("types", ActivityType.values());
//        model.addAttribute("intensities", Intensity.values());
//        return "fitness/create";
//    }
//
//    @PostMapping("/save")
//    public String saveFitness(@ModelAttribute("fitness") @Valid FitnessActivityDTO dto,
//                              BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("users", userService.getAllUsers());
//            model.addAttribute("types", ActivityType.values());
//            model.addAttribute("intensities", Intensity.values());
//            return "fitness/create";
//        }
//        fitnessService.createFitness(dto);
//        return "redirect:/view/fitness";
//    }
//
//
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        FitnessActivityDTO dto = fitnessService.getFitnessById(id).getBody();
//        model.addAttribute("fitness", dto);
//        model.addAttribute("users", userService.getAllUsers());
//        model.addAttribute("types", ActivityType.values());
//        model.addAttribute("intensities", Intensity.values());
//        return "fitness/edit";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateFitness(@PathVariable Long id,
//                                @ModelAttribute("fitness") @Valid FitnessActivityDTO dto,
//                                BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("users", userService.getAllUsers());
//            model.addAttribute("types", ActivityType.values());
//            model.addAttribute("intensities", Intensity.values());
//            return "fitness/edit";
//        }
//        fitnessService.fullUpdateById(dto, id);
//        return "redirect:/view/fitness";
//    }
//
//
//    @GetMapping("/delete/{id}")
//    public String deleteFitness(@PathVariable Long id) {
//        fitnessService.deleteById(id);
//        return "redirect:/view/fitness";
//    }
//}
//

package com.example.Nutri_Nest.viewcontroller;

import com.example.Nutri_Nest.dto.FitnessActivityDTO;
import com.example.Nutri_Nest.enums.ActivityType;
import com.example.Nutri_Nest.service.FitnessActivityService;
import com.example.Nutri_Nest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/users/fitness")
public class FitnessActivityViewController {

    @Autowired
    private FitnessActivityService fitnessActivityService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listActivities(Model model) {
        List<FitnessActivityDTO> activities = fitnessActivityService.getAllFitness().getBody();
        model.addAttribute("activities", activities);
        model.addAttribute("activity", new FitnessActivityDTO()); // form backing object
        model.addAttribute("activityTypes", Arrays.asList(ActivityType.values()));
        model.addAttribute("users", userService.getAllUsers());
        return "fitnessactivity";
    }


    @GetMapping("/edit/{id}")
    public String editActivity(@PathVariable Long id, Model model) {
        FitnessActivityDTO dto = fitnessActivityService.getFitnessById(id).getBody();
        model.addAttribute("activity", dto);
        model.addAttribute("activities", fitnessActivityService.getAllFitness().getBody());
        model.addAttribute("activityTypes", Arrays.asList(ActivityType.values()));
        return "fitnessactivity";
    }

    @PostMapping("/save")
    public String saveActivity(@ModelAttribute("activity") FitnessActivityDTO dto) {
        if (dto.getId() == null) {
            fitnessActivityService.createFitness(dto);
        } else {
            fitnessActivityService.fullUpdateById(dto, dto.getId());
        }
            return "redirect:/users/fitness";
    }

    @GetMapping("/delete/{id}")
    public String deleteActivity(@PathVariable Long id) {
        fitnessActivityService.deleteById(id);
        return "redirect:/users/fitness";
    }
}
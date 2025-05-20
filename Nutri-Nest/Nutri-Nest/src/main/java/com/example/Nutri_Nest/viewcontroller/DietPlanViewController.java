//package com.example.Nutri_Nest.viewcontroller;
//
//import com.example.Nutri_Nest.dto.DietPlanDTO;
//import com.example.Nutri_Nest.service.DietPlanService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/view/dietplans")
//public class DietPlanViewController {
//
//    @Autowired
//    private DietPlanService dietPlanService;
//
//    @GetMapping
//    public String showDietPlans(Model model) {
//        List<DietPlanDTO> plans = dietPlanService.getAllDietPlans().getBody();
//        model.addAttribute("dietPlans", plans);
//        model.addAttribute("newDietPlan", new DietPlanDTO());
//        return "dietplans";
//    }
//
//    @PostMapping("/add")
//    public String addDietPlan(@ModelAttribute DietPlanDTO dto) {
//        dietPlanService.createDietPlan(dto);
//        return "redirect:/view/dietplans";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateDietPlan(@PathVariable Long id, @ModelAttribute DietPlanDTO dto) {
//        dietPlanService.fullUpdateDietPlan(id, dto);
//        return "redirect:/view/dietplans";
//    }
//
//
//    @GetMapping("/delete/{id}")
//    public String deleteDietPlan(@PathVariable Long id) {
//        dietPlanService.deleteDietPlanById(id);
//        return "redirect:/view/dietplans";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editDietPlan(@PathVariable Long id, Model model) {
//        DietPlanDTO plan = dietPlanService.getDietPlanById(id).getBody();
//        List<DietPlanDTO> plans = dietPlanService.getAllDietPlans().getBody();
//        model.addAttribute("dietPlans", plans);
//        model.addAttribute("newDietPlan", plan);
//        model.addAttribute("editing", true);
//        return "dietplans";
//    }
//}

package com.example.Nutri_Nest.viewcontroller;

import com.example.Nutri_Nest.dto.DietPlanDTO;
import com.example.Nutri_Nest.enums.MealType;
import com.example.Nutri_Nest.entity.FoodItem;
import com.example.Nutri_Nest.service.DietPlanService;
import com.example.Nutri_Nest.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users/dietplans")
public class DietPlanViewController {

    @Autowired
    private DietPlanService dietPlanService;

    @Autowired
    private FoodItemService foodItemService;

    @GetMapping
    public String listDietPlans(Model model) {
        List<DietPlanDTO> dietPlans = dietPlanService.getAllDietPlans().getBody();
        model.addAttribute("dietPlans", dietPlans);
        model.addAttribute("dietPlan", new DietPlanDTO());
        model.addAttribute("mealTypes", Arrays.asList(MealType.values()));
        return "dietplan";
    }

    @GetMapping("/edit/{id}")
    public String editDietPlan(@PathVariable Long id, Model model) {
        DietPlanDTO dto = dietPlanService.getDietPlanById(id).getBody();
        model.addAttribute("dietPlan", dto);
        model.addAttribute("dietPlans", dietPlanService.getAllDietPlans().getBody());
        model.addAttribute("mealTypes", Arrays.asList(MealType.values()));
        return "dietplan";
    }

    @PostMapping("/save")
        public String saveDietPlan(@ModelAttribute("dietPlan") DietPlanDTO dto,
                                   @RequestParam("foodItemIdsStr") String foodItemIdsStr) {

            List<Long> foodItemIds = Arrays.stream(foodItemIdsStr.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            dto.setFoodItemIds(foodItemIds);

            List<FoodItem> foodItems = foodItemService.findAllByIds(foodItemIds);

            Double totalCalories = foodItems.stream().mapToDouble(FoodItem::getCalories).sum();
            Double totalProtein = foodItems.stream().mapToDouble(FoodItem::getProtein).sum();
            Double totalFat     = foodItems.stream().mapToDouble(FoodItem::getFat).sum();
            Double totalFiber   = foodItems.stream().mapToDouble(FoodItem::getFiber).sum();

            dto.setTotalCalories(totalCalories);
            dto.setTotalProtein(totalProtein);
            dto.setTotalFat(totalFat);
            dto.setTotalFiber(totalFiber);

            if (dto.getId() == null) {
                dietPlanService.createDietPlan(dto);
            } else {
                dietPlanService.fullUpdateDietPlan(dto.getId(), dto);
            }

            return "redirect:/users/dietplans";
        }


//    @PostMapping("/save")
//    public String saveDietPlan(@ModelAttribute DietPlan dietPlan,
//                               @RequestParam("foodItemIdsStr") String foodItemIdsStr,
//                               Model model) {
//        try {
//            // Convert comma-separated IDs to List<Long>
//            List<Long> foodItemIds = Arrays.stream(foodItemIdsStr.split(","))
//                    .map(String::trim)
//                    .map(Long::parseLong)
//                    .collect(Collectors.toList());
//
//            // Build DTO to use service logic
//            DietPlanDTO dto = new DietPlanDTO();
//            dto.setId(dietPlan.getId());
//            dto.setDietDate(dietPlan.getDietDate());
//            dto.setMealType(dietPlan.getMealType());
//            dto.setUserId(dietPlan.getUser().getId()); // if not null
//            dto.setFoodItemIds(foodItemIds);
//
//            dietPlanService.createDietPlan(dto);
//
//            return "redirect:/users/dietplans"; // success
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "Error saving diet plan: " + e.getMessage());
//            return "dietplan"; // return back to form with error
//        }
//    }


    @GetMapping("/delete/{id}")
    public String deleteDietPlan(@PathVariable Long id) {
        dietPlanService.deleteDietPlanById(id);
        return "redirect:/users/dietplans";
    }
}

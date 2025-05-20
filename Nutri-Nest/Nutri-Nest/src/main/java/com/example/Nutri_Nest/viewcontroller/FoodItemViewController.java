package com.example.Nutri_Nest.viewcontroller;
import com.example.Nutri_Nest.dto.FoodItemDTO;
import com.example.Nutri_Nest.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/view/fooditems")
public class FoodItemViewController {

    @Autowired
    private FoodItemService foodItemService;

    @GetMapping
    public String showFoodItems(Model model) {
        List<FoodItemDTO> foodItems = foodItemService.getAllFoodItems().getBody();
        model.addAttribute("foodItems", foodItems);
        model.addAttribute("newFoodItem", new FoodItemDTO());
        return "fooditems";
    }

    @PostMapping("/add")
    public String addFoodItem(@ModelAttribute FoodItemDTO foodItemDTO) {
        foodItemService.createFoodItem(foodItemDTO);
        return "redirect:/view/fooditems";
    }

    @PostMapping("/update/{id}")
    public String updateFoodItem(@PathVariable Long id, @ModelAttribute FoodItemDTO foodItemDTO) {
        foodItemService.updateFoodItem(id, foodItemDTO);
        return "redirect:/view/fooditems";
    }

    @GetMapping("/delete/{id}")
    public String deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItemById(id);
        return "redirect:/view/fooditems";
    }

    @GetMapping("/edit/{id}")
    public String editFoodItem(@PathVariable Long id, Model model) {
        FoodItemDTO item = foodItemService.getFoodItemById(id).getBody();
        List<FoodItemDTO> foodItems = foodItemService.getAllFoodItems().getBody();
        model.addAttribute("foodItems", foodItems);
        model.addAttribute("newFoodItem", item);
        model.addAttribute("editing", true);
        return "fooditems";
    }
}


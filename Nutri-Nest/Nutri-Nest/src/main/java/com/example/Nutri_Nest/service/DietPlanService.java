package com.example.Nutri_Nest.service;

import com.example.Nutri_Nest.dto.DietPlanDTO;
import com.example.Nutri_Nest.entity.DietPlan;
import com.example.Nutri_Nest.entity.FoodItem;
import com.example.Nutri_Nest.entity.User;
import com.example.Nutri_Nest.repository.DietPlanRepo;
import com.example.Nutri_Nest.repository.FoodItemRepo;
import com.example.Nutri_Nest.repository.UserRepo;
import com.example.Nutri_Nest.exception.DietPlanNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DietPlanService {

    @Autowired
    DietPlanRepo dietPlanRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private FoodItemRepo foodItemRepo;

    @Autowired
    private ModelMapper modelMapper;

    private void calculateNutrition(DietPlan dietPlan) {
        double calories = 0, protein = 0, fat = 0, fiber = 0;
        for (FoodItem item : dietPlan.getFoodItems()) {
            calories += item.getCalories();
            protein += item.getProtein();
            fat += item.getFat();
            fiber += item.getFiber();
        }
        dietPlan.setTotalCalories(calories);
        dietPlan.setTotalProtein(protein);
        dietPlan.setTotalFat(fat);
        dietPlan.setTotalFiber(fiber);
    }

    @Transactional
    public ResponseEntity<String> createDietPlan(DietPlanDTO dto) {
        DietPlan dietPlan = convertToEntity(dto);
        calculateNutrition(dietPlan);
        dietPlanRepo.save(dietPlan);
        return new ResponseEntity<>("Diet plan created successfully!", HttpStatus.CREATED);
    }

    public ResponseEntity<List<DietPlanDTO>> getAllDietPlans() {
        List<DietPlanDTO> plans = dietPlanRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    public ResponseEntity<DietPlanDTO> getDietPlanById(Long id) {
        DietPlan dietPlan = dietPlanRepo.findById(id)
                .orElseThrow(() -> new DietPlanNotFoundException("Diet plan not found with id: " + id));
        return new ResponseEntity<>(convertToDTO(dietPlan), HttpStatus.OK);
    }

    public ResponseEntity<String> fullUpdateDietPlan(Long id, DietPlanDTO dto) {
        DietPlan existing = dietPlanRepo.findById(id)
                .orElseThrow(() -> new DietPlanNotFoundException("Diet plan not found with id: " + id));

        DietPlan updated = convertToEntity(dto);
        updated.setId(id); // Ensure we're updating existing ID
        calculateNutrition(updated);
        dietPlanRepo.save(updated);
        return new ResponseEntity<>("Diet plan updated successfully!", HttpStatus.OK);
    }

    public ResponseEntity<String> partialUpdateDietPlan(Long id, DietPlanDTO dto) {
        DietPlan existing = dietPlanRepo.findById(id)
                .orElseThrow(() -> new DietPlanNotFoundException("Diet plan not found with id: " + id));

        if (dto.getDietDate() != null) existing.setDietDate(dto.getDietDate());
        if (dto.getMealType() != null) existing.setMealType(dto.getMealType());
        if (dto.getUserId() != null) {
            User user = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            existing.setUser(user);
        }
        if (dto.getFoodItemIds() != null) {
            List<FoodItem> items = foodItemRepo.findAllById(dto.getFoodItemIds());
            existing.setFoodItems(items);
        }

        calculateNutrition(existing);
        dietPlanRepo.save(existing);
        return new ResponseEntity<>("Diet plan partially updated!", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteDietPlanById(Long id) {
        if (dietPlanRepo.existsById(id)) {
            dietPlanRepo.deleteById(id);
            return new ResponseEntity<>("Diet plan with id " + id + " deleted successfully", HttpStatus.OK);
        } else {
            throw new DietPlanNotFoundException("Diet plan not found with id: " + id);
        }
    }

    public ResponseEntity<String> deleteAllDietPlans() {
        dietPlanRepo.deleteAll();
        return new ResponseEntity<>("All diet plans deleted successfully!", HttpStatus.OK);
    }

    private DietPlanDTO convertToDTO(DietPlan dietPlan) {
        DietPlanDTO dto = modelMapper.map(dietPlan, DietPlanDTO.class);
        dto.setUserId(dietPlan.getUser().getId());
        List<Long> foodItemIds = dietPlan.getFoodItems()
                .stream()
                .map(FoodItem::getId)
                .collect(Collectors.toList());
        dto.setFoodItemIds(foodItemIds);
        dto.setTotalCalories(round(dietPlan.getTotalCalories()));
        dto.setTotalProtein(round(dietPlan.getTotalProtein()));
        dto.setTotalFat(round(dietPlan.getTotalFat()));
        dto.setTotalFiber(round(dietPlan.getTotalFiber()));
        return dto;
    }

    private Double round(Double value) {
        return value != null ? Math.round(value * 100.0) / 100.0 : null;
    }
//    private Double round(Double value) {
//        return value != null ? BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue() : null;
//    }


    private DietPlan convertToEntity(DietPlanDTO dto) {
        DietPlan dietPlan = modelMapper.map(dto, DietPlan.class);

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));
        dietPlan.setUser(user);

        List<FoodItem> foodItems = foodItemRepo.findAllById(dto.getFoodItemIds());

        if (foodItems.size() != dto.getFoodItemIds().size()) {
            throw new RuntimeException("One or more food items are invalid.");
        }

        dietPlan.setFoodItems(foodItems);

        return dietPlan;
    }
}


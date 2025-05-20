package com.example.Nutri_Nest.controller;

import com.example.Nutri_Nest.dto.DietPlanDTO;
import com.example.Nutri_Nest.service.DietPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrinest/diet")
public class DietPlanController {

    @Autowired
    DietPlanService dietPlanService;

    @PostMapping("/admin/create")
    public ResponseEntity<String> createDietPlan(@Valid @RequestBody DietPlanDTO dietPlanDTO){
        return dietPlanService.createDietPlan(dietPlanDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DietPlanDTO>> getAllDietPlan(){
        return dietPlanService.getAllDietPlans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietPlanDTO> getDietPlanById(@PathVariable Long id){
        return dietPlanService.getDietPlanById(id);
    }

    // full update -> PUT
    @PutMapping("/admin/full-update/{id}")
    public ResponseEntity<String> fullUpdateById( @PathVariable Long id, @RequestBody DietPlanDTO dietPlanDTO) {
        return dietPlanService.fullUpdateDietPlan(id, dietPlanDTO);
    }

    // partial update -> PATCH
    @PatchMapping("/admin/update/{id}")
    public ResponseEntity<String> partialUpdateById(@PathVariable Long id,@RequestBody DietPlanDTO dietPlanDTO){
        return dietPlanService.partialUpdateDietPlan(id, dietPlanDTO);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteDietById(@PathVariable Long id){
        return dietPlanService.deleteDietPlanById(id);
    }

    @DeleteMapping("/admin/delete/all")
    public ResponseEntity<String> deleteAllDiets(){
        return dietPlanService.deleteAllDietPlans();
    }
}



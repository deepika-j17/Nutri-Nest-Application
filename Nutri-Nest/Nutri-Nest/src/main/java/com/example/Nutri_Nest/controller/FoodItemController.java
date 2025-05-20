package com.example.Nutri_Nest.controller;

import com.example.Nutri_Nest.dto.FoodItemDTO;
import com.example.Nutri_Nest.service.FoodItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/nutrinest/fooditems")
@CrossOrigin
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    @PostMapping("/admin/create")
    public ResponseEntity<String> create(@Valid @RequestBody FoodItemDTO dto) {
        return foodItemService.createFoodItem(dto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FoodItemDTO>> getAll() {
        return foodItemService.getAllFoodItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItemDTO> getById(@PathVariable Long id) {
        return foodItemService.getFoodItemById(id);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody FoodItemDTO dto) {
        return foodItemService.updateFoodItem(id, dto);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return foodItemService.deleteFoodItemById(id);
    }
}

package com.example.Nutri_Nest.service;

import com.example.Nutri_Nest.dto.FoodItemDTO;
import com.example.Nutri_Nest.entity.FoodItem;
import com.example.Nutri_Nest.repository.FoodItemRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodItemService {

    @Autowired
    private FoodItemRepo foodItemRepo;

    @Autowired
    private ModelMapper modelMapper;

    private FoodItemDTO convertToDTO(FoodItem item) {
        return modelMapper.map(item, FoodItemDTO.class);
    }

    private FoodItem convertToEntity(FoodItemDTO dto) {
        return modelMapper.map(dto, FoodItem.class);
    }

    public ResponseEntity<String> createFoodItem(FoodItemDTO dto) {
        foodItemRepo.save(convertToEntity(dto));
        return new ResponseEntity<>("Food item added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<FoodItemDTO>> getAllFoodItems() {
        List<FoodItemDTO> list = foodItemRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<FoodItemDTO> getFoodItemById(Long id) {
        FoodItem item = foodItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));
        return new ResponseEntity<>(convertToDTO(item), HttpStatus.OK);
    }

    public List<FoodItem> findAllByIds(List<Long> ids) {
        return foodItemRepo.findAllById(ids);
    }


    public ResponseEntity<String> updateFoodItem(Long id, FoodItemDTO updatedDto) {
        FoodItem existing = foodItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        existing.setFoodName(updatedDto.getFoodName());
        existing.setCalories(updatedDto.getCalories());
        existing.setProtein(updatedDto.getProtein());
        existing.setFat(updatedDto.getFat());
        existing.setFiber(updatedDto.getFiber());

        foodItemRepo.save(existing);
        return new ResponseEntity<>("Food item updated successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteFoodItemById(Long id) {
        if (foodItemRepo.existsById(id)) {
            foodItemRepo.deleteById(id);
            return new ResponseEntity<>("Food item deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Food item not found", HttpStatus.NOT_FOUND);
        }
    }
}

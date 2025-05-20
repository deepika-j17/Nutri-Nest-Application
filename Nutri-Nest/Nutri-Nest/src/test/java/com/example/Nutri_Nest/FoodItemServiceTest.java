package com.example.Nutri_Nest;

import com.example.Nutri_Nest.dto.FoodItemDTO;
import com.example.Nutri_Nest.entity.FoodItem;
import com.example.Nutri_Nest.repository.FoodItemRepo;
import com.example.Nutri_Nest.service.FoodItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodItemServiceTest {

    private FoodItemRepo foodItemRepo;
    private ModelMapper modelMapper;
    private FoodItemService foodItemService;

    private FoodItem foodItem;
    private FoodItemDTO foodItemDTO;

    @BeforeEach
    void setUp() {
        foodItemRepo = mock(FoodItemRepo.class);
        modelMapper = new ModelMapper();
        foodItemService = new FoodItemService();

        ReflectionTestUtils.setField(foodItemService, "foodItemRepo", foodItemRepo);
        ReflectionTestUtils.setField(foodItemService, "modelMapper", modelMapper);

        foodItem = new FoodItem(1L, "Apple", 95.0, 0.5, 0.3, 4.0);
        foodItemDTO = new FoodItemDTO(1L, "Apple", 95.0, 0.5, 0.3, 4.0);
    }

    @Test
    void testCreateFoodItem() {
        when(foodItemRepo.save(any(FoodItem.class))).thenReturn(foodItem);
        ResponseEntity<String> response = foodItemService.createFoodItem(foodItemDTO);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Food item added successfully", response.getBody());
    }

    @Test
    void testGetAllFoodItems() {
        List<FoodItem> itemList = Arrays.asList(foodItem);
        when(foodItemRepo.findAll()).thenReturn(itemList);

        ResponseEntity<List<FoodItemDTO>> response = foodItemService.getAllFoodItems();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Apple", response.getBody().get(0).getFoodName());
    }

    @Test
    void testGetFoodItemById_Found() {
        when(foodItemRepo.findById(1L)).thenReturn(Optional.of(foodItem));
        ResponseEntity<FoodItemDTO> response = foodItemService.getFoodItemById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Apple", response.getBody().getFoodName());
    }

    @Test
    void testGetFoodItemById_NotFound() {
        when(foodItemRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> foodItemService.getFoodItemById(1L));
    }

    @Test
    void testUpdateFoodItem_Found() {
        when(foodItemRepo.findById(1L)).thenReturn(Optional.of(foodItem));
        when(foodItemRepo.save(any(FoodItem.class))).thenReturn(foodItem);

        FoodItemDTO updatedDTO = new FoodItemDTO(1L, "Banana", 105.0, 1.3, 0.4, 3.1);
        ResponseEntity<String> response = foodItemService.updateFoodItem(1L, updatedDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Food item updated successfully", response.getBody());
    }

    @Test
    void testUpdateFoodItem_NotFound() {
        when(foodItemRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> foodItemService.updateFoodItem(1L, foodItemDTO));
    }

    @Test
    void testDeleteFoodItemById_Found() {
        when(foodItemRepo.existsById(1L)).thenReturn(true);
        ResponseEntity<String> response = foodItemService.deleteFoodItemById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Food item deleted successfully", response.getBody());
    }

    @Test
    void testDeleteFoodItemById_NotFound() {
        when(foodItemRepo.existsById(1L)).thenReturn(false);
        ResponseEntity<String> response = foodItemService.deleteFoodItemById(1L);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Food item not found", response.getBody());
    }
}

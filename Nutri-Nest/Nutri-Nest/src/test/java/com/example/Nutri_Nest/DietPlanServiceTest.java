package com.example.Nutri_Nest;

import com.example.Nutri_Nest.dto.DietPlanDTO;
import com.example.Nutri_Nest.entity.DietPlan;
import com.example.Nutri_Nest.entity.FoodItem;
import com.example.Nutri_Nest.entity.User;
import com.example.Nutri_Nest.enums.MealType;
import com.example.Nutri_Nest.exception.DietPlanNotFoundException;
import com.example.Nutri_Nest.repository.DietPlanRepo;
import com.example.Nutri_Nest.repository.FoodItemRepo;
import com.example.Nutri_Nest.repository.UserRepo;
import com.example.Nutri_Nest.service.DietPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DietPlanServiceTest {

    @InjectMocks
    private DietPlanService dietPlanService;

    @Mock
    private DietPlanRepo dietPlanRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private FoodItemRepo foodItemRepo;

    @Mock
    private ModelMapper modelMapper;

    private DietPlanDTO dto;
    private DietPlan dietPlan;
    private User user;
    private FoodItem item;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        item = new FoodItem();
        item.setId(1L);
        item.setCalories(200.0);
        item.setProtein(10.0);
        item.setFat(5.0);
        item.setFiber(3.0);

        dto = new DietPlanDTO();
        dto.setUserId(1L);
        dto.setFoodItemIds(Collections.singletonList(1L));

        dietPlan = new DietPlan();
        dietPlan.setId(1L);
        dietPlan.setUser(user);
        dietPlan.setFoodItems(Collections.singletonList(item));
    }

    @Test
    void testCreateDietPlan() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(foodItemRepo.findAllById(dto.getFoodItemIds())).thenReturn(Collections.singletonList(item));
        when(modelMapper.map(dto, DietPlan.class)).thenReturn(dietPlan);

        ResponseEntity<String> response = dietPlanService.createDietPlan(dto);

        assertEquals(201, response.getStatusCodeValue());
        verify(dietPlanRepo, times(1)).save(any(DietPlan.class));
    }

    @Test
    void testGetAllDietPlans() {
        when(dietPlanRepo.findAll()).thenReturn(Collections.singletonList(dietPlan));
        when(modelMapper.map(any(DietPlan.class), eq(DietPlanDTO.class))).thenReturn(dto);

        ResponseEntity<?> response = dietPlanService.getAllDietPlans();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetDietPlanByIdFound() {
        when(dietPlanRepo.findById(1L)).thenReturn(Optional.of(dietPlan));
        when(modelMapper.map(dietPlan, DietPlanDTO.class)).thenReturn(dto);

        ResponseEntity<DietPlanDTO> response = dietPlanService.getDietPlanById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testGetDietPlanByIdNotFound() {
        when(dietPlanRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(DietPlanNotFoundException.class, () -> dietPlanService.getDietPlanById(2L));
    }

    @Test
    void testFullUpdateDietPlan() {
        when(dietPlanRepo.findById(1L)).thenReturn(Optional.of(dietPlan));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(foodItemRepo.findAllById(dto.getFoodItemIds())).thenReturn(Collections.singletonList(item));
        when(modelMapper.map(dto, DietPlan.class)).thenReturn(dietPlan);

        ResponseEntity<String> response = dietPlanService.fullUpdateDietPlan(1L, dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testPartialUpdateDietPlan() {
        dto.setMealType(MealType.BREAKFAST);
        when(dietPlanRepo.findById(1L)).thenReturn(Optional.of(dietPlan));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(foodItemRepo.findAllById(dto.getFoodItemIds())).thenReturn(Collections.singletonList(item));

        ResponseEntity<String> response = dietPlanService.partialUpdateDietPlan(1L, dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteDietPlanByIdFound() {
        when(dietPlanRepo.existsById(1L)).thenReturn(true);
        ResponseEntity<String> response = dietPlanService.deleteDietPlanById(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(dietPlanRepo).deleteById(1L);
    }

    @Test
    void testDeleteDietPlanByIdNotFound() {
        when(dietPlanRepo.existsById(2L)).thenReturn(false);
        assertThrows(DietPlanNotFoundException.class, () -> dietPlanService.deleteDietPlanById(2L));
    }

    @Test
    void testDeleteAllDietPlans() {
        ResponseEntity<String> response = dietPlanService.deleteAllDietPlans();
        assertEquals(200, response.getStatusCodeValue());
        verify(dietPlanRepo).deleteAll();
    }
}

package com.example.Nutri_Nest;

import com.example.Nutri_Nest.dto.FitnessActivityDTO;
import com.example.Nutri_Nest.entity.FitnessActivity;
import com.example.Nutri_Nest.entity.User;
import com.example.Nutri_Nest.enums.ActivityType;
import com.example.Nutri_Nest.enums.Intensity;
import com.example.Nutri_Nest.exception.FitnessActivityNotFoundException;
import com.example.Nutri_Nest.exception.UserNotFoundException;
import com.example.Nutri_Nest.repository.FitnessActivityRepo;
import com.example.Nutri_Nest.repository.UserRepo;
import com.example.Nutri_Nest.service.FitnessActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FitnessActivityServiceTest {

    @Mock
    private FitnessActivityRepo fitnessActivityRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FitnessActivityService fitnessActivityService;

    private ActivityType workoutType;
    private FitnessActivityDTO fitnessActivityDTO;
    private FitnessActivity fitnessActivity;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        fitnessActivityDTO = new FitnessActivityDTO();
        fitnessActivityDTO.setId(1L);
        fitnessActivityDTO.setUserId(1L);
        fitnessActivityDTO.setWorkoutType(ActivityType.CARDIO);
        fitnessActivityDTO.setDuration(30);
        fitnessActivityDTO.setIntensity(Intensity.HIGH);
        fitnessActivityDTO.setNoOfActivities(3);

        fitnessActivity = new FitnessActivity();
        fitnessActivity.setId(1L);
        fitnessActivity.setWorkoutType(ActivityType.CARDIO);
        fitnessActivity.setDuration(30);
        fitnessActivity.setIntensity(Intensity.HIGH);
        fitnessActivity.setNoOfActivities(3);
        fitnessActivity.setUser(user);
    }

    @Test
    void testCreateFitness() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(fitnessActivityDTO, FitnessActivity.class)).thenReturn(fitnessActivity);
        when(fitnessActivityRepo.save(fitnessActivity)).thenReturn(fitnessActivity);

        ResponseEntity<String> response = fitnessActivityService.createFitness(fitnessActivityDTO);

        assertEquals("Fitness created successfully", response.getBody());
        verify(fitnessActivityRepo, times(1)).save(fitnessActivity);
    }

    @Test
    void testCreateFitness_UserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> fitnessActivityService.createFitness(fitnessActivityDTO));
    }

    @Test
    void testGetAllFitness() {
        when(fitnessActivityRepo.findAll()).thenReturn(List.of(fitnessActivity));
        when(modelMapper.map(fitnessActivity, FitnessActivityDTO.class)).thenReturn(fitnessActivityDTO);

        ResponseEntity<List<FitnessActivityDTO>> response = fitnessActivityService.getAllFitness();

        assertEquals(1, response.getBody().size());
        assertEquals(ActivityType.CARDIO, response.getBody().get(0).getWorkoutType());
        verify(fitnessActivityRepo, times(1)).findAll();
    }

    @Test
    void testGetFitnessById() {
        when(fitnessActivityRepo.findById(1L)).thenReturn(Optional.of(fitnessActivity));
        when(modelMapper.map(fitnessActivity, FitnessActivityDTO.class)).thenReturn(fitnessActivityDTO);

        ResponseEntity<FitnessActivityDTO> response = fitnessActivityService.getFitnessById(1L);

        assertEquals(ActivityType.CARDIO, response.getBody().getWorkoutType());
        verify(fitnessActivityRepo, times(1)).findById(1L);
    }

    @Test
    void testGetFitnessById_NotFound() {
        when(fitnessActivityRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FitnessActivityNotFoundException.class, () -> fitnessActivityService.getFitnessById(1L));
    }

    @Test
    void testFullUpdateById() {
        when(fitnessActivityRepo.findById(1L)).thenReturn(Optional.of(fitnessActivity));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = fitnessActivityService.fullUpdateById(fitnessActivityDTO, 1L);

        assertEquals("Fitness with ID 1 updated successfully!", response.getBody());
        verify(fitnessActivityRepo, times(1)).save(any(FitnessActivity.class));
    }

    @Test
    void testFullUpdateById_NotFound() {
        when(fitnessActivityRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FitnessActivityNotFoundException.class, () -> fitnessActivityService.fullUpdateById(fitnessActivityDTO, 1L));
    }

    @Test
    void testUpdateById() {
        when(fitnessActivityRepo.findById(1L)).thenReturn(Optional.of(fitnessActivity));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = fitnessActivityService.updateById(fitnessActivityDTO, 1L);

        assertEquals("Fitness with ID 1 updated successfully!", response.getBody());
        verify(fitnessActivityRepo, times(1)).save(fitnessActivity);
    }

    @Test
    void testDeleteById() {
        when(fitnessActivityRepo.findById(1L)).thenReturn(Optional.of(fitnessActivity));

        ResponseEntity<String> response = fitnessActivityService.deleteById(1L);

        assertEquals("Fitness with ID 1 deleted successfully", response.getBody());
        verify(fitnessActivityRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(fitnessActivityRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FitnessActivityNotFoundException.class, () -> fitnessActivityService.deleteById(1L));
    }

    @Test
    void testDeleteAllFitness() {
        ResponseEntity<String> response = fitnessActivityService.deleteAllFitness();

        assertEquals("All Fitness records deleted successfully!", response.getBody());
        verify(fitnessActivityRepo, times(1)).deleteAll();
    }
}


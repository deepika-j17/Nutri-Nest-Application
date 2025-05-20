package com.example.Nutri_Nest;

import com.example.Nutri_Nest.dto.UserProgressDTO;
import com.example.Nutri_Nest.entity.User;
import com.example.Nutri_Nest.entity.UserProgress;
import com.example.Nutri_Nest.exception.UserNotFoundException;
import com.example.Nutri_Nest.exception.UserProgressNotFoundException;
import com.example.Nutri_Nest.repository.UserProgressRepo;
import com.example.Nutri_Nest.repository.UserRepo;
import com.example.Nutri_Nest.service.UserProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProgressServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserProgressRepo userProgressRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserProgressService userProgressService;

    private User user;
    private UserProgress progress;
    private UserProgressDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        progress = new UserProgress();
        progress.setId(1L);
        progress.setSteps(1000);
        progress.setUser(user);
        progress.setDate(LocalDate.now());

        dto = new UserProgressDTO();
        dto.setSteps(1000);
        dto.setUserId(1L);
        dto.setDate(LocalDate.now());
    }

    @Test
    void testCreateUserProgress() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(dto, UserProgress.class)).thenReturn(progress);

        ResponseEntity<String> response = userProgressService.createUserProgress(dto);
        assertEquals("Progress created successfully", response.getBody());
        verify(userProgressRepo, times(1)).save(any(UserProgress.class));
    }

    @Test
    void testGetAllUserProgress() {
        List<UserProgress> progressList = Arrays.asList(progress);
        when(userProgressRepo.findAll()).thenReturn(progressList);

        UserProgressDTO mappedDTO = new UserProgressDTO();
        mappedDTO.setUserId(1L);
        when(modelMapper.map(any(UserProgress.class), eq(UserProgressDTO.class))).thenReturn(mappedDTO);

        ResponseEntity<List<UserProgressDTO>> response = userProgressService.getAllUserProgress();
        assertEquals(1, response.getBody().size());
        verify(userProgressRepo, times(1)).findAll();
    }

    @Test
    void testGetUserProgressById() {
        when(userProgressRepo.findById(1L)).thenReturn(Optional.of(progress));
        UserProgressDTO mappedDTO = new UserProgressDTO();
        mappedDTO.setUserId(1L);
        when(modelMapper.map(any(UserProgress.class), eq(UserProgressDTO.class))).thenReturn(mappedDTO);

        ResponseEntity<UserProgressDTO> response = userProgressService.getUserProgressById(1L);
        assertEquals(1L, response.getBody().getUserId());
    }

    @Test
    void testFullUpdateById() {
        when(userProgressRepo.findById(1L)).thenReturn(Optional.of(progress));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(dto, UserProgress.class)).thenReturn(progress);

        ResponseEntity<String> response = userProgressService.fullUpdateById(dto, 1L);
        assertEquals("User Progress with ID 1 updated successfully!", response.getBody());
    }

    @Test
    void testPartialUpdateById() {
        when(userProgressRepo.findById(1L)).thenReturn(Optional.of(progress));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        dto.setSteps(2000);
        dto.setWaterIntake(2);
        dto.setWeightTracking(60.0);

        ResponseEntity<String> response = userProgressService.updateById(dto, 1L);
        assertEquals("User Progress with ID 1 updated successfully!", response.getBody());
    }

    @Test
    void testDeleteById() {
        when(userProgressRepo.existsById(1L)).thenReturn(true);
        ResponseEntity<String> response = userProgressService.deleteById(1L);
        assertEquals("User Progress with ID 1 deleted successfully", response.getBody());
        verify(userProgressRepo).deleteById(1L);
    }

    @Test
    void testDeleteAllUserProgress() {
        ResponseEntity<String> response = userProgressService.deleteAllUserProgress();
        assertEquals("All User Progress records deleted successfully!", response.getBody());
        verify(userProgressRepo).deleteAll();
    }

    @Test
    void testCreateUserProgress_UserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userProgressService.createUserProgress(dto));
    }

    @Test
    void testGetUserProgressById_NotFound() {
        when(userProgressRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserProgressNotFoundException.class, () -> userProgressService.getUserProgressById(1L));
    }
}

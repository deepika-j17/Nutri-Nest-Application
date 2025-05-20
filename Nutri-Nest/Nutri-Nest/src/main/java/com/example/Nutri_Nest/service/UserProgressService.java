package com.example.Nutri_Nest.service;

import com.example.Nutri_Nest.dto.UserProgressDTO;
import com.example.Nutri_Nest.entity.User;
import com.example.Nutri_Nest.entity.UserProgress;
import com.example.Nutri_Nest.exception.UserNotFoundException;
import com.example.Nutri_Nest.repository.UserProgressRepo;
import com.example.Nutri_Nest.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.Nutri_Nest.exception.UserProgressNotFoundException;
import java.text.DecimalFormat;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProgressService {

    @Autowired
    UserProgressRepo userProgressRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public Double calculateCalories(UserProgress progress) {
        Integer steps = progress.getSteps();

        // Average calories burnt per step (adjustable: 0.04 - 0.06 based on pace/weight)
        double caloriesPerStep = 0.05;
        Double totalCalories = steps * caloriesPerStep;
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(totalCalories));
    }

    public ResponseEntity<String> createUserProgress(UserProgressDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserProgress progress = modelMapper.map(dto, UserProgress.class);
        progress.setUser(user);
        Double caloriesBurned = calculateCalories(progress);
        progress.setCaloriesBurnt(caloriesBurned);
        userProgressRepo.save(progress);
        return ResponseEntity.ok("Progress created successfully");
    }

    public ResponseEntity<List<UserProgressDTO>> getAllUserProgress() {
        List<UserProgressDTO> dtoList = userProgressRepo.findAll()
                .stream()
                .map(progress -> {
                    UserProgressDTO dto = modelMapper.map(progress, UserProgressDTO.class);
                    dto.setUserId(progress.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<UserProgressDTO> getUserProgressById(Long id) {
        UserProgress progress = userProgressRepo.findById(id)
                .orElseThrow(() -> new UserProgressNotFoundException("User Progress with ID " + id + " not found"));
        UserProgressDTO dto = modelMapper.map(progress, UserProgressDTO.class);
        dto.setUserId(progress.getUser().getId());
        return ResponseEntity.ok(dto);
    }

    // full update
    public ResponseEntity<String> fullUpdateById(UserProgressDTO dto, Long id) {
        UserProgress progress = userProgressRepo.findById(id)
                .orElseThrow(() -> new UserProgressNotFoundException("User Progress with ID " + id + " not found"));
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User ID is not provided or invalid in the UserProgress request."));

        modelMapper.map(dto, progress);
        progress.setUser(user);
        Double caloriesBurned = calculateCalories(progress);
        progress.setCaloriesBurnt(caloriesBurned);  // Update caloriesBurnt in the entity

        userProgressRepo.save(progress);
        return ResponseEntity.ok("User Progress with ID " + id + " updated successfully!");
    }

    // partial update
    public ResponseEntity<String> updateById(UserProgressDTO dto, Long id) {

        UserProgress progress = userProgressRepo.findById(id)
                .orElseThrow(() -> new UserProgressNotFoundException("User Progress with ID " + id + " not found"));

        if (dto.getWaterIntake() != null) progress.setWaterIntake(dto.getWaterIntake());
        if (dto.getWeightTracking() != null) progress.setWeightTracking(dto.getWeightTracking());
        if (dto.getSteps() != null) progress.setCaloriesBurnt(Double.valueOf(dto.getSteps()));
        if (dto.getDate() != null) progress.setDate(dto.getDate());
        if (dto.getCaloriesBurnt() != null) progress.setCaloriesBurnt(dto.getCaloriesBurnt());
        if (dto.getUserId() != null) {
            User user = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            progress.setUser(user);
        }
        Double caloriesBurned = calculateCalories(progress);
        progress.setCaloriesBurnt(caloriesBurned);

        userProgressRepo.save(progress);
        return ResponseEntity.ok("User Progress with ID " + id + " updated successfully!");
    }

    public ResponseEntity<String> deleteById(Long id) {
        if (!userProgressRepo.existsById(id)) {
            throw new UserProgressNotFoundException("User Progress with ID " + id + " not found");
        }
        userProgressRepo.deleteById(id);
        return ResponseEntity.ok("User Progress with ID " + id + " deleted successfully");
    }

    public ResponseEntity<String> deleteAllUserProgress() {
        userProgressRepo.deleteAll();
        return ResponseEntity.ok("All User Progress records deleted successfully!");
    }
}


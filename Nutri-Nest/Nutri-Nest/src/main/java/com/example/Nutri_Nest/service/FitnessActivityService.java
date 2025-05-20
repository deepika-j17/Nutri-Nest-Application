package com.example.Nutri_Nest.service;

import com.example.Nutri_Nest.dto.FitnessActivityDTO;
import com.example.Nutri_Nest.entity.FitnessActivity;
import com.example.Nutri_Nest.entity.User;
import com.example.Nutri_Nest.exception.FitnessActivityNotFoundException;
import com.example.Nutri_Nest.exception.UserNotFoundException;
import com.example.Nutri_Nest.repository.FitnessActivityRepo;
import com.example.Nutri_Nest.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FitnessActivityService {

    @Autowired
    private FitnessActivityRepo fitnessActivityRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<String> createFitness(FitnessActivityDTO fitnessActivityDTO) {
        fitnessActivityDTO.setId(null);
        FitnessActivity fitness = modelMapper.map(fitnessActivityDTO, FitnessActivity.class);
        User user = userRepo.findById(fitnessActivityDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + fitnessActivityDTO.getUserId()));
        fitness.setUser(user);
        fitnessActivityRepo.save(fitness);
        return ResponseEntity.ok("Fitness created successfully");
    }

    public ResponseEntity<List<FitnessActivityDTO>> getAllFitness() {
        List<FitnessActivityDTO> dtoList =  fitnessActivityRepo.findAll().stream().
                map(fitness -> {
                    FitnessActivityDTO dto = modelMapper.map(fitness, FitnessActivityDTO.class);
                    dto.setUserId(fitness.getUser().getId());
                    return dto;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<FitnessActivityDTO> getFitnessById(Long id) {
        FitnessActivity fitness = fitnessActivityRepo.findById(id)
                .orElseThrow(() -> new FitnessActivityNotFoundException("Fitness with ID " + id + " not found"));
        FitnessActivityDTO dto = modelMapper.map(fitness, FitnessActivityDTO.class);
        dto.setUserId(fitness.getUser().getId());
        return ResponseEntity.ok(dto);
    }

    // Full Update (PUT)
    public ResponseEntity<String> fullUpdateById(FitnessActivityDTO fitnessActivityDTO, Long id) {
        FitnessActivity existingFitness = fitnessActivityRepo.findById(id)
                .orElseThrow(() -> new FitnessActivityNotFoundException("Fitness with ID " + id + " not found"));

        modelMapper.map(fitnessActivityDTO, existingFitness);

        existingFitness.setId(existingFitness.getId());
        User user = userRepo.findById(fitnessActivityDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + fitnessActivityDTO.getUserId()));
        existingFitness.setUser(user);
        fitnessActivityRepo.save(existingFitness);
        return ResponseEntity.ok("Fitness with ID " + id + " updated successfully!");
    }

    // Partial Update (PATCH)
    public ResponseEntity<String> updateById(FitnessActivityDTO fitnessActivityDTO, Long id) {
        FitnessActivity existingFitness = fitnessActivityRepo.findById(id)
                .orElseThrow(() -> new FitnessActivityNotFoundException("Fitness with ID " + id + " not found"));

        if (fitnessActivityDTO.getId() != null) {
            fitnessActivityDTO.setId(null); // Set id to null to prevent it from being updated
        }

        if (fitnessActivityDTO.getWorkoutType() != null) {
            existingFitness.setWorkoutType(fitnessActivityDTO.getWorkoutType());
        }
        if (fitnessActivityDTO.getDuration() != null) {
            existingFitness.setDuration(fitnessActivityDTO.getDuration());
        }
        if (fitnessActivityDTO.getIntensity() != null) {
            existingFitness.setIntensity(fitnessActivityDTO.getIntensity());
        }
        if (fitnessActivityDTO.getNoOfActivities() != null) {
            existingFitness.setNoOfActivities(fitnessActivityDTO.getNoOfActivities());
        }
        if (fitnessActivityDTO.getWorkoutDate() != null) {
            existingFitness.setWorkoutDate(fitnessActivityDTO.getWorkoutDate());
        }
        if (fitnessActivityDTO.getUserId() != null) {
            User user = userRepo.findById(fitnessActivityDTO.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("User not found with id " + fitnessActivityDTO.getUserId()));
            existingFitness.setUser(user);
        }

        fitnessActivityRepo.save(existingFitness);
        return ResponseEntity.ok("Fitness with ID " + id + " updated successfully!");
    }

    public ResponseEntity<String> deleteById(Long id) {
        FitnessActivity existingFitness = fitnessActivityRepo.findById(id)
                .orElseThrow(() -> new FitnessActivityNotFoundException("Fitness with ID " + id + " not found"));

        fitnessActivityRepo.deleteById(id);
        return ResponseEntity.ok("Fitness with ID " + id + " deleted successfully");
    }

    public ResponseEntity<String> deleteAllFitness() {
        fitnessActivityRepo.deleteAll();
        return ResponseEntity.ok("All Fitness records deleted successfully!");
    }
}

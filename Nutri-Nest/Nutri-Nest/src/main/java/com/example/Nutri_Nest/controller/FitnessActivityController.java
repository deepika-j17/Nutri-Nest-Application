package com.example.Nutri_Nest.controller;

import com.example.Nutri_Nest.dto.FitnessActivityDTO;
import com.example.Nutri_Nest.service.FitnessActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrinest/fitness")
public class FitnessActivityController {

    @Autowired
    FitnessActivityService fitnessActivityService;

    @PostMapping("/admin/create")
    public ResponseEntity<String> createFitness(@Valid @RequestBody FitnessActivityDTO fitnessActivityDTO){
        return fitnessActivityService.createFitness(fitnessActivityDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FitnessActivityDTO>> getAllFitness(){
        return fitnessActivityService.getAllFitness();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FitnessActivityDTO> getFitnessById(@PathVariable Long id){
        return fitnessActivityService.getFitnessById(id);
    }

    //full update ->PUT
    @PutMapping("/admin/full-update/{id}")
    public ResponseEntity<String> fullUpdateById(@Valid @RequestBody FitnessActivityDTO fitnessActivityDTO, @PathVariable Long id){
        return fitnessActivityService.fullUpdateById(fitnessActivityDTO, id);
    }

    //partial update -> PATCH
    @PatchMapping("/admin/update/{id}")
    public ResponseEntity<String> updateById(@Valid @RequestBody FitnessActivityDTO fitnessActivityDTO, @PathVariable Long id){
        return fitnessActivityService.updateById(fitnessActivityDTO,id);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        return fitnessActivityService.deleteById(id);
    }

    @DeleteMapping("/admin/delete/all")
    public ResponseEntity<String> deleteAllFitness(){
        return fitnessActivityService.deleteAllFitness();
    }
}

package com.example.Nutri_Nest.controller;

import com.example.Nutri_Nest.dto.UserProgressDTO;
import com.example.Nutri_Nest.service.UserProgressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrinest/userProgress")
public class UserProgressController {

    @Autowired
    UserProgressService userProgressService;

    @PostMapping("/admin/create")
    public ResponseEntity<String> createUserProgress(@Valid @RequestBody UserProgressDTO userProgressDTO) {
        return userProgressService.createUserProgress(userProgressDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserProgressDTO>> getAllUserProgress() {
        return userProgressService.getAllUserProgress();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProgressDTO> getUserProgressById(@PathVariable Long id){
        return userProgressService.getUserProgressById(id);
    }

    //full update -> PUT
    @PutMapping("/admin/full-update/{id}")
    public ResponseEntity<String> fullUpdateById(@RequestBody UserProgressDTO userProgressDTO, @PathVariable Long id){
        return userProgressService.fullUpdateById(userProgressDTO, id);
    }

    //partial update -> PATCH
    @PatchMapping("/admin/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody UserProgressDTO userProgressDTO, @PathVariable Long id){
        return userProgressService.updateById(userProgressDTO,id);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        return userProgressService.deleteById(id);
    }

    @DeleteMapping("/admin/delete/all")
    public ResponseEntity<String> deleteAllUserProgress(){
        return userProgressService.deleteAllUserProgress();
    }
}


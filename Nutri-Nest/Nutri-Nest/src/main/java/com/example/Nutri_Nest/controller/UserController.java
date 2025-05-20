package com.example.Nutri_Nest.controller;

import com.example.Nutri_Nest.dto.UserDTO;
import com.example.Nutri_Nest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrinest/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/admin/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);

    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    //full update -> PUT
    @PutMapping("/admin/full-update/{id}")
    public ResponseEntity<String> fullUpdateById(@RequestBody UserDTO userDTO, @PathVariable Long id){
        return userService.fullUpdateById(userDTO,id);
    }

    //partial update -> PATCH
    @PatchMapping("/admin/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody UserDTO userDTO, @PathVariable Long id){
        return userService.updateById(userDTO,id);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){

        return userService.deleteUserById(id);
    }

    @DeleteMapping("/admin/delete/all")
    public ResponseEntity<String> deleteAllUsers()
    {
        return userService.deleteAllUsers();
    }
}

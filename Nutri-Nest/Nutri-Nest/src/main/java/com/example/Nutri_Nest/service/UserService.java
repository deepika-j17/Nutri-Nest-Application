package com.example.Nutri_Nest.service;

import com.example.Nutri_Nest.dto.UserDTO;
import com.example.Nutri_Nest.entity.User;
import com.example.Nutri_Nest.exception.UserNotFoundException;
import com.example.Nutri_Nest.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<String> createUser(UserDTO userDTO) {
        User user  = modelMapper.map(userDTO , User.class);
        userRepo.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepo.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<UserDTO> getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    // full update
    public ResponseEntity<String> fullUpdateById(UserDTO userDTO, Long id) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        modelMapper.map(userDTO, existingUser);

        userRepo.save(existingUser);
        return ResponseEntity.ok("User fully updated");
    }

    // partial update
    public ResponseEntity<String> updateById(UserDTO userDTO, Long id) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        if (userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }
        if (userDTO.getAge() != null) {
            existingUser.setAge(userDTO.getAge());
        }
        if (userDTO.getHeight() != null) {
            existingUser.setHeight(userDTO.getHeight());
        }
        if (userDTO.getWeight() != null) {
            existingUser.setWeight(userDTO.getWeight());
        }
        if (userDTO.getGoal() != null) {
            existingUser.setGoal(userDTO.getGoal());
        }

        userRepo.save(existingUser);
        return ResponseEntity.ok("User partially updated");
    }

    public ResponseEntity<String> deleteUserById(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    public ResponseEntity<String> deleteAllUsers() {
        userRepo.deleteAll();
        return ResponseEntity.ok("All Users deleted successfully!");
    }
}

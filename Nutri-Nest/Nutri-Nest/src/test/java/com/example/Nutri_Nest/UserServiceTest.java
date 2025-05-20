package com.example.Nutri_Nest;

import com.example.Nutri_Nest.enums.Goal;
import com.example.Nutri_Nest.service.UserService;
import com.example.Nutri_Nest.dto.UserDTO;
import com.example.Nutri_Nest.entity.User;
import com.example.Nutri_Nest.exception.UserNotFoundException;
import com.example.Nutri_Nest.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setAge(30);
        userDTO.setHeight(180.0);
        userDTO.setWeight(75.0);
        userDTO.setGoal(Goal.WEIGHT_LOSS);

        user = new User();
        user.setId(1L);
        user.setName("John");
        user.setAge(30);
        user.setHeight(180.0);
        user.setWeight(75.0);
        user.setGoal(Goal.WEIGHT_LOSS);
    }

    @Test
    void testCreateUser() {
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepo.save(any(User.class))).thenReturn(user);

        ResponseEntity<String> response = userService.createUser(userDTO);

        assertEquals("User created successfully", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Collections.singletonList(user);
        when(userRepo.findAll()).thenReturn(users);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = userService.getAllUsers();

        assertEquals(1, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getName());
    }

    @Test
    void testGetUserById_Found() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userService.getUserById(1L);

        assertEquals("John", response.getBody().getName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testFullUpdateById() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        doAnswer(invocation -> {
            UserDTO source = invocation.getArgument(0);
            User target = invocation.getArgument(1);
            target.setName(source.getName());
            target.setAge(source.getAge());
            target.setHeight(source.getHeight());
            target.setWeight(source.getWeight());
            target.setGoal(source.getGoal());
            return null;
        }).when(modelMapper).map(eq(userDTO), eq(user));

        ResponseEntity<String> response = userService.fullUpdateById(userDTO, 1L);

        assertEquals("User fully updated", response.getBody());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testPartialUpdateById() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        userDTO.setName("Updated John");
        userDTO.setAge(null); // partial update
        ResponseEntity<String> response = userService.updateById(userDTO, 1L);

        assertEquals("User partially updated", response.getBody());
        verify(userRepo, times(1)).save(user);
        assertEquals("Updated John", user.getName());
        assertEquals(30, user.getAge()); // original value should remain
    }

    @Test
    void testDeleteUserById_Success() {
        when(userRepo.existsById(1L)).thenReturn(true);

        ResponseEntity<String> response = userService.deleteUserById(1L);

        assertEquals("User deleted successfully", response.getBody());
        verify(userRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserById_NotFound() {
        when(userRepo.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(1L));
    }

    @Test
    void testDeleteAllUsers() {
        ResponseEntity<String> response = userService.deleteAllUsers();

        assertEquals("All Users deleted successfully!", response.getBody());
        verify(userRepo, times(1)).deleteAll();
    }
}

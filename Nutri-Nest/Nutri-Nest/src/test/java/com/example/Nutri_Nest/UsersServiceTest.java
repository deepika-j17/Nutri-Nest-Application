package com.example.Nutri_Nest;

import com.example.Nutri_Nest.service.UsersService;
import com.example.Nutri_Nest.entity.Users;
import com.example.Nutri_Nest.repository.UserDetailsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UserDetailsRepo userDetailsRepo;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new Users();
        user.setUsername("john_doe");
        user.setPwd("password123"); // plain text password
    }

    @Test
    void testCreateUser() {
        // Mock password encoding
        String encodedPassword = "encodedPassword";
        when(bCryptPasswordEncoder.encode(user.getPwd())).thenReturn(encodedPassword);
        when(userDetailsRepo.save(user)).thenReturn(user);

        // Call the service method
        String response = usersService.createUser(user);

        // Verify the expected outcome
        assertEquals("User is created", response);
        assertEquals(encodedPassword, user.getPwd()); // Ensure password is encoded
        verify(userDetailsRepo, times(1)).save(user); // Verify save method was called
    }

    @Test
    void testPasswordEncoding() {
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";

        // Mock the encoding process
        when(bCryptPasswordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String encoded = bCryptPasswordEncoder.encode(rawPassword);

        // Assert the encoded password matches the mock encoding
        assertEquals(encodedPassword, encoded);
    }
}

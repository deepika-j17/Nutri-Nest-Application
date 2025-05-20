package com.example.Nutri_Nest;

import com.example.Nutri_Nest.entity.UserPrincipal;
import com.example.Nutri_Nest.entity.Users;
import com.example.Nutri_Nest.repository.UserDetailsRepo;
import com.example.Nutri_Nest.service.MyUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

        import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class MyUserDetailsServiceTest {

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @Mock
    private UserDetailsRepo userDetailsRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        Users user = new Users();
        user.setUsername("john");
        user.setPwd("password");

        when(userDetailsRepo.getByUsername("john")).thenReturn(user);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername("john");

        assertNotNull(userDetails);
        assertEquals("john", userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userDetailsRepo.getByUsername("invalid")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            myUserDetailsService.loadUserByUsername("invalid");
        });
    }
}

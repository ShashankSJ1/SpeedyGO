package com.happiest.APIGatewayJWT.Service;

import com.happiest.APIGatewayJWT.model.UserPrincipal;
import com.happiest.APIGatewayJWT.model.Users;
import com.happiest.APIGatewayJWT.repository.UserRepo;
import com.happiest.APIGatewayJWT.service.MyUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MyUserDetailsServiceTest {

    @InjectMocks
    private MyUserDetailsService userDetailsService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsernameValid() {
        // Arrange
        String email = "test@example.com";
        Users user = new Users();
        user.setEmail(email);
        // Set other properties as necessary

        when(userRepo.findByEmail(email)).thenReturn(user);

        // Act
        UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(email);

        // Assert
        assertEquals(email, userPrincipal.getUsername());
        // Add additional assertions based on UserPrincipal properties
    }

    @Test
    public void testLoadUserByUsernameInvalid() {
        // Arrange
        String email = "nonexistent@example.com";

        when(userRepo.findByEmail(email)).thenReturn(null);

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email)
        );

        assertEquals("User not found", exception.getMessage());
    }
}


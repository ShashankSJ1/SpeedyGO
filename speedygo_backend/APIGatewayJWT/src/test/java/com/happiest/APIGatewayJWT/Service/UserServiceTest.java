package com.happiest.APIGatewayJWT.Service;

import com.happiest.APIGatewayJWT.LoginResponse;
import com.happiest.APIGatewayJWT.model.Users;
import com.happiest.APIGatewayJWT.model.enums.Role;
import com.happiest.APIGatewayJWT.repository.UserRepo;
import com.happiest.APIGatewayJWT.service.JWTService;
import com.happiest.APIGatewayJWT.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private Authentication authentication;

    private Users user;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new Users();
        user.setEmail("test@example.com");
        user.setPassword(new BCryptPasswordEncoder().encode("password"));
        user.setUsername("testuser");
        //user.setPhonenumber(1234567890);
        user.setPhonenumber(1234567890L);

        // Set the roles to a valid value
        user.setRoles(Role.CUSTOMER); // Ensure this matches your enum definition
    }


    @Test
    public void testRegister() {
        // Arrange
        when(userRepo.save(any(Users.class))).thenReturn(user);

        // Act
        Users registeredUser = userService.register(user);

        // Assert
        assertNotNull(registeredUser);
        assertEquals(user.getEmail(), registeredUser.getEmail());
    }

    @Test
    public void testVerifySuccessful() {
        // Arrange
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(user.getEmail())).thenReturn("dummyToken");
        when(userRepo.findByEmail(user.getEmail())).thenReturn(user);

        // Act
        LoginResponse loginResponse = userService.verify(user);

        // Assert
        assertNotNull(loginResponse);
        assertEquals("dummyToken", loginResponse.getToken());
        assertEquals(user.getEmail(), loginResponse.getEmail());
    }

    @Test
    public void testVerifyFailedAuthentication() {
        // Arrange
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Act
        LoginResponse loginResponse = userService.verify(user);

        // Assert
        assertNull(loginResponse);
    }

    @Test
    public void testForgotPasswordUserExists() {
        // Arrange
        when(userRepo.findByEmail(user.getEmail())).thenReturn(user);

        // Act
        String response = userService.forgotPassword(user.getEmail());

        // Assert
        assertEquals("User exists.", response);
    }

    @Test
    public void testForgotPasswordUserNotFound() {
        // Arrange
        when(userRepo.findByEmail(user.getEmail())).thenReturn(null);

        // Act
        String response = userService.forgotPassword(user.getEmail());

        // Assert
        assertEquals("User not found.", response);
    }

    @Test
    public void testUpdatePasswordSuccessful() {
        // Arrange
        String newPassword = new BCryptPasswordEncoder().encode("newpassword");
        when(userRepo.findByEmail(user.getEmail())).thenReturn(user);

        // Act
        String response = userService.updatePassword(user.getEmail(), newPassword);

        // Assert
        assertEquals("Password reset successful.", response);
        verify(userRepo).save(user);
    }

    @Test
    public void testUpdatePasswordSameAsCurrent() {
        // Arrange
        when(userRepo.findByEmail(user.getEmail())).thenReturn(user);

        // Act with the current password
        String response = userService.updatePassword(user.getEmail(), "password"); // This should be the plain text current password

        // Assert
        assertEquals("New password cannot be the same as the current password.", response);
        verify(userRepo, never()).save(any(Users.class)); // Ensure save is never called
    }



    @Test
    public void testUpdatePasswordUserNotFound() {
        // Arrange
        when(userRepo.findByEmail(user.getEmail())).thenReturn(null);

        // Act
        String response = userService.updatePassword(user.getEmail(), "newpassword");

        // Assert
        assertEquals("User not found.", response);
    }

    @Test
    public void testIsEmailPresentFound() {
        // Arrange
        when(userRepo.findByEmail(user.getEmail())).thenReturn(user);

        // Act
        String response = userService.isEmailPresent(user.getEmail());

        // Assert
        assertEquals("Email present", response);
    }

    @Test
    public void testIsEmailPresentNotFound() {
        // Arrange
        when(userRepo.findByEmail(user.getEmail())).thenReturn(null);

        // Act
        String response = userService.isEmailPresent(user.getEmail());

        // Assert
        assertEquals("Email not found", response);
    }
}

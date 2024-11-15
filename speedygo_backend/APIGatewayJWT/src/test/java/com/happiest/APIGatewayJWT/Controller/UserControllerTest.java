package com.happiest.APIGatewayJWT.Controller;

import com.happiest.APIGatewayJWT.LoginResponse;
import com.happiest.APIGatewayJWT.controller.UserController;
import com.happiest.APIGatewayJWT.model.Users;
import com.happiest.APIGatewayJWT.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterValidUser() {
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword("password");

        ResponseEntity<String> response = userController.register(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    public void testRegisterInvalidUser() {
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword(""); // Invalid password

        ResponseEntity<String> response = userController.register(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testLoginValidUser() {
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword("password");

        LoginResponse loginResponse = new LoginResponse("token", user);
        when(userService.verify(user)).thenReturn(loginResponse);

        ResponseEntity<?> response = userController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponse, response.getBody());
    }

    @Test
    public void testLoginInvalidUser() {
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword("wrongpassword");

        when(userService.verify(user)).thenReturn(null);

        ResponseEntity<?> response = userController.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    public void testForgotPasswordValidEmail() {
        String email = "test@example.com";
        when(userService.forgotPassword(email)).thenReturn("Password reset link sent");

        ResponseEntity<String> response = userController.forgotPassword(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password reset link sent", response.getBody());
    }

    @Test
    public void testForgotPasswordInvalidEmail() {
        String email = "invalid@example.com";
        when(userService.forgotPassword(email)).thenReturn("Email not found");

        ResponseEntity<String> response = userController.forgotPassword(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email not found", response.getBody());
    }

//    @Test
//    public void testResetPasswordValid() {
//        String email = "test@example.com";
//        String newPassword = "Newpassword@12";
//        String encodedPassword = encoder.encode(newPassword);
//
//        when(userService.updatePassword(email, encodedPassword)).thenReturn("Password updated successfully");
//
//        ResponseEntity<String> response = userController.resetPassword(email, newPassword);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Password updated successfully", response.getBody());
//    }

    @Test
    public void testResetPasswordInvalid() {
        String email = "test@example.com";
        String newPassword = "short"; // Invalid password

        ResponseEntity<String> response = userController.resetPassword(email, newPassword);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testIsEmailPresentValid() {
        String email = "test@example.com";
        when(userService.isEmailPresent(email)).thenReturn("Email is present");

        ResponseEntity<String> response = userController.isEmailPresent(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email is present", response.getBody());
    }

    @Test
    public void testIsEmailPresentInvalid() {
        String email = "invalid@example.com";
        when(userService.isEmailPresent(email)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<String> response = userController.isEmailPresent(email);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while checking email presence.", response.getBody());
    }
}

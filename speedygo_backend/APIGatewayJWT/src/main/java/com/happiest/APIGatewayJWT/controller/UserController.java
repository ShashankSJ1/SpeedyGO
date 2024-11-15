package com.happiest.APIGatewayJWT.controller;

import com.happiest.APIGatewayJWT.LoginResponse;
import com.happiest.APIGatewayJWT.model.Users;
import com.happiest.APIGatewayJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return new ResponseEntity<>("Invalid password", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        service.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        LoginResponse response = service.verify(user);
        if (response != null) {
            // Return the LoginResponse object with JWT token and user details
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/home")
    public String home() {
        return "In Home Page";
    }

    @PostMapping("/mail")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        String response = service.forgotPassword(email);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        if (email == null || email.isEmpty()) {
            return new ResponseEntity<>("Invalid email", HttpStatus.BAD_REQUEST);
        }
        if (newPassword == null || newPassword.isEmpty()) {
            return new ResponseEntity<>("Invalid password", HttpStatus.BAD_REQUEST);
        }
        String encodedPassword = encoder.encode(newPassword);
        String response = service.updatePassword(email, newPassword);
        if(response != null) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password update failed");
        }
    }

    @PostMapping("/emailPresent")
    public ResponseEntity<String> isEmailPresent(@RequestParam String email) {
        try {
            String present = service.isEmailPresent(email);
            return ResponseEntity.ok(present);
        } catch (Exception e) {
            // Log the exception (optional)
            e.printStackTrace();

            // Return a custom error message with a 500 status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking email presence.");
        }
    }
}

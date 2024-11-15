package com.happiest.APIGatewayJWT.controller;

import com.happiest.APIGatewayJWT.service.OtpService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    // Generate OTP and send it to the user's email (same as before)
    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateOtp(@RequestParam String email) {
        try {
            otpService.generateOtp(email); // Send OTP via email
            Map<String, String> response = new HashMap<>();
            response.put("message", "OTP has been sent to your email.");
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to send OTP: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Validate the OTP by matching the email and the OTP from the user input
    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> validateOtp(@RequestParam String email, @RequestParam String otp) {
        Map<String, String> response = new HashMap<>();
        if (otpService.validateOtp(email, otp)) {
            response.put("message", "OTP verified successfully");
            return ResponseEntity.ok(response); // Return 200 OK if OTP is valid
        } else {
            response.put("message", "OTP is wrong");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // Return 401 Unauthorized if OTP is wrong
        }
    }
}

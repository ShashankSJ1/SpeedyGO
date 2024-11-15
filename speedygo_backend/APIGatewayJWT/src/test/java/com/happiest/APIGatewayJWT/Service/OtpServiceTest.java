package com.happiest.APIGatewayJWT.Service;


import com.happiest.APIGatewayJWT.service.OtpService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OtpServiceTest {

    @InjectMocks
    private OtpService otpService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private MimeMessageHelper mimeMessageHelper;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    }

    @Test
    public void testGenerateOtp() throws MessagingException {
        // Arrange
        String email = "test@example.com";

        // Act
        otpService.generateOtp(email);

        // Assert
        assertNotNull(otpService.otpStore.get(email));
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void testValidateOtpSuccess() {
        // Arrange
        String email = "test@example.com";
        String generatedOtp = "123456"; // Assume this is the generated OTP
        otpService.otpStore.put(email, generatedOtp); // Simulate storing the OTP

        // Act
        boolean isValid = otpService.validateOtp(email, generatedOtp);

        // Assert
        assertTrue(isValid);
        assertNull(otpService.otpStore.get(email)); // OTP should be removed after validation
    }

    @Test
    public void testValidateOtpFailure() {
        // Arrange
        String email = "test@example.com";
        otpService.otpStore.put(email, "123456"); // Simulate storing a different OTP

        // Act
        boolean isValid = otpService.validateOtp(email, "654321"); // Invalid OTP

        // Assert
        assertFalse(isValid);
        assertEquals("123456", otpService.otpStore.get(email)); // OTP should remain unchanged
    }

    @Test
    public void testValidateOtpEmailNotFound() {
        // Act
        boolean isValid = otpService.validateOtp("nonexistent@example.com", "123456");

        // Assert
        assertFalse(isValid);
    }
}


package com.happiest.APIGatewayJWT.Service;

import com.happiest.APIGatewayJWT.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendOtpEmail_ValidInput() throws MessagingException {
        // Arrange
        String to = "test@example.com";
        String otp = "123456";
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        emailService.sendOtpEmail(to, otp);

        // Assert
        verify(mailSender).send(mimeMessage);
    }

    @Test
    void testSendOtpEmail_InvalidEmail() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emailService.sendOtpEmail(null, "123456");
        });
    }

    @Test
    void testSendOtpEmail_NullOtp() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emailService.sendOtpEmail("test@example.com", null);
        });
    }

    @Test
    void testSendOtpEmail_EmptyRecipient() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emailService.sendOtpEmail("", "123456");
        });
    }
}

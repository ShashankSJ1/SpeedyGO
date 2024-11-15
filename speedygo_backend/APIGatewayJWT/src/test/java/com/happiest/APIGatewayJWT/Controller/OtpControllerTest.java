package com.happiest.APIGatewayJWT.Controller;

import com.happiest.APIGatewayJWT.controller.OtpController;
import com.happiest.APIGatewayJWT.service.OtpService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OtpControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OtpService otpService;

    @InjectMocks
    private OtpController otpController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(otpController).build();
    }

    @Test
    public void generateOtp_ValidEmail_Success() throws Exception {
        String email = "test@example.com";

        mockMvc.perform(post("/otp/generate")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OTP has been sent to your email."));

        verify(otpService, times(1)).generateOtp(email);
    }

    @Test
    public void generateOtp_EmailSendingFails_ReturnsInternalServerError() throws Exception {
        String email = "test@example.com";
        doThrow(new MessagingException("Email sending failed")).when(otpService).generateOtp(email);

        mockMvc.perform(post("/otp/generate")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Failed to send OTP: Email sending failed"));

        verify(otpService, times(1)).generateOtp(email);
    }

    @Test
    public void validateOtp_ValidOtp_Success() throws Exception {
        String email = "test@example.com";
        String validOtp = "123456";
        when(otpService.validateOtp(email, validOtp)).thenReturn(true);

        mockMvc.perform(post("/otp/validate")
                        .param("email", email)
                        .param("otp", validOtp)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OTP verified successfully"));

        verify(otpService, times(1)).validateOtp(email, validOtp);
    }

    @Test
    public void validateOtp_InvalidOtp_ReturnsUnauthorized() throws Exception {
        String email = "test@example.com";
        String invalidOtp = "654321";
        when(otpService.validateOtp(email, invalidOtp)).thenReturn(false);

        mockMvc.perform(post("/otp/validate")
                        .param("email", email)
                        .param("otp", invalidOtp)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("OTP is wrong"));

        verify(otpService, times(1)).validateOtp(email, invalidOtp);
    }
}

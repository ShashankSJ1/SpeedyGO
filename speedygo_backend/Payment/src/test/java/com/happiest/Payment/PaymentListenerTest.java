package com.happiest.Payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.Payment.EmailService.PaymentListener;
import com.happiest.Payment.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentListenerTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private PaymentListener paymentListener;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testListen_SuccessfulPayment() throws JsonProcessingException {
        // Arrange
        Receipt receipt = new Receipt("12345", 100.0, "customer@example.com", "transporter@example.com");
        String message = objectMapper.writeValueAsString(receipt);

        // Act
        paymentListener.listen(message);

        // Verify that send was called twice
        verify(mailSender, times(2)).send(any(SimpleMailMessage.class));

        // Capture all sent emails
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(2)).send(messageCaptor.capture());

        // Get the captured messages
        List<SimpleMailMessage> sentMessages = messageCaptor.getAllValues();

        // Assert customer email
        SimpleMailMessage customerMessage = sentMessages.get(0);
        assertEquals("customer@example.com", customerMessage.getTo()[0]);
        assertEquals("Payment Receipt", customerMessage.getSubject());
        assertTrue(customerMessage.getText().contains("Payment ID: 12345"));
        assertTrue(customerMessage.getText().contains("Total Price: 100.00"));

        // Assert transporter email
        SimpleMailMessage transporterMessage = sentMessages.get(1);
        assertEquals("transporter@example.com", transporterMessage.getTo()[0]);
        assertEquals("Payment Receipt", transporterMessage.getSubject());
        assertTrue(transporterMessage.getText().contains("Payment ID: 12345"));
        assertTrue(transporterMessage.getText().contains("Total Price: 100.00"));
    }




    @Test
    public void testListen_InvalidMessage() {
        // Arrange
        String invalidMessage = "invalid-json";

        // Act
        assertDoesNotThrow(() -> paymentListener.listen(invalidMessage));

        // Assert
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }
}

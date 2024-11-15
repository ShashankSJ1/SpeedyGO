package com.happiest.Payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.Payment.model.Receipt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testReceiptConstructorAndGetters() {
        // Arrange
        String paymentId = "12345";
        double totalPrice = 100.0;
        String customerEmail = "customer@example.com";
        String transporterEmail = "transporter@example.com";

        // Act
        Receipt receipt = new Receipt(paymentId, totalPrice, customerEmail, transporterEmail);

        // Assert
        assertEquals(paymentId, receipt.getPaymentId());
        assertEquals(totalPrice, receipt.getTotalPrice());
        assertEquals(customerEmail, receipt.getCustomerEmail());
        assertEquals(transporterEmail, receipt.getTransporterEmail());
    }

    @Test
    public void testToJson() throws JsonProcessingException {
        // Arrange
        Receipt receipt = new Receipt("12345", 100.0, "customer@example.com", "transporter@example.com");

        // Act
        String json = receipt.toJson();

        // Assert
        assertNotNull(json);
        assertTrue(json.contains("\"paymentId\":\"12345\""));
        assertTrue(json.contains("\"totalPrice\":100.0"));
        assertTrue(json.contains("\"customerEmail\":\"customer@example.com\""));
        assertTrue(json.contains("\"transporterEmail\":\"transporter@example.com\""));
    }

    @Test
    public void testToString() {
        // Arrange
        Receipt receipt = new Receipt("12345", 100.0, "customer@example.com", "transporter@example.com");

        // Act
        String result = receipt.toString();

        // Assert
        String expectedString = "Payment ID: 12345, Total Price: 100.00, Customer Email: customer@example.com, Transporter Email: transporter@example.com";
        assertEquals(expectedString, result);
    }

    @Test
    public void testDefaultConstructor() {
        // Act
        Receipt receipt = new Receipt();

        // Assert
        assertNull(receipt.getPaymentId());
        assertEquals(0.0, receipt.getTotalPrice());
        assertNull(receipt.getCustomerEmail());
        assertNull(receipt.getTransporterEmail());
    }
}


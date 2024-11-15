package com.happiest.APIGatewayJWT.Booking.Contact;

import com.happiest.APIGatewayJWT.APiGateway.Contact.Repository.ContactServiceFeignClient;
import com.happiest.APIGatewayJWT.APiGateway.Contact.controller.ContactController;
import com.happiest.APIGatewayJWT.APiGateway.Contact.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ContactControllerTest {

    @InjectMocks
    private ContactController contactController;

    @Mock
    private ContactServiceFeignClient contactServiceFeignClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void submitContact_ValidContact_ReturnsSuccess() {
        // Arrange
        Contact contact = new Contact();
        contact.setName("John Doe");
        contact.setEmail("john.doe@example.com");
        contact.setMessage("Hello, this is a test message.");

        // Simulate success response from Feign client
        when(contactServiceFeignClient.submitContact(any(Contact.class)))
                .thenReturn(ResponseEntity.ok("Contact submitted successfully"));

        // Act
        ResponseEntity<String> response = contactController.submitContact(contact);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Contact submitted successfully", response.getBody());
        verify(contactServiceFeignClient, times(1)).submitContact(any(Contact.class));
    }

    @Test
    public void submitContact_ErrorSubmittingContact_ReturnsError() {
        // Arrange
        Contact contact = new Contact();
        contact.setName("Jane Doe");
        contact.setEmail("jane.doe@example.com");
        contact.setMessage("This message should not go through.");

        // Simulate error response from Feign client
        when(contactServiceFeignClient.submitContact(any(Contact.class)))
                .thenReturn(ResponseEntity.internalServerError().body("Error submitting contact"));

        // Act
        ResponseEntity<String> response = contactController.submitContact(contact);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error submitting contact", response.getBody());
        verify(contactServiceFeignClient, times(1)).submitContact(any(Contact.class));
    }

}


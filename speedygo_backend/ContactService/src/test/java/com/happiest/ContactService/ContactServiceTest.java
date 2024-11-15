package com.happiest.ContactService;

import com.happiest.ContactService.constants.PredefinedConstant;
import com.happiest.ContactService.model.Contact;
import com.happiest.ContactService.repository.ContactRepository;
import com.happiest.ContactService.service.ContactService;
import com.happiest.ContactService.utility.Rbundle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @MockBean
    private ContactRepository contactRepository;

    @Test
    public void testSaveContact_Success() {
        Contact contact = new Contact();
        contact.setEmail("test@example.com");
        contact.setName("John Doe");
        contact.setMessage("Hello, this is a test message.");

        // Mock repository save method
        when(contactRepository.save(contact)).thenReturn(contact);

        // Call the service method
        assertDoesNotThrow(() -> contactService.saveContact(contact));
    }

    @Test
    public void testSaveContact_ThrowsExceptionWhenEmailIsNull() {
        Contact contact = new Contact();
        contact.setName("John Doe");
        contact.setMessage("Hello, this is a test message.");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            contactService.saveContact(contact);
        });

        assertTrue(exception.getMessage().contains(Rbundle.getKey(PredefinedConstant.CONTACT_INVALID_INPUT)));
    }

    @Test
    public void testSaveContact_ThrowsExceptionWhenRepositoryFails() {
        Contact contact = new Contact();
        contact.setEmail("test@example.com");
        contact.setName("John Doe");
        contact.setMessage("Hello, this is a test message.");

        // Simulate a repository failure
        when(contactRepository.save(contact)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            contactService.saveContact(contact);
        });

        assertTrue(exception.getMessage().contains("An error occurred while saving the contact: Database error"));
    }
}

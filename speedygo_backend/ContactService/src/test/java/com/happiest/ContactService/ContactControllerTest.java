package com.happiest.ContactService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.ContactService.model.Contact;
import com.happiest.ContactService.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSubmitContact_Success() throws Exception {
        Contact contact = new Contact();
        contact.setEmail("test@example.com");
        contact.setName("John Doe");
        contact.setMessage("Hello, this is a test message.");

        // Simulate successful service execution
        doNothing().when(contactService).saveContact(contact);

        mockMvc.perform(post("/api/contact/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contact)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSubmitContact_InternalServerError() throws Exception {
        Contact contact = new Contact();
        contact.setEmail("test@example.com");
        contact.setName("John Doe");
        contact.setMessage("Hello, this is a test message.");

        // Simulate a failure in the service
        doThrow(new RuntimeException("Service failure")).when(contactService).saveContact(contact);

        mockMvc.perform(post("/api/contact/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contact)))
                .andExpect(status().isInternalServerError());
    }
}

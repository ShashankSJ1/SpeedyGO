package com.happiest.BookingService.DTO;

import com.happiest.BookingService.dto.CustomerInfoDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerInfoDTOTest {

    @Test
    public void testNoArgsConstructor() {
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();

        // Check that all fields are initialized to their default values
        assertNull(customerInfoDTO.getEmail());
        assertNull(customerInfoDTO.getName());
        assertNull(customerInfoDTO.getPhoneNumber());
    }

    @Test
    public void testAllArgsConstructor() {
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO(
                "test@example.com", "John Doe", 1234567890L);

        // Check that fields are correctly set
        assertEquals("test@example.com", customerInfoDTO.getEmail());
        assertEquals("John Doe", customerInfoDTO.getName());
        assertEquals(1234567890L, customerInfoDTO.getPhoneNumber());
    }

    @Test
    public void testGettersAndSetters() {
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();

        customerInfoDTO.setEmail("test@example.com");
        customerInfoDTO.setName("John Doe");
        customerInfoDTO.setPhoneNumber(1234567890L);

        assertEquals("test@example.com", customerInfoDTO.getEmail());
        assertEquals("John Doe", customerInfoDTO.getName());
        assertEquals(1234567890L, customerInfoDTO.getPhoneNumber());
    }

    @Test
    public void testEqualsAndHashCode() {
        CustomerInfoDTO customerInfoDTO1 = new CustomerInfoDTO("test@example.com", "John Doe", 1234567890L);
        CustomerInfoDTO customerInfoDTO2 = new CustomerInfoDTO("test@example.com", "John Doe", 1234567890L);
        CustomerInfoDTO customerInfoDTO3 = new CustomerInfoDTO("other@example.com", "Jane Doe", 9876543210L);

        assertEquals(customerInfoDTO1, customerInfoDTO2); // Should be equal
        assertNotEquals(customerInfoDTO1, customerInfoDTO3); // Should not be equal
        assertEquals(customerInfoDTO1.hashCode(), customerInfoDTO2.hashCode()); // HashCodes should be equal
    }

    @Test
    public void testToString() {
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO("test@example.com", "John Doe", 1234567890L);

        String expectedString = "CustomerInfoDTO(email=test@example.com, name=John Doe, phoneNumber=1234567890)";
        assertEquals(expectedString, customerInfoDTO.toString());
    }
}


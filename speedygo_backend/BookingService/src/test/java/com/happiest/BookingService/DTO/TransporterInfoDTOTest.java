package com.happiest.BookingService.DTO;

import com.happiest.BookingService.dto.TransporterInfoDTO;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransporterInfoDTOTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        TransporterInfoDTO transporterInfoDTO = new TransporterInfoDTO();

        // Act & Assert
        assertThat(transporterInfoDTO.getEmail()).isNull();
        assertThat(transporterInfoDTO.getUsername()).isNull();
        assertThat(transporterInfoDTO.getPhoneNumber()).isNull();
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        String email = "transporter@example.com";
        String username = "transporterUser";
        Long phoneNumber = 1234567890L;

        // Act
        TransporterInfoDTO transporterInfoDTO = new TransporterInfoDTO(email, username, phoneNumber);

        // Assert
        assertThat(transporterInfoDTO.getEmail()).isEqualTo(email);
        assertThat(transporterInfoDTO.getUsername()).isEqualTo(username);
        assertThat(transporterInfoDTO.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        TransporterInfoDTO transporterInfoDTO = new TransporterInfoDTO();
        String email = "transporter@example.com";
        String username = "transporterUser";
        Long phoneNumber = 1234567890L;

        // Act
        transporterInfoDTO.setEmail(email);
        transporterInfoDTO.setUsername(username);
        transporterInfoDTO.setPhoneNumber(phoneNumber);

        // Assert
        assertThat(transporterInfoDTO.getEmail()).isEqualTo(email);
        assertThat(transporterInfoDTO.getUsername()).isEqualTo(username);
        assertThat(transporterInfoDTO.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        TransporterInfoDTO transporterInfoDTO1 = new TransporterInfoDTO("transporter@example.com", "transporterUser", 1234567890L);
        TransporterInfoDTO transporterInfoDTO2 = new TransporterInfoDTO("transporter@example.com", "transporterUser", 1234567890L);
        TransporterInfoDTO transporterInfoDTO3 = new TransporterInfoDTO("another@example.com", "anotherUser", 9876543210L);

        // Act & Assert
        assertThat(transporterInfoDTO1).isEqualTo(transporterInfoDTO2); // Should be equal
        assertThat(transporterInfoDTO1).isNotEqualTo(transporterInfoDTO3); // Should not be equal
        assertThat(transporterInfoDTO1.hashCode()).isEqualTo(transporterInfoDTO2.hashCode()); // Same hash code
        assertThat(transporterInfoDTO1.hashCode()).isNotEqualTo(transporterInfoDTO3.hashCode()); // Different hash code
    }
}


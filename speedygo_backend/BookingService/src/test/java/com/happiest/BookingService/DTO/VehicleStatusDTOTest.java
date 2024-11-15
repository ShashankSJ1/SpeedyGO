package com.happiest.BookingService.DTO;


import com.happiest.BookingService.dto.VehicleStatusDTO;
import com.happiest.BookingService.model.enums.VehicleStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VehicleStatusDTOTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        String vehicleNumber = "ABC123";
        VehicleStatus status = VehicleStatus.AVAILABLE; // Assuming this is a valid status

        // Act
        VehicleStatusDTO vehicleStatusDTO = new VehicleStatusDTO(vehicleNumber, status);

        // Assert
        assertThat(vehicleStatusDTO.getVehicleNumber()).isEqualTo(vehicleNumber);
        assertThat(vehicleStatusDTO.getStatus()).isEqualTo(status);
    }

    @Test
    public void testSetters() {
        // Arrange
        VehicleStatusDTO vehicleStatusDTO = new VehicleStatusDTO("XYZ789", VehicleStatus.NOT_AVAILABLE);

        // Act
        String newVehicleNumber = "LMN456";
        VehicleStatus newStatus = VehicleStatus.AVAILABLE;

        vehicleStatusDTO.setVehicleNumber(newVehicleNumber);
        vehicleStatusDTO.setStatus(newStatus);

        // Assert
        assertThat(vehicleStatusDTO.getVehicleNumber()).isEqualTo(newVehicleNumber);
        assertThat(vehicleStatusDTO.getStatus()).isEqualTo(newStatus);
    }


}


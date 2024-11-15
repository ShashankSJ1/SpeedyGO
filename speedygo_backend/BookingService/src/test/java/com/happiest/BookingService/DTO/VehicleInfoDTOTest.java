package com.happiest.BookingService.DTO;

import com.happiest.BookingService.dto.VehicleInfoDTO;
import com.happiest.BookingService.model.enums.VehicleStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VehicleInfoDTOTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        VehicleInfoDTO vehicleInfoDTO = new VehicleInfoDTO();

        // Act & Assert
        assertThat(vehicleInfoDTO.getVehicleName()).isNull();
        assertThat(vehicleInfoDTO.getVehicleNumber()).isNull();
        assertThat(vehicleInfoDTO.getVehicleType()).isNull();
        assertThat(vehicleInfoDTO.getPricePerKilometer()).isZero();
        assertThat(vehicleInfoDTO.getBasePrice()).isZero();
        assertThat(vehicleInfoDTO.getStatus()).isNull();
        assertThat(vehicleInfoDTO.getTotalPrice()).isZero();
    }

    @Test
    public void testParameterizedConstructor() {
        // Arrange
        String vehicleName = "Toyota Corolla";
        String vehicleNumber = "ABC123";
        String vehicleType = "Sedan";
        double basePrice = 15000.00;
        double pricePerKilometer = 0.5;
        VehicleStatus status = VehicleStatus.AVAILABLE;

        // Act
        VehicleInfoDTO vehicleInfoDTO = new VehicleInfoDTO(vehicleName, status, basePrice, pricePerKilometer, vehicleType, vehicleNumber);

        // Assert
        assertThat(vehicleInfoDTO.getVehicleName()).isEqualTo(vehicleName);
        assertThat(vehicleInfoDTO.getVehicleNumber()).isEqualTo(vehicleNumber);
        assertThat(vehicleInfoDTO.getVehicleType()).isEqualTo(vehicleType);
        assertThat(vehicleInfoDTO.getPricePerKilometer()).isEqualTo(pricePerKilometer);
        assertThat(vehicleInfoDTO.getBasePrice()).isEqualTo(basePrice);
        assertThat(vehicleInfoDTO.getStatus()).isEqualTo(status);
        assertThat(vehicleInfoDTO.getTotalPrice()).isZero(); // totalPrice should default to 0
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        VehicleInfoDTO vehicleInfoDTO = new VehicleInfoDTO();
        String vehicleName = "Honda Civic";
        String vehicleNumber = "XYZ456";
        String vehicleType = "Coupe";
        double basePrice = 18000.00;
        double pricePerKilometer = 0.6;
        VehicleStatus status = VehicleStatus.NOT_AVAILABLE;

        // Act
        vehicleInfoDTO.setVehicleName(vehicleName);
        vehicleInfoDTO.setVehicleNumber(vehicleNumber);
        vehicleInfoDTO.setVehicleType(vehicleType);
        vehicleInfoDTO.setBasePrice(basePrice);
        vehicleInfoDTO.setPricePerKilometer(pricePerKilometer);
        vehicleInfoDTO.setStatus(status);

        // Assert
        assertThat(vehicleInfoDTO.getVehicleName()).isEqualTo(vehicleName);
        assertThat(vehicleInfoDTO.getVehicleNumber()).isEqualTo(vehicleNumber);
        assertThat(vehicleInfoDTO.getVehicleType()).isEqualTo(vehicleType);
        assertThat(vehicleInfoDTO.getBasePrice()).isEqualTo(basePrice);
        assertThat(vehicleInfoDTO.getPricePerKilometer()).isEqualTo(pricePerKilometer);
        assertThat(vehicleInfoDTO.getStatus()).isEqualTo(status);
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        VehicleInfoDTO vehicleInfoDTO1 = new VehicleInfoDTO("Ford Focus", VehicleStatus.AVAILABLE, 20000.00, 0.7, "Hatchback", "LMN789");
        VehicleInfoDTO vehicleInfoDTO2 = new VehicleInfoDTO("Ford Focus", VehicleStatus.AVAILABLE, 20000.00, 0.7, "Hatchback", "LMN789");
        VehicleInfoDTO vehicleInfoDTO3 = new VehicleInfoDTO("Chevrolet Malibu", VehicleStatus.NOT_AVAILABLE, 22000.00, 0.8, "Sedan", "OPQ101");

        // Act & Assert
        assertThat(vehicleInfoDTO1).isEqualTo(vehicleInfoDTO2); // Should be equal
        assertThat(vehicleInfoDTO1).isNotEqualTo(vehicleInfoDTO3); // Should not be equal
        assertThat(vehicleInfoDTO1.hashCode()).isEqualTo(vehicleInfoDTO2.hashCode()); // Same hash code
        assertThat(vehicleInfoDTO1.hashCode()).isNotEqualTo(vehicleInfoDTO3.hashCode()); // Different hash code
    }
}

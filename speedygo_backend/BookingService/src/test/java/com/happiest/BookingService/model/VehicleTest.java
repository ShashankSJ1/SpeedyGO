package com.happiest.BookingService.model;


import com.happiest.BookingService.model.enums.VehicleStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VehicleTest {

//    @Test
//    public void testDefaultConstructor() {
//        // Arrange
//        Vehicle vehicle = new Vehicle();
//
//        // Act & Assert
//        assertThat(vehicle.getVehicleNumber()).isNull();
//        assertThat(vehicle.getVehicleName()).isNull();
//        assertThat(vehicle.getVehicleType()).isNull();
//        assertThat(vehicle.getBasePrice()).isZero();
//        assertThat(vehicle.getPricePerKilometer()).isZero();
//        assertThat(vehicle.getTransporter()).isNull();
//        assertThat(vehicle.getStatus()).isNull();
//    }
//
//    @Test
//    public void testParameterizedConstructor() {
//        // Arrange
//        String vehicleNumber = "ABC123";
//        String vehicleName = "Transport Vehicle";
//        String vehicleType = "Truck";
//        double basePrice = 10000.0;
//        double pricePerKilometer = 5.0;
//        Users transporter = new Users("customer@example.com", "Customer", 1234567890L); // Create a mock Users object or use a proper object
//        VehicleStatus status = VehicleStatus.AVAILABLE; // Use a valid enum value
//
//        // Act
//        Vehicle vehicle = new Vehicle(vehicleNumber, vehicleName, vehicleType, basePrice, pricePerKilometer, transporter, status);
//
//        // Assert
//        assertThat(vehicle.getVehicleNumber()).isEqualTo(vehicleNumber);
//        assertThat(vehicle.getVehicleName()).isEqualTo(vehicleName);
//        assertThat(vehicle.getVehicleType()).isEqualTo(vehicleType);
//        assertThat(vehicle.getBasePrice()).isEqualTo(basePrice);
//        assertThat(vehicle.getPricePerKilometer()).isEqualTo(pricePerKilometer);
//        assertThat(vehicle.getTransporter()).isEqualTo(transporter);
//        assertThat(vehicle.getStatus()).isEqualTo(status);
//    }
//
//    @Test
//    public void testGettersAndSetters() {
//        // Arrange
//        Vehicle vehicle = new Vehicle();
//        String vehicleNumber = "XYZ456";
//        String vehicleName = "Delivery Van";
//        String vehicleType = "Van";
//        double basePrice = 15000.0;
//        double pricePerKilometer = 7.0;
//        Users transporter = new Users("customer@example.com", "Customer", 1234567890L); // Create a mock Users object or use a proper object
//        VehicleStatus status = VehicleStatus.NOT_AVAILABLE; // Use a valid enum value
//
//        // Act
//        vehicle.setVehicleNumber(vehicleNumber);
//        vehicle.setVehicleName(vehicleName);
//        vehicle.setVehicleType(vehicleType);
//        vehicle.setBasePrice(basePrice);
//        vehicle.setPricePerKilometer(pricePerKilometer);
//        vehicle.setTransporter(transporter);
//        vehicle.setStatus(status);
//
//        // Assert
//        assertThat(vehicle.getVehicleNumber()).isEqualTo(vehicleNumber);
//        assertThat(vehicle.getVehicleName()).isEqualTo(vehicleName);
//        assertThat(vehicle.getVehicleType()).isEqualTo(vehicleType);
//        assertThat(vehicle.getBasePrice()).isEqualTo(basePrice);
//        assertThat(vehicle.getPricePerKilometer()).isEqualTo(pricePerKilometer);
//        assertThat(vehicle.getTransporter()).isEqualTo(transporter);
//        assertThat(vehicle.getStatus()).isEqualTo(status);
//    }
//
//    @Test
//    public void testEqualsAndHashCode() {
//        // Arrange
//        Users transporter1 = new Users("customer@example.com", "Customer", 1234567890L); // Create mock Users objects
//        Vehicle vehicle1 = new Vehicle("ABC123", "Transport Vehicle", "Truck", 10000.0, 5.0, transporter1, VehicleStatus.AVAILABLE);
//        Vehicle vehicle2 = new Vehicle("ABC123", "Transport Vehicle", "Truck", 10000.0, 5.0, transporter1, VehicleStatus.AVAILABLE);
//        Vehicle vehicle3 = new Vehicle("DEF456", "Different Vehicle", "Bus", 20000.0, 10.0, transporter1, VehicleStatus.NOT_AVAILABLE);
//
//        // Act & Assert
//        assertThat(vehicle1).isEqualTo(vehicle2); // Should be equal
//        assertThat(vehicle1).isNotEqualTo(vehicle3); // Should not be equal
//        assertThat(vehicle1.hashCode()).isEqualTo(vehicle2.hashCode()); // Same hash code
//        assertThat(vehicle1.hashCode()).isNotEqualTo(vehicle3.hashCode()); // Different hash code
//    }
}


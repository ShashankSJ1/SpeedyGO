package com.happiest.BookingService.model;


import com.happiest.BookingService.model.enums.RideStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        Booking booking = new Booking();

        // Act & Assert
        assertThat(booking.getRequestId()).isNull();
        assertThat(booking.getSource()).isNull();
        assertThat(booking.getDestination()).isNull();
        assertThat(booking.getDistance()).isZero();
        assertThat(booking.getTotalPrice()).isZero();
        assertThat(booking.getCustomer()).isNull();
        assertThat(booking.getTransporter()).isNull();
        assertThat(booking.getVehicle()).isNull();
        assertThat(booking.getRideStatus()).isNull();
        assertThat(booking.getRideDate()).isNull();
        assertThat(booking.getRideTime()).isNull();
    }

//    @Test
//    public void testParameterizedConstructor() {
//        // Arrange
//        Long requestId = 1L;
//        String source = "Location A";
//        String destination = "Location B";
//        double distance = 12.5;
//        double totalPrice = 150.0;
//        Users customer = new Users("customer@example.com", "Customer", 1234567890L); // Create a mock Users object or use a proper object
//        Users transporter = new Users("customer@example.com", "Customer", 1234567890L); // Create a mock Users object or use a proper object
//        Vehicle vehicle = new Vehicle(); // Create a mock Vehicle object or use a proper object
//        RideStatus rideStatus = RideStatus.PENDING; // Use a valid enum value
//        LocalDate rideDate = LocalDate.now();
//        LocalTime rideTime = LocalTime.now();
//
//        // Act
//        Booking booking = new Booking(requestId, source, destination, distance, totalPrice, customer, transporter, vehicle, rideStatus, rideDate, rideTime);
//
//        // Assert
//        assertThat(booking.getRequestId()).isEqualTo(requestId);
//        assertThat(booking.getSource()).isEqualTo(source);
//        assertThat(booking.getDestination()).isEqualTo(destination);
//        assertThat(booking.getDistance()).isEqualTo(distance);
//        assertThat(booking.getTotalPrice()).isEqualTo(totalPrice);
//        assertThat(booking.getCustomer()).isEqualTo(customer);
//        assertThat(booking.getTransporter()).isEqualTo(transporter);
//        assertThat(booking.getVehicle()).isEqualTo(vehicle);
//        assertThat(booking.getRideStatus()).isEqualTo(rideStatus);
//        assertThat(booking.getRideDate()).isEqualTo(rideDate);
//        assertThat(booking.getRideTime()).isEqualTo(rideTime);
//    }
//
//    @Test
//    public void testGettersAndSetters() {
//        // Arrange
//        Booking booking = new Booking();
//        String source = "City A";
//        String destination = "City B";
//        double distance = 25.0;
//        double totalPrice = 200.0;
//        Users customer = new Users("customer@example.com", "Customer", 1234567890L); // Create a mock Users object or use a proper object
//        Users transporter = new Users("customer@example.com", "Customer", 1234567890L); // Create a mock Users object or use a proper object
//        Vehicle vehicle = new Vehicle(); // Create a mock Vehicle object or use a proper object
//        RideStatus rideStatus = RideStatus.COMPLETED; // Use a valid enum value
//        LocalDate rideDate = LocalDate.now();
//        LocalTime rideTime = LocalTime.now();
//
//        // Act
//        booking.setSource(source);
//        booking.setDestination(destination);
//        booking.setDistance(distance);
//        booking.setTotalPrice(totalPrice);
//        booking.setCustomer(customer);
//        booking.setTransporter(transporter);
//        booking.setVehicle(vehicle);
//        booking.setRideStatus(rideStatus);
//        booking.setRideDate(rideDate);
//        booking.setRideTime(rideTime);
//
//        // Assert
//        assertThat(booking.getSource()).isEqualTo(source);
//        assertThat(booking.getDestination()).isEqualTo(destination);
//        assertThat(booking.getDistance()).isEqualTo(distance);
//        assertThat(booking.getTotalPrice()).isEqualTo(totalPrice);
//        assertThat(booking.getCustomer()).isEqualTo(customer);
//        assertThat(booking.getTransporter()).isEqualTo(transporter);
//        assertThat(booking.getVehicle()).isEqualTo(vehicle);
//        assertThat(booking.getRideStatus()).isEqualTo(rideStatus);
//        assertThat(booking.getRideDate()).isEqualTo(rideDate);
//        assertThat(booking.getRideTime()).isEqualTo(rideTime);
//    }
//
//    @Test
//    public void testEqualsAndHashCode() {
//        // Arrange
//        Users customer1 = new Users("customer@example.com", "Customer", 1234567890L); // Create mock Users objects
//        Users transporter1 = new Users("customer@example.com", "Customer", 1234567890L); // Create mock Users objects
//        Vehicle vehicle1 = new Vehicle(); // Create mock Vehicle objects
//
//        Booking booking1 = new Booking(1L, "Location A", "Location B", 10.0, 100.0, customer1, transporter1, vehicle1, RideStatus.PENDING, LocalDate.now(), LocalTime.now());
//        Booking booking2 = new Booking(1L, "Location A", "Location B", 10.0, 100.0, customer1, transporter1, vehicle1, RideStatus.PENDING, LocalDate.now(), LocalTime.now());
//        Booking booking3 = new Booking(2L, "Location C", "Location D", 15.0, 150.0, customer1, transporter1, vehicle1, RideStatus.COMPLETED, LocalDate.now(), LocalTime.now());
//
//        // Act & Assert
//        assertThat(booking1).isEqualTo(booking2); // Should be equal
//        assertThat(booking1).isNotEqualTo(booking3); // Should not be equal
//        assertThat(booking1.hashCode()).isEqualTo(booking2.hashCode()); // Same hash code
//        assertThat(booking1.hashCode()).isNotEqualTo(booking3.hashCode()); // Different hash code
//    }
}


package com.happiest.BookingService.DTO;

import com.happiest.BookingService.dto.RideRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class RideRequestTest {

    @Test
    public void testNoArgsConstructor() {
        RideRequest rideRequest = new RideRequest();

        // Check that all fields are initialized to their default values
        assertNull(rideRequest.getCustomerEmail());
        assertNull(rideRequest.getTransporterEmail());
        assertNull(rideRequest.getVehicleNumber());
        assertNull(rideRequest.getSource());
        assertNull(rideRequest.getDestination());
        assertEquals(0.0, rideRequest.getDistance());
        assertNull(rideRequest.getRideDate());
        assertNull(rideRequest.getRideTime());
    }

    @Test
    public void testAllArgsConstructor() {
        RideRequest rideRequest = new RideRequest(
                "customer@example.com",
                "transporter@example.com",
                "V123",
                "Source Location",
                "Destination Location",
                10.5,
                LocalDate.of(2024, 10, 21),
                LocalTime.of(14, 30)
        );

        // Check that fields are correctly set
        assertEquals("customer@example.com", rideRequest.getCustomerEmail());
        assertEquals("transporter@example.com", rideRequest.getTransporterEmail());
        assertEquals("V123", rideRequest.getVehicleNumber());
        assertEquals("Source Location", rideRequest.getSource());
        assertEquals("Destination Location", rideRequest.getDestination());
        assertEquals(10.5, rideRequest.getDistance());
        assertEquals(LocalDate.of(2024, 10, 21), rideRequest.getRideDate());
        assertEquals(LocalTime.of(14, 30), rideRequest.getRideTime());
    }

    @Test
    public void testGettersAndSetters() {
        RideRequest rideRequest = new RideRequest();

        // Set values
        rideRequest.setCustomerEmail("customer@example.com");
        rideRequest.setTransporterEmail("transporter@example.com");
        rideRequest.setVehicleNumber("V123");
        rideRequest.setSource("Source Location");
        rideRequest.setDestination("Destination Location");
        rideRequest.setDistance(10.5);
        rideRequest.setRideDate(LocalDate.of(2024, 10, 21));
        rideRequest.setRideTime(LocalTime.of(14, 30));

        // Assert that values are set correctly
        assertEquals("customer@example.com", rideRequest.getCustomerEmail());
        assertEquals("transporter@example.com", rideRequest.getTransporterEmail());
        assertEquals("V123", rideRequest.getVehicleNumber());
        assertEquals("Source Location", rideRequest.getSource());
        assertEquals("Destination Location", rideRequest.getDestination());
        assertEquals(10.5, rideRequest.getDistance());
        assertEquals(LocalDate.of(2024, 10, 21), rideRequest.getRideDate());
        assertEquals(LocalTime.of(14, 30), rideRequest.getRideTime());
    }

    @Test
    public void testEqualsAndHashCode() {
        RideRequest rideRequest1 = new RideRequest("customer@example.com", "transporter@example.com", "V123", "Source Location", "Destination Location", 10.5, LocalDate.of(2024, 10, 21), LocalTime.of(14, 30));
        RideRequest rideRequest2 = new RideRequest("customer@example.com", "transporter@example.com", "V123", "Source Location", "Destination Location", 10.5, LocalDate.of(2024, 10, 21), LocalTime.of(14, 30));
        RideRequest rideRequest3 = new RideRequest("other@example.com", "othertransporter@example.com", "V456", "Other Source", "Other Destination", 15.0, LocalDate.of(2024, 10, 22), LocalTime.of(15, 45));

        assertEquals(rideRequest1, rideRequest2); // Should be equal
        assertNotEquals(rideRequest1, rideRequest3); // Should not be equal
        assertEquals(rideRequest1.hashCode(), rideRequest2.hashCode()); // HashCodes should be equal
    }

    @Test
    public void testToString() {
        RideRequest rideRequest = new RideRequest("customer@example.com", "transporter@example.com", "V123", "Source Location", "Destination Location", 10.5, LocalDate.of(2024, 10, 21), LocalTime.of(14, 30));

        String expectedString = "RideRequest(customerEmail=customer@example.com, transporterEmail=transporter@example.com, vehicleNumber=V123, source=Source Location, destination=Destination Location, distance=10.5, rideDate=2024-10-21, rideTime=14:30)";
        assertEquals(expectedString, rideRequest.toString());
    }
}


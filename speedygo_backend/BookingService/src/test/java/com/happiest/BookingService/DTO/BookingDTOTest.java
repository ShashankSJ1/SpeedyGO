package com.happiest.BookingService.DTO;

import com.happiest.BookingService.dto.BookingDTO;
import com.happiest.BookingService.dto.CustomerInfoDTO;
import com.happiest.BookingService.dto.TransporterInfoDTO;

import com.happiest.BookingService.dto.VehicleInfoDTO;
import com.happiest.BookingService.model.enums.RideStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDTOTest {

    @Test
    public void testNoArgsConstructor() {
        BookingDTO bookingDTO = new BookingDTO();

        // Check that all fields are initialized to their default values
        assertNull(bookingDTO.getRequestId());
        assertNull(bookingDTO.getSource());
        assertNull(bookingDTO.getDestination());
        assertEquals(0.0, bookingDTO.getDistance());
        assertEquals(0.0, bookingDTO.getTotalPrice());
        assertNull(bookingDTO.getCustomerInfo());
        assertNull(bookingDTO.getTransporterInfo());
        assertNull(bookingDTO.getVehicleInfo());
        assertNull(bookingDTO.getRideStatus());
        assertNull(bookingDTO.getRideDate());
        assertNull(bookingDTO.getRideTime());
    }

    @Test
    public void testAllArgsConstructor() {
        CustomerInfoDTO customerInfo = new CustomerInfoDTO(); // Create a valid CustomerInfoDTO
        TransporterInfoDTO transporterInfo = new TransporterInfoDTO(); // Create a valid TransporterInfoDTO
        VehicleInfoDTO vehicleInfo = new VehicleInfoDTO(); // Create a valid VehicleInfoDTO
        RideStatus rideStatus = RideStatus.PENDING; // Example RideStatus

        BookingDTO bookingDTO = new BookingDTO(
                1L, "Source Location", "Destination Location", 10.0,
                100.0, customerInfo, transporterInfo, vehicleInfo,
                rideStatus, LocalDate.now(), LocalTime.now()
        );

        // Check that fields are correctly set
        assertEquals(1L, bookingDTO.getRequestId());
        assertEquals("Source Location", bookingDTO.getSource());
        assertEquals("Destination Location", bookingDTO.getDestination());
        assertEquals(10.0, bookingDTO.getDistance());
        assertEquals(100.0, bookingDTO.getTotalPrice());
        assertEquals(customerInfo, bookingDTO.getCustomerInfo());
        assertEquals(transporterInfo, bookingDTO.getTransporterInfo());
        assertEquals(vehicleInfo, bookingDTO.getVehicleInfo());
        assertEquals(rideStatus, bookingDTO.getRideStatus());
        assertEquals(LocalDate.now(), bookingDTO.getRideDate());
        assertEquals(LocalTime.now().getHour(), bookingDTO.getRideTime().getHour()); // Check only hour for simplicity
    }

    @Test
    public void testGettersAndSetters() {
        BookingDTO bookingDTO = new BookingDTO();
        CustomerInfoDTO customerInfo = new CustomerInfoDTO();
        TransporterInfoDTO transporterInfo = new TransporterInfoDTO();
        VehicleInfoDTO vehicleInfo = new VehicleInfoDTO();
        RideStatus rideStatus = RideStatus.PENDING;

        bookingDTO.setRequestId(1L);
        bookingDTO.setSource("Source");
        bookingDTO.setDestination("Destination");
        bookingDTO.setDistance(12.5);
        bookingDTO.setTotalPrice(150.0);
        bookingDTO.setCustomerInfo(customerInfo);
        bookingDTO.setTransporterInfo(transporterInfo);
        bookingDTO.setVehicleInfo(vehicleInfo);
        bookingDTO.setRideStatus(rideStatus);
        bookingDTO.setRideDate(LocalDate.now());
        bookingDTO.setRideTime(LocalTime.now());

        assertEquals(1L, bookingDTO.getRequestId());
        assertEquals("Source", bookingDTO.getSource());
        assertEquals("Destination", bookingDTO.getDestination());
        assertEquals(12.5, bookingDTO.getDistance());
        assertEquals(150.0, bookingDTO.getTotalPrice());
        assertEquals(customerInfo, bookingDTO.getCustomerInfo());
        assertEquals(transporterInfo, bookingDTO.getTransporterInfo());
        assertEquals(vehicleInfo, bookingDTO.getVehicleInfo());
        assertEquals(rideStatus, bookingDTO.getRideStatus());
        assertEquals(LocalDate.now(), bookingDTO.getRideDate());
        assertEquals(LocalTime.now().getHour(), bookingDTO.getRideTime().getHour()); // Check only hour for simplicity
    }

    @Test
    public void testEqualsAndHashCode() {
        CustomerInfoDTO customerInfo1 = new CustomerInfoDTO();
        CustomerInfoDTO customerInfo2 = new CustomerInfoDTO();

        BookingDTO bookingDTO1 = new BookingDTO(1L, "Source", "Destination", 10.0, 100.0,
                customerInfo1, null, null, RideStatus.PENDING, LocalDate.now(), LocalTime.now());
        BookingDTO bookingDTO2 = new BookingDTO(1L, "Source", "Destination", 10.0, 100.0,
                customerInfo1, null, null, RideStatus.PENDING, LocalDate.now(), LocalTime.now());
        BookingDTO bookingDTO3 = new BookingDTO(2L, "Source", "Destination", 10.0, 100.0,
                customerInfo2, null, null, RideStatus.PENDING, LocalDate.now(), LocalTime.now());

        assertEquals(bookingDTO1, bookingDTO2); // Should be equal
        assertNotEquals(bookingDTO1, bookingDTO3); // Should not be equal
        assertEquals(bookingDTO1.hashCode(), bookingDTO2.hashCode()); // HashCodes should be equal
    }

    @Test
    public void testToString() {
        CustomerInfoDTO customerInfo = new CustomerInfoDTO();
        BookingDTO bookingDTO = new BookingDTO(1L, "Source", "Destination", 10.0, 100.0,
                customerInfo, null, null, RideStatus.PENDING, LocalDate.now(), LocalTime.now());

        String expectedString = "BookingDTO(requestId=1, source=Source, destination=Destination, " +
                "distance=10.0, totalPrice=100.0, customerInfo=" + customerInfo + ", " +
                "transporterInfo=null, vehicleInfo=null, rideStatus=PENDING, " +
                "rideDate=" + bookingDTO.getRideDate() + ", rideTime=" + bookingDTO.getRideTime() + ")";

        assertEquals(expectedString, bookingDTO.toString());
    }
}


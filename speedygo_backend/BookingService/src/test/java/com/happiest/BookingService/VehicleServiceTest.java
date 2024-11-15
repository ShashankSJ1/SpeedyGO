package com.happiest.BookingService;

import com.happiest.BookingService.dto.TransporterInfoDTO;
import com.happiest.BookingService.dto.VehicleInfoDTO;
import com.happiest.BookingService.dto.VehicleWithTransporterDTO;
import com.happiest.BookingService.exceptions.VehicleNotFoundException;
import com.happiest.BookingService.model.Booking;
import com.happiest.BookingService.model.Users;
import com.happiest.BookingService.model.Vehicle;
import com.happiest.BookingService.model.enums.RideStatus;
import com.happiest.BookingService.model.enums.VehicleStatus;
import com.happiest.BookingService.repository.BookingRepository;
import com.happiest.BookingService.repository.VehicleRepository;
import com.happiest.BookingService.service.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;




public class VehicleServiceTest {

    @InjectMocks
    private BookingService vehicleService; // Assuming BookingService is the correct service class

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private BookingRepository bookingRepository;

    private Vehicle vehicle;
    private Users transporter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize transporter
        transporter = new Users();
        transporter.setEmail("transporter@example.com");
        transporter.setUsername("Transporter");
        transporter.setPhonenumber(9876543210L);

        // Initialize vehicle
        vehicle = new Vehicle();
        vehicle.setVehicleNumber("V123");
        vehicle.setVehicleName("Car");
        vehicle.setVehicleType("Sedan");
        vehicle.setBasePrice(1000.0);
        vehicle.setPricePerKilometer(10.0);
        vehicle.setStatus(VehicleStatus.AVAILABLE); // Use the enum
        vehicle.setTransporter(transporter);
    }



    @Test
    public void testGetAvailableVehiclesNotInBookings() {
        // Given
        List<Vehicle> availableVehicles = new ArrayList<>();
        availableVehicles.add(vehicle);

        // Mock bookings with statuses (empty for this test)
        List<Booking> bookingsWithStatuses = Collections.emptyList();

        // Mocking the repository responses
        when(vehicleRepository.findAvailableVehiclesNotInBookings()).thenReturn(availableVehicles);
        when(bookingRepository.findBookingsWithStatuses()).thenReturn(bookingsWithStatuses);

        // Call the service method
        List<VehicleWithTransporterDTO> result = vehicleService.getAvailableVehiclesNotInBookings(50.0);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(vehicle.getVehicleNumber(), result.get(0).getVehicleInfo().getVehicleNumber());

        // Verify that the repository methods were called exactly once
        verify(vehicleRepository, times(1)).findAvailableVehiclesNotInBookings();
        verify(bookingRepository, times(1)).findBookingsWithStatuses();
    }

    @Test
    public void testGetAvailableVehiclesWithBookingStatuses() {
        // Given
        List<Vehicle> availableVehicles = new ArrayList<>();
        availableVehicles.add(vehicle);

        // Mock bookings with statuses
        Booking booking = new Booking();
        booking.setVehicle(vehicle);
        booking.setRideStatus(RideStatus.PENDING); // Assuming it has a pending status

        List<Booking> bookingsWithStatuses = new ArrayList<>();
        bookingsWithStatuses.add(booking);

        // Mocking the repository responses
        when(vehicleRepository.findAvailableVehicles()).thenReturn(availableVehicles);
        when(bookingRepository.findBookingsWithStatuses()).thenReturn(bookingsWithStatuses);

        // Call the service method
        List<Vehicle> result = vehicleService.getAvailableVehiclesWithBookingStatuses();

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(vehicle.getVehicleNumber(), result.get(0).getVehicleNumber());

        // Verify that the repository methods were called exactly once
        verify(vehicleRepository, times(1)).findAvailableVehicles();
        verify(bookingRepository, times(1)).findBookingsWithStatuses();
    }

    @Test
    public void testUpdateVehiclePricing() {
        // Mock input data
        String vehicleNumber = "KA01AB1234";
        double newBasePrice = 500.0;
        double newPricePerKilometer = 15.0;

        // Mock existing vehicle
        Vehicle existingVehicle = new Vehicle();
        existingVehicle.setVehicleNumber(vehicleNumber);
        existingVehicle.setBasePrice(400.0);
        existingVehicle.setPricePerKilometer(10.0);

        // Set up mock behavior for repository
        Mockito.when(vehicleRepository.findById(vehicleNumber)).thenReturn(Optional.of(existingVehicle));
        Mockito.when(vehicleRepository.save(any(Vehicle.class))).thenReturn(existingVehicle);

        // Call the service method
        Vehicle updatedVehicle = vehicleService.updateVehiclePricing(vehicleNumber, newBasePrice, newPricePerKilometer);

        // Verify the updated values
        Assertions.assertEquals(newBasePrice, updatedVehicle.getBasePrice());
        Assertions.assertEquals(newPricePerKilometer, updatedVehicle.getPricePerKilometer());

        // Verify repository interactions
        Mockito.verify(vehicleRepository, Mockito.times(1)).findById(vehicleNumber);
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(existingVehicle);
    }


}

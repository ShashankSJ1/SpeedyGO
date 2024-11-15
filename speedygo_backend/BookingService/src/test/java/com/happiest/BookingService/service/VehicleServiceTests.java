package com.happiest.BookingService.service;

import static org.mockito.Mockito.*;

import com.happiest.BookingService.exceptions.VehicleNotFoundException;
import com.happiest.BookingService.model.Vehicle;
import com.happiest.BookingService.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VehicleServiceTests {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private BookingService vehicleService;

    @BeforeEach
    public void setUp() {
        // Setup a valid vehicle object for the success scenario
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("123ABC");
        vehicle.setVehicleName("Test Vehicle");
        vehicle.setVehicleType("Car");
        vehicle.setBasePrice(100.0);
        vehicle.setPricePerKilometer(10.0);

        // Mock the repository to return this vehicle when queried
        when(vehicleRepository.findByVehicleNumber("123ABC")).thenReturn(vehicle);
    }

    @Test
    public void testGetVehicleByNumber_Success() {
        // Call the service method to fetch the vehicle
        Vehicle vehicle = vehicleService.getVehicleByNumber("123ABC");

        // Verify the result
        assertNotNull(vehicle);
        assertEquals("123ABC", vehicle.getVehicleNumber());
    }

    @Test
    public void testGetVehicleByNumber_VehicleNotFound() {
        // Mock the repository to return null (vehicle not found)
        when(vehicleRepository.findByVehicleNumber("INVALID123")).thenReturn(null);

        // Assert that a RuntimeException is thrown
        Exception exception = assertThrows(RuntimeException.class, () -> {
            vehicleService.getVehicleByNumber("INVALID123");
        });

        // Check that the exception message contains the cause for VehicleNotFoundException
        String expectedMessage = "Vehicle with number not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Optionally, you can assert the cause of the RuntimeException is VehicleNotFoundException
        assertTrue(exception.getCause() instanceof VehicleNotFoundException);
    }


}


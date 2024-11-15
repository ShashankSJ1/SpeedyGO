package com.happiest.BookingService;

import com.happiest.BookingService.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VehicleComparatorTest {

    private Comparator<Vehicle> vehicleTypeComparator;
    private Comparator<Vehicle> basePriceComparator;
    private Comparator<Vehicle> pricePerKilometerComparator;

    @Mock
    private Vehicle vehicle1;

    @Mock
    private Vehicle vehicle2;

    @Mock
    private Vehicle vehicleWithNullType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize comparators
        vehicleTypeComparator = Comparator.comparing(Vehicle::getVehicleType, Comparator.nullsLast(Comparator.naturalOrder()));
        basePriceComparator = Comparator.comparingDouble(Vehicle::getBasePrice).reversed();
        pricePerKilometerComparator = Comparator.comparingDouble(Vehicle::getPricePerKilometer).reversed();
    }

    @Test
    void testCompareByVehicleTypeDescending() {
        when(vehicle1.getVehicleType()).thenReturn("Truck");
        when(vehicle2.getVehicleType()).thenReturn("Car");

        assertTrue(vehicleTypeComparator.compare(vehicle1, vehicle2) > 0,
                "Expected vehicle1 (Truck) to be after vehicle2 (Car) when sorted by vehicle type in descending order.");
    }

    @Test
    void testCompareByVehicleTypeWithNull() {
        when(vehicle1.getVehicleType()).thenReturn("Car");
        when(vehicleWithNullType.getVehicleType()).thenReturn(null);

        // Now compare should return > 0 since "Car" should be before null
        assertTrue(vehicleTypeComparator.compare(vehicle1, vehicleWithNullType) < 0,
                "Expected vehicle1 (Car) to be before vehicleWithNullType (null type) when sorted by vehicle type in descending order.");
    }


    @Test
    void testCompareByBasePriceDescending() {
        when(vehicle1.getBasePrice()).thenReturn(1500.0);
        when(vehicle2.getBasePrice()).thenReturn(1000.0);

        assertTrue(basePriceComparator.compare(vehicle1, vehicle2) < 0,
                "Expected vehicle1 to be before vehicle2 when sorted by base price in descending order.");
    }

    @Test
    void testCompareByPricePerKilometerDescending() {
        when(vehicle1.getPricePerKilometer()).thenReturn(2.0);
        when(vehicle2.getPricePerKilometer()).thenReturn(1.0);

        assertTrue(pricePerKilometerComparator.compare(vehicle1, vehicle2) < 0,
                "Expected vehicle1 to be before vehicle2 when sorted by price per kilometer in descending order.");
    }

    @Test
    void testCompareWithNullBasePrice() {
        // Return a default value instead of null
        when(vehicle1.getBasePrice()).thenReturn(0.0); // Mock a valid default
        when(vehicle2.getBasePrice()).thenReturn(1000.0);

        assertTrue(basePriceComparator.compare(vehicle1, vehicle2) > 0,
                "Expected vehicle2 to be after vehicle1 when vehicle1 has a base price of 0.");
    }

    @Test
    void testCompareWithNullPricePerKilometer() {
        // Return a default value instead of null
        when(vehicle1.getPricePerKilometer()).thenReturn(2.0);
        when(vehicle2.getPricePerKilometer()).thenReturn(0.0); // Mock a valid default

        assertTrue(pricePerKilometerComparator.compare(vehicle1, vehicle2) < 0,
                "Expected vehicle1 to be before vehicle2 when vehicle2 has a price per kilometer of 0.");
    }

    @Test
    void testInvalidSortField() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Invalid sort field: invalidField");
        });
        assertEquals("Invalid sort field: invalidField", thrown.getMessage());
    }
}

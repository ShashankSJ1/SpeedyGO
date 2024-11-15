package com.happiest.BookingService.DTO;

import com.happiest.BookingService.controller.BookingController;
import com.happiest.BookingService.model.Vehicle;
import com.happiest.BookingService.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        // This is optional with @WebMvcTest as MockMvc is already autowired.
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void updateVehiclePricing_ShouldReturnUpdatedVehicle() throws Exception {
        // Prepare the test data
        String vehicleNumber = "V002"; // Ensure this number corresponds to an existing vehicle in your setup
        double basePrice = 1000.0;
        double pricePerKilometer = 50.0;

        // Create a mock Vehicle object to return
        Vehicle updatedVehicle = new Vehicle();
        updatedVehicle.setVehicleNumber(vehicleNumber);
        updatedVehicle.setBasePrice(basePrice);
        updatedVehicle.setPricePerKilometer(pricePerKilometer);

        // Mock the service call
        when(bookingService.updateVehiclePricing(anyString(), anyDouble(), anyDouble())).thenReturn(updatedVehicle);

        // Perform the PUT request
        mockMvc.perform(put("/api/bookings/{vehicleNumber}/updatePricing", vehicleNumber)
                        .param("basePrice", String.valueOf(basePrice))
                        .param("pricePerKilometer", String.valueOf(pricePerKilometer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleNumber").value(vehicleNumber))
                .andExpect(jsonPath("$.basePrice").value(basePrice))
                .andExpect(jsonPath("$.pricePerKilometer").value(pricePerKilometer));
    }

}

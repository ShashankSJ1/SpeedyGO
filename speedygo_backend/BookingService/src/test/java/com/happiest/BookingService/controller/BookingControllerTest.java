package com.happiest.BookingService.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.BookingService.dto.*;
import com.happiest.BookingService.exceptions.VehicleNotFoundException;
import com.happiest.BookingService.exceptions.VehicleSaveException;
import com.happiest.BookingService.model.Booking;
import com.happiest.BookingService.model.Users;
import com.happiest.BookingService.model.Vehicle;
import com.happiest.BookingService.model.enums.RideStatus;
import com.happiest.BookingService.model.enums.Role;
import com.happiest.BookingService.model.enums.VehicleStatus;
import com.happiest.BookingService.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingControllerTest {

    @InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }



    @Test
    public void testGetPendingRequests() throws Exception {
        List<Booking> pendingRequests = new ArrayList<>(); // Initialize with test data

        when(bookingService.getPendingRequests("test@transporter.com")).thenReturn(pendingRequests);

        mockMvc.perform(get("/api/bookings/transporter/test@transporter.com/pending-requests"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size
    }

    @Test
    public void testGetCustomerRequests() throws Exception {
        List<Booking> customerRequests = new ArrayList<>(); // Initialize with test data

        when(bookingService.getCustomerRequests("test@customer.com")).thenReturn(customerRequests);

        mockMvc.perform(get("/api/bookings/customer/test@customer.com/requests"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size
    }

    @Test
    public void testGetCombinedAvailableVehicles() throws Exception {
        List<VehicleWithTransporterDTO> vehicles = new ArrayList<>(); // Initialize with test data

        when(bookingService.getCombinedAvailableVehicles(anyDouble())).thenReturn(vehicles);

        mockMvc.perform(get("/api/bookings/available?distance=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size
    }





    @Test
    public void testGetCombinedAvailableVehicles_Success() throws Exception {
        List<VehicleWithTransporterDTO> vehicles = new ArrayList<>(); // Initialize with test data

        when(bookingService.getCombinedAvailableVehicles(anyDouble())).thenReturn(vehicles);

        mockMvc.perform(get("/api/bookings/available?distance=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }

    // Additional test cases for completed or rejected bookings by transporter and customer
    @Test
    public void testGetCompletedOrRejectedBookingsByTransporterEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getCompletedOrRejectedBookingsByTransporterEmail("transporter@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/completed-or-rejected/transporter/transporter@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size
    }

    @Test
    public void testGetCompletedOrRejectedBookingsByCustomerEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getCompletedOrRejectedBookingsByCustomerEmail("customer@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/completed-or-rejected/customer/customer@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size
    }



    @Test
    public void testGetAcceptedOrPendingBookingsByTransporterEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getAcceptedOrPendingBookingsByTransporterEmail("transporter@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/accepted-or-pending/transporter/transporter@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }

    @Test
    public void testGetAcceptedOrPendingBookingsByCustomerEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getAcceptedOrPendingBookingsByCustomerEmail("customer@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/accepted-or-pending/customer/customer@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }
    @Test
    public void testGetRejectedBookingsByTransporterEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getRejectedBookingsByTransporterEmail("transporter@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/rejected/transporter/transporter@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }

    @Test
    public void testGetRejectedBookingsByCustomerEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getRejectedBookingsByCustomerEmail("customer@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/rejected/customer/customer@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }

    @Test
    public void testGetPendingBookingsByTransporterEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getPendingBookingsByTransporterEmail("transporter@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/pending/transporter/transporter@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }

    @Test
    public void testGetPendingBookingsByCustomerEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getPendingBookingsByCustomerEmail("customer@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/pending/customer/customer@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }

    @Test
    public void testGetAcceptedBookingsByTransporterEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getAcceptedBookingsByTransporterEmail("transporter@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/accepted/transporter/transporter@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }

    @Test
    public void testGetAcceptedBookingsByCustomerEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getAcceptedBookingsByCustomerEmail("customer@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/accepted/customer/customer@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }


    @Test
    public void testGetVehicleInfoByEmail() throws Exception {
        // Mock VehicleStatusDTO response
        VehicleStatusDTO vehicleStatus = new VehicleStatusDTO("ABC123", VehicleStatus.AVAILABLE);

        when(bookingService.getVehicleStatusByEmail("transporter@example.com")).thenReturn(vehicleStatus);

        mockMvc.perform(get("/api/bookings/vehicle-info/transporter@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vehicleNumber").value("ABC123"))
                .andExpect(jsonPath("$.status").value("AVAILABLE")); // Match the enum value
    }


    @Test
    public void testGetPaymentBookingsByTransporterEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getPaymentBookingsByTransporterEmail("transporter@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/payment/transporter/transporter@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }

    @Test
    public void testGetPaymentBookingsByCustomerEmail() throws Exception {
        List<BookingDTO> bookings = new ArrayList<>(); // Initialize with test data

        when(bookingService.getPaymentBookingsByCustomerEmail("customer@example.com")).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings/payment/customer/customer@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Update with actual size if necessary
    }


    @Test
    public void testSearchVehicles_ValidParams() throws Exception {
        // Given
        String keyword = "truck";
        double distance = 50.0;

        // Create instances of VehicleStatus, VehicleInfoDTO, and TransporterInfoDTO
        VehicleStatus status = VehicleStatus.AVAILABLE; // Example status
        VehicleInfoDTO vehicleInfo = new VehicleInfoDTO("Truck", status, 1000, 5, "Cargo", "TRK123");

        // Create a TransporterInfoDTO instance
        TransporterInfoDTO transporterInfo = new TransporterInfoDTO("transporter@example.com", "Transporter A", 1234567890L);

        // Create VehicleWithTransporterDTO
        VehicleWithTransporterDTO vehicle = new VehicleWithTransporterDTO(vehicleInfo, transporterInfo);
        List<VehicleWithTransporterDTO> vehicleList = Collections.singletonList(vehicle);

        when(bookingService.searchVehicles(eq(keyword), eq(distance))).thenReturn(vehicleList);

        // When & Then
        mockMvc.perform(get("/api/bookings/search")
                        .param("keyword", keyword)
                        .param("distance", String.valueOf(distance)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFilterVehicles_ValidParams() throws Exception {
        // Given
        double distance = 50.0;

        // Create instances of VehicleStatus, VehicleInfoDTO, and TransporterInfoDTO
        VehicleStatus status = VehicleStatus.AVAILABLE; // Example status
        VehicleInfoDTO vehicleInfo = new VehicleInfoDTO("Van", status, 800, 4, "Cargo", "VAN456");

        // Create a TransporterInfoDTO instance
        TransporterInfoDTO transporterInfo = new TransporterInfoDTO("transporter@example.com", "Transporter B", 9876543210L);

        // Create VehicleWithTransporterDTO
        VehicleWithTransporterDTO vehicle = new VehicleWithTransporterDTO(vehicleInfo, transporterInfo);
        List<VehicleWithTransporterDTO> vehicleList = Collections.singletonList(vehicle);

        when(bookingService.filterVehicles(any(), any(), any(), any(), any(), any(), any(), any(), any(), eq(distance)))
                .thenReturn(vehicleList);

        // When & Then
        mockMvc.perform(get("/api/bookings/filter")
                        .param("distance", String.valueOf(distance)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testRequestRide() {
        // Arrange
        RideRequest rideRequest = new RideRequest();
        BookingDTO expectedBookingDTO = new BookingDTO();
        expectedBookingDTO.setRequestId(1L); // Set properties as necessary

        when(bookingService.createRideRequest(any(RideRequest.class))).thenReturn(expectedBookingDTO);

        // Act
        ResponseEntity<BookingDTO> response = bookingController.requestRide(rideRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedBookingDTO, response.getBody());
    }

    @Test
    public void testUpdateRideStatus() {
        // Arrange
        Long bookingId = 1L;
        UpdateRideStatusRequest updateRideStatusRequest = new UpdateRideStatusRequest();
        updateRideStatusRequest.setStatus(RideStatus.COMPLETED); // Use the enum directly

        // Create a Vehicle object
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("V123");
        vehicle.setVehicleName("Sedan");
        vehicle.setVehicleType("Car");
        vehicle.setBasePrice(10000.0);
        vehicle.setPricePerKilometer(5.0);
        vehicle.setStatus(VehicleStatus.AVAILABLE); // Assuming VehicleStatus is an enum

        // Create a Customer object
        Users customer = new Users();
        customer.setEmail("customer@example.com");
        customer.setUsername("John Doe");
        // Set other necessary customer fields

        // Create a Transporter object
        Users transporter = new Users();
        transporter.setEmail("transporter@example.com");
        transporter.setUsername("Jane Doe");
        // Set other necessary transporter fields

        // Create an updated Booking object
        Booking updatedBooking = new Booking();
        updatedBooking.setRequestId(1L);
        updatedBooking.setSource("Source Location");
        updatedBooking.setDestination("Destination Location");
        updatedBooking.setDistance(10.0);
        updatedBooking.setTotalPrice(100.0);
        updatedBooking.setCustomer(customer); // Set the customer
        updatedBooking.setTransporter(transporter); // Set the transporter
        updatedBooking.setVehicle(vehicle); // Set the vehicle
        updatedBooking.setRideStatus(RideStatus.COMPLETED); // Set the ride status
        updatedBooking.setRideDate(LocalDate.now()); // Set today's date
        updatedBooking.setRideTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS)); // Truncate to avoid precision issues

        // Mock the service method
        when(bookingService.updateRideStatus(any(Long.class), any(RideStatus.class))).thenReturn(updatedBooking);

        // Act
        ResponseEntity<Booking> response = bookingController.updateRideStatus(bookingId, updateRideStatusRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Booking responseBooking = response.getBody();

        // Assert Booking fields individually to avoid issues with time precision
        assertEquals(updatedBooking.getRequestId(), responseBooking.getRequestId());
        assertEquals(updatedBooking.getSource(), responseBooking.getSource());
        assertEquals(updatedBooking.getDestination(), responseBooking.getDestination());
        assertEquals(updatedBooking.getDistance(), responseBooking.getDistance(), 0.01); // For double comparison
        assertEquals(updatedBooking.getTotalPrice(), responseBooking.getTotalPrice(), 0.01); // For double comparison
        assertEquals(updatedBooking.getCustomer().getEmail(), responseBooking.getCustomer().getEmail());
        assertEquals(updatedBooking.getTransporter().getEmail(), responseBooking.getTransporter().getEmail());
        assertEquals(updatedBooking.getVehicle().getVehicleNumber(), responseBooking.getVehicle().getVehicleNumber());
        assertEquals(updatedBooking.getRideStatus(), responseBooking.getRideStatus());
        assertEquals(updatedBooking.getRideDate(), responseBooking.getRideDate());
        // Compare the LocalTime values carefully
        assertEquals(updatedBooking.getRideTime().truncatedTo(ChronoUnit.SECONDS), responseBooking.getRideTime().truncatedTo(ChronoUnit.SECONDS));
    }


    @Test
    public void testAddVehicle_Success() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("V123");
        vehicle.setVehicleName("Sedan");
        vehicle.setVehicleType("Car");
        vehicle.setBasePrice(10000.0);
        vehicle.setPricePerKilometer(5.0);
        vehicle.setStatus(VehicleStatus.AVAILABLE); // Assuming VehicleStatus is an enum

        Users transporter = new Users();
        transporter.setEmail("transporter@example.com");
        vehicle.setTransporter(transporter); // Set transporter

        // Mock the service method
        when(bookingService.saveVehicle(vehicle)).thenReturn(vehicle);

        // Act
        ResponseEntity<?> response = bookingController.addVehicle(vehicle);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vehicle, response.getBody());
    }






    @Test
    public void testAddVehicle_UnexpectedException() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("V123");
        vehicle.setVehicleName("Sedan");
        vehicle.setVehicleType("Car");
        vehicle.setBasePrice(10000.0);
        vehicle.setPricePerKilometer(5.0);
        vehicle.setStatus(VehicleStatus.AVAILABLE); // Assuming VehicleStatus is an enum

        // Mock the service method to throw a generic exception
        when(bookingService.saveVehicle(vehicle)).thenThrow(new RuntimeException("Unexpected error"));

        // Act
        ResponseEntity<?> response = bookingController.addVehicle(vehicle);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("An error occurred while adding the vehicle.", response.getBody());
    }


    @Test
    public void testGetVehicle_Success() {
        // Arrange
        String vehicleNumber = "123ABC";
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setVehicleName("Test Vehicle");
        vehicle.setVehicleType("Car");
        vehicle.setBasePrice(100.0);
        vehicle.setPricePerKilometer(10.0);

        // Mock the service to return the vehicle
        when(bookingService.getVehicleByNumber(vehicleNumber)).thenReturn(vehicle);

        // Act
        ResponseEntity<?> response = bookingController.getVehicle(vehicleNumber);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(vehicle, response.getBody());
    }

    @Test
    public void testGetVehicle_VehicleNotFound() {
        // Arrange
        String vehicleNumber = "INVALID123";

        // Mock the service to throw VehicleNotFoundException
        when(bookingService.getVehicleByNumber(vehicleNumber)).thenThrow(new VehicleNotFoundException("Vehicle with number " + vehicleNumber + " not found."));

        // Act
        ResponseEntity<?> response = bookingController.getVehicle(vehicleNumber);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Vehicle with number INVALID123 not found.", response.getBody());
    }

    @Test
    public void testGetVehicle_UnexpectedException() {
        // Arrange
        String vehicleNumber = "ANY_VEHICLE_NUMBER";

        // Mock the service to throw a generic exception
        when(bookingService.getVehicleByNumber(vehicleNumber)).thenThrow(new RuntimeException("Some unexpected error"));

        // Act
        ResponseEntity<?> response = bookingController.getVehicle(vehicleNumber);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("An unexpected error occurred: Some unexpected error", response.getBody());
    }










}


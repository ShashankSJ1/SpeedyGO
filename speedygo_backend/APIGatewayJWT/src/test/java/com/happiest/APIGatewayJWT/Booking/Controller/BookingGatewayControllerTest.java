package com.happiest.APIGatewayJWT.Booking.Controller;
import com.happiest.APIGatewayJWT.APiGateway.Booking.controller.BookingGatewayController;
import com.happiest.APIGatewayJWT.APiGateway.Booking.dto.*;
import com.happiest.APIGatewayJWT.APiGateway.Booking.enums.VehicleStatus;

import com.happiest.APIGatewayJWT.APiGateway.Booking.interfaces.BookingFeignClient;
import com.happiest.APIGatewayJWT.APiGateway.Booking.model.Booking;
import com.happiest.APIGatewayJWT.APiGateway.Booking.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingGatewayControllerTest {

    @InjectMocks
    private BookingGatewayController bookingGatewayController;

    @Mock
    private BookingFeignClient bookingFeignClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void requestRide_ShouldReturnBookingDTO() {
        RideRequest rideRequest = new RideRequest(); // Set up rideRequest object as needed
        BookingDTO expectedResponse = new BookingDTO(); // Set up expected BookingDTO

        when(bookingFeignClient.requestRide(any(RideRequest.class))).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.CREATED));

        ResponseEntity<BookingDTO> response = bookingGatewayController.requestRide(rideRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void updateRideStatus_ShouldReturnBooking() {
        Long id = 1L;
        UpdateRideStatusRequest updateRideStatusRequest = new UpdateRideStatusRequest(); // Set up updateRideStatusRequest
        Booking expectedBooking = new Booking(); // Set up expected Booking object

        when(bookingFeignClient.updateRideStatus(eq(id), any(UpdateRideStatusRequest.class))).thenReturn(new ResponseEntity<>(expectedBooking, HttpStatus.OK));

        ResponseEntity<Booking> response = bookingGatewayController.updateRideStatus(id, updateRideStatusRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBooking, response.getBody());
    }

    @Test
    public void getPendingRequests_ShouldReturnListOfBookings() {
        String transporterEmail = "test@transporter.com";
        Booking booking1 = new Booking(); // Set up booking objects
        Booking booking2 = new Booking();
        List<Booking> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getPendingRequests(transporterEmail)).thenReturn(new ResponseEntity<>(expectedBookings, HttpStatus.OK));

        ResponseEntity<List<Booking>> response = bookingGatewayController.getPendingRequests(transporterEmail);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getCustomerRequests_ShouldReturnListOfBookings() {
        String customerEmail = "test@customer.com";
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        List<Booking> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getCustomerRequests(customerEmail)).thenReturn(new ResponseEntity<>(expectedBookings, HttpStatus.OK));

        ResponseEntity<List<Booking>> response = bookingGatewayController.getCustomerRequests(customerEmail);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getCombinedAvailableVehicles_ShouldReturnListOfVehicles() {
        double distance = 10.0;
        VehicleWithTransporterDTO vehicle1 = new VehicleWithTransporterDTO(); // Set up vehicle objects
        VehicleWithTransporterDTO vehicle2 = new VehicleWithTransporterDTO();
        List<VehicleWithTransporterDTO> expectedVehicles = Arrays.asList(vehicle1, vehicle2);

        when(bookingFeignClient.getCombinedAvailableVehicles(distance)).thenReturn(new ResponseEntity<>(expectedVehicles, HttpStatus.OK));

        ResponseEntity<List<VehicleWithTransporterDTO>> response = bookingGatewayController.getCombinedAvailableVehicles(distance);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedVehicles, response.getBody());
    }

    @Test
    public void updateVehicleStatus_ShouldReturnStringMessage() {
        String vehicleNumber = "V123";
        VehicleStatus status = VehicleStatus.AVAILABLE;

        when(bookingFeignClient.updateVehicleStatus(vehicleNumber, status)).thenReturn(new ResponseEntity<>("Vehicle status updated", HttpStatus.OK));

        ResponseEntity<String> response = bookingGatewayController.updateVehicleStatus(vehicleNumber, status);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vehicle status updated", response.getBody());
    }


    @Test
    public void getRejectedBookingsByTransporterEmail_ShouldReturnListOfBookingDTO() {
        String email = "transporter@example.com";
        BookingDTO booking1 = new BookingDTO(); // Set up bookingDTO objects
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getRejectedBookingsByTransporterEmail(email)).thenReturn(expectedBookings);

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getRejectedBookingsByTransporterEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getRejectedBookingsByCustomerEmail_ShouldReturnListOfBookingDTO() {
        String email = "customer@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getRejectedBookingsByCustomerEmail(email)).thenReturn(expectedBookings);

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getRejectedBookingsByCustomerEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getPendingBookingsByTransporterEmail_ShouldReturnListOfBookingDTO() {
        String email = "transporter@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getPendingBookingsByTransporterEmail(email)).thenReturn(expectedBookings);

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getPendingBookingsByTransporterEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getPendingBookingsByCustomerEmail_ShouldReturnListOfBookingDTO() {
        String email = "customer@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getPendingBookingsByCustomerEmail(email)).thenReturn(expectedBookings);

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getPendingBookingsByCustomerEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getAcceptedBookingsByTransporterEmail_ShouldReturnListOfBookingDTO() {
        String email = "transporter@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getAcceptedBookingsByTransporterEmail(email)).thenReturn(expectedBookings);

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getAcceptedBookingsByTransporterEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getAcceptedBookingsByCustomerEmail_ShouldReturnListOfBookingDTO() {
        String email = "customer@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getAcceptedBookingsByCustomerEmail(email)).thenReturn(expectedBookings);

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getAcceptedBookingsByCustomerEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getCompletedOrRejectedBookingsByCustomerEmail_ShouldReturnListOfBookingDTO() {
        String email = "customer@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getCompletedOrRejectedBookingsByCustomerEmail(email)).thenReturn(expectedBookings);

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getCompletedOrRejectedBookingsByCustomerEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getCompletedOrRejectedBookingsByTransporterEmail_ShouldReturnListOfBookingDTO() {
        String email = "transporter@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getCompletedOrRejectedBookingsByTransporterEmail(email)).thenReturn(expectedBookings);

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getCompletedOrRejectedBookingsByTransporterEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getAcceptedOrPendingBookingsByTransporterEmail_ShouldReturnListOfBookingDTO() {
        String email = "transporter@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getAcceptedOrPendingBookingsByTransporterEmail(email)).thenReturn(expectedBookings);

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getAcceptedOrPendingBookingsByTransporterEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getAcceptedOrPendingBookingsByCustomerEmail_ShouldReturnListOfBookingDTO() {
        String email = "customer@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getAcceptedOrPendingBookingsByCustomerEmail(email)).thenReturn(expectedBookings);

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getAcceptedOrPendingBookingsByCustomerEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void searchVehicles_ShouldReturnListOfVehicles() {
        String keyword = "SUV";
        double distance = 10.0;
        VehicleWithTransporterDTO vehicle1 = new VehicleWithTransporterDTO();
        VehicleWithTransporterDTO vehicle2 = new VehicleWithTransporterDTO();
        List<VehicleWithTransporterDTO> expectedVehicles = Arrays.asList(vehicle1, vehicle2);

        when(bookingFeignClient.searchVehicles(keyword, distance)).thenReturn(expectedVehicles);

        List<VehicleWithTransporterDTO> response = bookingGatewayController.searchVehicles(keyword, distance);
        assertEquals(expectedVehicles, response);
    }



    @Test
    public void getVehicleInfoByEmail_ShouldReturnVehicleStatusDTO() {
        String email = "test@transporter.com";
        VehicleStatusDTO expectedDTO = new VehicleStatusDTO(); // Set up expected DTO

        when(bookingFeignClient.getVehicleStatusByEmail(email)).thenReturn(new ResponseEntity<>(expectedDTO, HttpStatus.OK));

        ResponseEntity<VehicleStatusDTO> response = bookingGatewayController.getVehicleInfoByEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
    }

    @Test
    public void getPaymentBookingsByTransporterEmail_ShouldReturnListOfBookingDTO() {
        String email = "transporter@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getPaymentBookingsByTransporterEmail(email)).thenReturn(new ResponseEntity<>(expectedBookings, HttpStatus.OK));

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getPaymentBookingsByTransporterEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }

    @Test
    public void getPaymentBookingsByCustomerEmail_ShouldReturnListOfBookingDTO() {
        String email = "customer@example.com";
        BookingDTO booking1 = new BookingDTO();
        BookingDTO booking2 = new BookingDTO();
        List<BookingDTO> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingFeignClient.getPaymentBookingsByCustomerEmail(email)).thenReturn(new ResponseEntity<>(expectedBookings, HttpStatus.OK));

        ResponseEntity<List<BookingDTO>> response = bookingGatewayController.getPaymentBookingsByCustomerEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookings, response.getBody());
    }



}


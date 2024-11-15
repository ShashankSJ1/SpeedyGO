package com.happiest.BookingService;



import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.happiest.BookingService.dto.*;
import com.happiest.BookingService.exceptions.VehicleSaveException;
import com.happiest.BookingService.model.Booking;
import com.happiest.BookingService.model.Users;
import com.happiest.BookingService.model.Vehicle;
import com.happiest.BookingService.model.enums.RideStatus;
import com.happiest.BookingService.model.enums.Role;
import com.happiest.BookingService.model.enums.VehicleStatus;
import com.happiest.BookingService.repository.BookingRepository;
import com.happiest.BookingService.repository.UserRepository;
import com.happiest.BookingService.repository.VehicleRepository;
import com.happiest.BookingService.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private UserRepository userRepository;

    private Users customer;
    private Users transporter;
    private Vehicle vehicle;
    private RideRequest rideRequest;
    private Booking booking;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initializing Users instances according to the existing Users entity structure
        customer = new Users();
        customer.setEmail("customer@example.com");
        customer.setUsername("Customer");
        customer.setPassword("customer123");
        customer.setPhonenumber(1234567890L);
        customer.setRoles(Role.CUSTOMER);

        transporter = new Users();
        transporter.setEmail("transporter@example.com");
        transporter.setUsername("Transporter");
        transporter.setPassword("transporter123");
        transporter.setPhonenumber(9876543210L);
        transporter.setRoles(Role.TRANSPORTER);

        vehicle = new Vehicle();
        vehicle.setVehicleNumber("V123");
        vehicle.setVehicleName("Car");
        vehicle.setVehicleType("Sedan");
        vehicle.setBasePrice(1000.0);
        vehicle.setPricePerKilometer(10.0);
        vehicle.setTransporter(transporter); // Setting the transporter for the vehicle

        // Setting up RideRequest with necessary details
        rideRequest = new RideRequest();
        rideRequest.setCustomerEmail("customer@example.com");
        rideRequest.setTransporterEmail("transporter@example.com");
        rideRequest.setVehicleNumber("V123");
        rideRequest.setSource("A");
        rideRequest.setDestination("B");
        rideRequest.setDistance(50.0);
        rideRequest.setRideDate(LocalDate.now());
        rideRequest.setRideTime(LocalTime.now());

        // Setting up Booking instance
        booking = new Booking();
        booking.setRequestId(1L);
        booking.setVehicle(vehicle);
        booking.setRideStatus(RideStatus.PENDING);
    }



    @Test
    public void testCreateRideRequest() {
        // Mocking the repository responses
        when(userRepository.findByEmail(rideRequest.getCustomerEmail())).thenReturn(customer);
        when(userRepository.findByEmail(rideRequest.getTransporterEmail())).thenReturn(transporter);
        when(vehicleRepository.findByVehicleNumber(rideRequest.getVehicleNumber())).thenReturn(vehicle);

        // Calling the service method
        BookingDTO result = bookingService.createRideRequest(rideRequest);

        // Assertions to verify the result
        assertNotNull(result);
        assertEquals("A", result.getSource());
        assertEquals("B", result.getDestination());
        assertEquals(50.0, result.getDistance());
        assertEquals(1000.0 + (50.0 * 10.0), result.getTotalPrice());
        assertEquals(RideStatus.PENDING, result.getRideStatus());

        // Verifying that save was called on bookingRepository
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testUpdateRideStatus_Accepted() {
        // Setting up the booking object with initial values
        booking = new Booking();
        booking.setRequestId(1L);
        booking.setVehicle(vehicle); // Make sure to link the vehicle
        booking.setRideStatus(RideStatus.PENDING); // Set an initial status

        // Mocking the repository response to return the booking when finding by ID
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // Mocking the save method to return the updated booking
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Calling the service method
        Booking updatedBooking = bookingService.updateRideStatus(1L, RideStatus.ACCEPTED);

        // Assertions to verify the updated status
        assertEquals(RideStatus.ACCEPTED, updatedBooking.getRideStatus());
        verify(vehicleRepository, times(1)).save(vehicle); // Ensure save was called on vehicle
        verify(bookingRepository, times(1)).save(updatedBooking); // Ensure save was called on booking
    }


    @Test
    public void testUpdateRideStatus_RideRequestNotFound() {
        // Mocking the repository response to return empty for a non-existing requestId
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        // Exception should be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.updateRideStatus(1L, RideStatus.ACCEPTED);
        });

        assertEquals("Ride request not found", exception.getMessage());
    }

    @Test
    public void testUpdateRideStatus_Rejected() {
        // Setting up the booking object with initial values
        booking = new Booking();
        booking.setRequestId(1L);
        booking.setVehicle(vehicle); // Link the vehicle
        booking.setRideStatus(RideStatus.PENDING); // Initial status

        // Mocking the repository response to return the booking when finding by ID
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // Mocking the save method to return the updated booking
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Calling the service method
        Booking updatedBooking = bookingService.updateRideStatus(1L, RideStatus.REJECTED);

        // Assertions to verify the updated status
        assertEquals(RideStatus.REJECTED, updatedBooking.getRideStatus());
        verify(vehicleRepository, times(1)).save(vehicle); // Ensure save was called on vehicle
        verify(bookingRepository, times(1)).save(updatedBooking); // Ensure save was called on booking
    }

    @Test
    public void testUpdateRideStatus_Completed() {
        // Setting up the booking object with initial values
        booking = new Booking();
        booking.setRequestId(1L);
        booking.setVehicle(vehicle); // Link the vehicle
        booking.setRideStatus(RideStatus.PENDING); // Initial status

        // Mocking the repository response to return the booking when finding by ID
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // Mocking the save method to return the updated booking
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Calling the service method
        Booking updatedBooking = bookingService.updateRideStatus(1L, RideStatus.COMPLETED);

        // Assertions to verify the updated status
        assertEquals(RideStatus.COMPLETED, updatedBooking.getRideStatus());
        verify(vehicleRepository, times(1)).save(vehicle); // Ensure save was called on vehicle
        verify(bookingRepository, times(1)).save(updatedBooking); // Ensure save was called on booking
    }
    @Test
    public void testUpdateRideStatus_Cancelled() {
        // Setting up the booking object with initial values
        booking = new Booking();
        booking.setRequestId(1L);
        booking.setVehicle(vehicle); // Link the vehicle
        booking.setRideStatus(RideStatus.PENDING); // Initial status

        // Mocking the repository response to return the booking when finding by ID
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // Mocking the save method to return the updated booking
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Calling the service method
        Booking updatedBooking = bookingService.updateRideStatus(1L, RideStatus.CANCELLED);

        // Assertions to verify the updated status
        assertEquals(RideStatus.CANCELLED, updatedBooking.getRideStatus());
        verify(vehicleRepository, times(1)).save(vehicle); // Ensure save was called on vehicle
        verify(bookingRepository, times(1)).save(updatedBooking); // Ensure save was called on booking
    }
    @Test
    public void testUpdateRideStatus_Payment() {
        // Setting up the booking object with initial values
        booking = new Booking();
        booking.setRequestId(1L);
        booking.setVehicle(vehicle); // Link the vehicle
        booking.setRideStatus(RideStatus.PENDING); // Initial status

        // Mocking the repository response to return the booking when finding by ID
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // Mocking the save method to return the updated booking
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Calling the service method
        Booking updatedBooking = bookingService.updateRideStatus(1L, RideStatus.PAYMENT);

        // Assertions to verify the updated status
        assertEquals(RideStatus.PAYMENT, updatedBooking.getRideStatus());
        verify(vehicleRepository, times(1)).save(vehicle); // Ensure save was called on vehicle
        verify(bookingRepository, times(1)).save(updatedBooking); // Ensure save was called on booking
    }



    @Test
    public void testUpdateRideStatus_UnknownStatus() {
        // Mocking the repository response
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // Calling the service method with a null status should throw NullPointerException
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            bookingService.updateRideStatus(1L, null);
        });

        // Verifying the exception message
        assertEquals("Cannot invoke \"com.happiest.BookingService.model.enums.RideStatus.ordinal()\" because \"status\" is null", exception.getMessage());
    }

    @Test
    public void testGetPendingRequests() {
        // Prepare mock data
        List<Booking> pendingBookings = new ArrayList<>();
        pendingBookings.add(booking);

        // Mocking the repository response
        when(bookingRepository.findByTransporterEmailAndRideStatus(transporter.getEmail(), RideStatus.PENDING))
                .thenReturn(pendingBookings);

        // Calling the service method
        List<Booking> result = bookingService.getPendingRequests(transporter.getEmail());

        // Assertions to verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(booking.getRequestId(), result.get(0).getRequestId());

        // Verify that the repository method was called
        verify(bookingRepository, times(1)).findByTransporterEmailAndRideStatus(transporter.getEmail(), RideStatus.PENDING);
    }

    @Test
    public void testGetCustomerRequests() {
        // Prepare mock data
        List<Booking> customerPendingBookings = new ArrayList<>();
        customerPendingBookings.add(booking);

        // Mocking the repository response
        when(bookingRepository.findByCustomerEmailAndRideStatus(customer.getEmail(), RideStatus.PENDING))
                .thenReturn(customerPendingBookings);

        // Calling the service method
        List<Booking> result = bookingService.getCustomerRequests(customer.getEmail());

        // Assertions to verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(booking.getRequestId(), result.get(0).getRequestId());

        // Verify that the repository method was called
        verify(bookingRepository, times(1)).findByCustomerEmailAndRideStatus(customer.getEmail(), RideStatus.PENDING);
    }

    @Test
    public void testCreateRideRequest_CustomerNotFound() {
        // Setting up a RideRequest with non-existing customer
        RideRequest rideRequest = new RideRequest();
        rideRequest.setCustomerEmail("nonexistent@customer.com");
        rideRequest.setTransporterEmail("transporter@example.com");
        rideRequest.setVehicleNumber("123ABC");

        // Mocking the repository to return null for the customer
        when(userRepository.findByEmail("nonexistent@customer.com")).thenReturn(null);

        // Expecting an IllegalArgumentException to be thrown with the specific message
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createRideRequest(rideRequest);
        });

        assertEquals("Customer not found", exception.getMessage());

    }

    @Test
    public void testCreateRideRequest_VehicleNotFound() {
        // Setting up a RideRequest with non-existing vehicle
        RideRequest rideRequest = new RideRequest();
        rideRequest.setCustomerEmail("customer@example.com");
        rideRequest.setTransporterEmail("transporter@example.com");
        rideRequest.setVehicleNumber("nonexistentVehicle");

        // Mocking the repository to return valid customer and transporter but null vehicle
        Users customer = new Users();
        customer.setEmail("customer@example.com");
        Users transporter = new Users();
        transporter.setEmail("transporter@example.com");

        when(userRepository.findByEmail("customer@example.com")).thenReturn(customer);
        when(userRepository.findByEmail("transporter@example.com")).thenReturn(transporter);
        when(vehicleRepository.findByVehicleNumber("nonexistentVehicle")).thenReturn(null);

        // Expecting an IllegalArgumentException to be thrown with the specific message
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createRideRequest(rideRequest);
        });

        assertEquals("Vehicle not found", exception.getMessage());
    }




    @Test
    public void testGetCompletedOrRejectedBookingsByTransporterEmail() {
        // Given
        String transporterEmail = transporter.getEmail(); // Using transporter email from the setup

        // Prepare a list of bookings
        booking.setTransporter(transporter); // Set transporter in booking
        booking.setCustomer(customer); // Set customer in booking
        booking.setRideStatus(RideStatus.COMPLETED); // Set a completed status

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        // Mock the repository response
        when(bookingRepository.findCompletedOrRejectedByTransporterEmail(transporterEmail)).thenReturn(bookings);

        // Call the service method
        List<BookingDTO> result = bookingService.getCompletedOrRejectedBookingsByTransporterEmail(transporterEmail);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(booking.getRequestId(), result.get(0).getRequestId()); // Assuming getId() is in BookingDTO

        // Verify that the repository method was called
        verify(bookingRepository, times(1)).findCompletedOrRejectedByTransporterEmail(transporterEmail);
    }

    @Test
    public void testGetCompletedOrRejectedBookingsByCustomerEmail() {
        // Given
        String customerEmail = customer.getEmail(); // Using customer email from the setup

        // Prepare a list of bookings
        booking.setTransporter(transporter); // Set transporter in booking
        booking.setCustomer(customer); // Set customer in booking
        booking.setRideStatus(RideStatus.REJECTED); // Set a rejected status

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        // Mock the repository response
        when(bookingRepository.findCompletedOrRejectedByCustomerEmail(customerEmail)).thenReturn(bookings);

        // Call the service method
        List<BookingDTO> result = bookingService.getCompletedOrRejectedBookingsByCustomerEmail(customerEmail);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(booking.getRequestId(), result.get(0).getRequestId()); // Assuming getId() is in BookingDTO

        // Verify that the repository method was called
        verify(bookingRepository, times(1)).findCompletedOrRejectedByCustomerEmail(customerEmail);
    }


    @Test
    public void testGetCompletedOrRejectedBookingsByTransporterEmail_NoBookings() {
        // Given
        String transporterEmail = "transporter@example.com";

        // Mock the repository response to return an empty list
        when(bookingRepository.findCompletedOrRejectedByTransporterEmail(transporterEmail)).thenReturn(Collections.emptyList());

        // Call the service method
        List<BookingDTO> result = bookingService.getCompletedOrRejectedBookingsByTransporterEmail(transporterEmail);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty()); // Expecting an empty list

        // Verify that the repository method was called
        verify(bookingRepository, times(1)).findCompletedOrRejectedByTransporterEmail(transporterEmail);
    }

    @Test
    public void testGetCompletedOrRejectedBookingsByCustomerEmail_NoBookings() {
        // Given
        String customerEmail = "customer@example.com";

        // Mock the repository response to return an empty list
        when(bookingRepository.findCompletedOrRejectedByCustomerEmail(customerEmail)).thenReturn(Collections.emptyList());

        // Call the service method
        List<BookingDTO> result = bookingService.getCompletedOrRejectedBookingsByCustomerEmail(customerEmail);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty()); // Expecting an empty list

        // Verify that the repository method was called
        verify(bookingRepository, times(1)).findCompletedOrRejectedByCustomerEmail(customerEmail);
    }

    @Test
    public void testGetAcceptedOrPendingBookingsByTransporterEmail() {
        // Given
        String transporterEmail = transporter.getEmail(); // Using transporter email from the setup

        // Prepare a list of bookings
        booking.setTransporter(transporter); // Set transporter in booking
        booking.setCustomer(customer); // Set customer in booking
        booking.setRideStatus(RideStatus.ACCEPTED); // Set an accepted status

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        // Mock the repository response
        when(bookingRepository.findAcceptedOrPendingByTransporterEmail(transporterEmail)).thenReturn(bookings);

        // Call the service method
        List<BookingDTO> result = bookingService.getAcceptedOrPendingBookingsByTransporterEmail(transporterEmail);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(booking.getRequestId(), result.get(0).getRequestId()); // Assuming getId() is in BookingDTO

        // Verify that the repository method was called
        verify(bookingRepository, times(1)).findAcceptedOrPendingByTransporterEmail(transporterEmail);
    }

    @Test
    public void testGetAcceptedOrPendingBookingsByCustomerEmail() {
        // Given
        String customerEmail = customer.getEmail(); // Using customer email from the setup

        // Prepare a list of bookings
        booking.setTransporter(transporter); // Set transporter in booking
        booking.setCustomer(customer); // Set customer in booking
        booking.setRideStatus(RideStatus.PENDING); // Set a pending status

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        // Mock the repository response
        when(bookingRepository.findAcceptedOrPendingByCustomerEmail(customerEmail)).thenReturn(bookings);

        // Call the service method
        List<BookingDTO> result = bookingService.getAcceptedOrPendingBookingsByCustomerEmail(customerEmail);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(booking.getRequestId(), result.get(0).getRequestId()); // Assuming getId() is in BookingDTO

        // Verify that the repository method was called
        verify(bookingRepository, times(1)).findAcceptedOrPendingByCustomerEmail(customerEmail);
    }


    @Test
    public void testUpdateVehicleStatus_Success() {
        // Given
        String vehicleNumber = vehicle.getVehicleNumber();
        VehicleStatus newStatus = VehicleStatus.NOT_AVAILABLE;

        // Mock the behavior of the repository
        when(vehicleRepository.findById(vehicleNumber)).thenReturn(Optional.of(vehicle));

        // Call the method to test
        bookingService.updateVehicleStatus(vehicleNumber, newStatus);

        // Verify that the status was updated and the vehicle was saved
        assertEquals(newStatus, vehicle.getStatus());
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    public void testUpdateVehicleStatus_VehicleNotFound() {
        // Given
        String vehicleNumber = "NonExistentVehicle";
        VehicleStatus newStatus = VehicleStatus.NOT_AVAILABLE;

        // Mock the repository to return an empty Optional
        when(vehicleRepository.findById(vehicleNumber)).thenReturn(Optional.empty());

        // Call the method and assert that an EntityNotFoundException is thrown
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            bookingService.updateVehicleStatus(vehicleNumber, newStatus);
        });

        assertEquals("Vehicle not found with number: " + vehicleNumber, exception.getMessage());
    }

    @Test
    public void testSaveVehicle_Success() {
        // Given
        Vehicle vehicleToSave = new Vehicle();
        vehicleToSave.setVehicleNumber("V124");
        vehicleToSave.setVehicleName("Bike");
        vehicleToSave.setVehicleType("Motorcycle");
        vehicleToSave.setBasePrice(800.0);
        vehicleToSave.setPricePerKilometer(5.0);
        vehicleToSave.setTransporter(transporter); // Assuming transporter is already set
        vehicleToSave.setStatus(VehicleStatus.AVAILABLE); // Set initial status

        // Mock the behavior of the repository
        when(vehicleRepository.save(vehicleToSave)).thenReturn(vehicleToSave);

        // Call the method to test
        Vehicle savedVehicle = bookingService.saveVehicle(vehicleToSave);

        // Assertions
        assertNotNull(savedVehicle);
        assertEquals(vehicleToSave.getVehicleNumber(), savedVehicle.getVehicleNumber());

        // Verify that the save method was called
        verify(vehicleRepository, times(1)).save(vehicleToSave);
    }

    @Test
    public void testSaveVehicle_ExceptionThrown() {
        // Given
        Vehicle vehicleToSave = new Vehicle();
        vehicleToSave.setVehicleNumber("V125");
        vehicleToSave.setVehicleName("Truck");
        vehicleToSave.setVehicleType("Lorry");
        vehicleToSave.setBasePrice(2000.0);
        vehicleToSave.setPricePerKilometer(15.0);
        vehicleToSave.setTransporter(transporter); // Assuming transporter is already set
        vehicleToSave.setStatus(VehicleStatus.AVAILABLE); // Set initial status

        // Mock the repository to throw an exception
        when(vehicleRepository.save(vehicleToSave)).thenThrow(new RuntimeException("Database error"));

        // Call the method and assert that VehicleSaveException is thrown
        Exception exception = assertThrows(VehicleSaveException.class, () -> {
            bookingService.saveVehicle(vehicleToSave);
        });

        assertEquals("Failed to save the vehicle. Please try again.", exception.getMessage());
    }



    @Test
    public void testGetRejectedBookingsByTransporterEmail() {
        // Arrange
        booking.setRideStatus(RideStatus.REJECTED); // Set status to REJECTED
        booking.setCustomer(customer); // Link customer to booking
        booking.setTransporter(transporter); // Link transporter to booking

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        when(bookingRepository.findRejectedByTransporterEmail(transporter.getEmail())).thenReturn(bookings);

        // Act
        List<BookingDTO> result = bookingService.getRejectedBookingsByTransporterEmail(transporter.getEmail());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(RideStatus.REJECTED, result.get(0).getRideStatus());
        assertEquals("customer@example.com", result.get(0).getCustomerInfo().getEmail());
    }

    @Test
    public void testGetRejectedBookingsByCustomerEmail() {
        // Arrange
        booking.setRideStatus(RideStatus.REJECTED); // Set status to REJECTED
        booking.setCustomer(customer); // Link customer to booking
        booking.setTransporter(transporter); // Link transporter to booking

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        when(bookingRepository.findRejectedByCustomerEmail(customer.getEmail())).thenReturn(bookings);

        // Act
        List<BookingDTO> result = bookingService.getRejectedBookingsByCustomerEmail(customer.getEmail());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(RideStatus.REJECTED, result.get(0).getRideStatus());
        assertEquals("transporter@example.com", result.get(0).getTransporterInfo().getEmail());
    }

    @Test
    public void testGetPendingBookingsByTransporterEmail() {
        // Arrange
        booking.setRideStatus(RideStatus.PENDING); // Set status to PENDING
        booking.setCustomer(customer); // Link customer to booking
        booking.setTransporter(transporter); // Link transporter to booking

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        when(bookingRepository.findPendingByTransporterEmail(transporter.getEmail())).thenReturn(bookings);

        // Act
        List<BookingDTO> result = bookingService.getPendingBookingsByTransporterEmail(transporter.getEmail());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(RideStatus.PENDING, result.get(0).getRideStatus());
    }

    @Test
    public void testGetPendingBookingsByCustomerEmail() {
        // Arrange
        booking.setRideStatus(RideStatus.PENDING); // Set status to PENDING
        booking.setCustomer(customer); // Link customer to booking
        booking.setTransporter(transporter); // Link transporter to booking

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        when(bookingRepository.findPendingByCustomerEmail(customer.getEmail())).thenReturn(bookings);

        // Act
        List<BookingDTO> result = bookingService.getPendingBookingsByCustomerEmail(customer.getEmail());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(RideStatus.PENDING, result.get(0).getRideStatus());
    }

// Add similar tests for accepted and payment bookings


    @Test
    public void testGetPaymentBookingsByTransporterEmail() {
        // Given
        booking.setRideStatus(RideStatus.COMPLETED); // Set ride status to PAID
        booking.setCustomer(customer); // Link customer to booking
        booking.setTransporter(transporter); // Link transporter to booking

        when(bookingRepository.findPaymentByTransporterEmail(transporter.getEmail()))
                .thenReturn(Arrays.asList(booking));

        // When
        List<BookingDTO> result = bookingService.getPaymentBookingsByTransporterEmail(transporter.getEmail());

        // Then
        assertEquals(1, result.size());
        assertEquals(booking.getRequestId(), result.get(0).getRequestId());
        assertEquals("customer@example.com", result.get(0).getCustomerInfo().getEmail()); // Example assert for customer info
    }

    @Test
    public void testGetPaymentBookingsByCustomerEmail() {
        // Given
        booking.setRideStatus(RideStatus.COMPLETED); // Set ride status to PAID
        booking.setCustomer(customer); // Link customer to booking
        booking.setTransporter(transporter); // Link transporter to booking

        when(bookingRepository.findPaymentByCustomerEmail(customer.getEmail()))
                .thenReturn(Arrays.asList(booking));

        // When
        List<BookingDTO> result = bookingService.getPaymentBookingsByCustomerEmail(customer.getEmail());

        // Then
        assertEquals(1, result.size());
        assertEquals(booking.getRequestId(), result.get(0).getRequestId());
        assertEquals("transporter@example.com", result.get(0).getTransporterInfo().getEmail()); // Example assert for transporter info
    }

    @Test
    public void testGetAcceptedBookingsByTransporterEmail() {
        // Given
        booking.setRideStatus(RideStatus.ACCEPTED); // Set ride status to ACCEPTED
        booking.setCustomer(customer); // Link customer to booking
        booking.setTransporter(transporter); // Link transporter to booking

        when(bookingRepository.findAcceptedByTransporterEmail(transporter.getEmail()))
                .thenReturn(Arrays.asList(booking));

        // When
        List<BookingDTO> result = bookingService.getAcceptedBookingsByTransporterEmail(transporter.getEmail());

        // Then
        assertEquals(1, result.size());
        assertEquals(booking.getRequestId(), result.get(0).getRequestId());
        assertEquals("customer@example.com", result.get(0).getCustomerInfo().getEmail()); // Example assert for customer info
    }

    @Test
    public void testGetAcceptedBookingsByCustomerEmail() {
        // Given
        booking.setRideStatus(RideStatus.ACCEPTED); // Set ride status to ACCEPTED
        booking.setCustomer(customer); // Link customer to booking
        booking.setTransporter(transporter); // Link transporter to booking

        when(bookingRepository.findAcceptedByCustomerEmail(customer.getEmail()))
                .thenReturn(Arrays.asList(booking));

        // When
        List<BookingDTO> result = bookingService.getAcceptedBookingsByCustomerEmail(customer.getEmail());

        // Then
        assertEquals(1, result.size());
        assertEquals(booking.getRequestId(), result.get(0).getRequestId());
        assertEquals("transporter@example.com", result.get(0).getTransporterInfo().getEmail()); // Example assert for transporter info
    }



    @Test
    public void testSearchVehicles() {
        // Given
        String keyword = "Car";
        double distance = 50.0;

        // Mock the repository method
        when(vehicleRepository.searchVehicles(keyword)).thenReturn(Arrays.asList(vehicle));

        // When
        List<VehicleWithTransporterDTO> result = bookingService.searchVehicles(keyword, distance);

        // Then
        assertEquals(1, result.size());
        VehicleWithTransporterDTO vehicleWithTransporterDTO = result.get(0);
        assertEquals("Car", vehicleWithTransporterDTO.getVehicleInfo().getVehicleName());
        assertEquals(1500.0, vehicleWithTransporterDTO.getVehicleInfo().getTotalPrice());
        assertEquals("transporter@example.com", vehicleWithTransporterDTO.getTransporterInfo().getEmail());
        assertEquals("Transporter", vehicleWithTransporterDTO.getTransporterInfo().getUsername());
        assertEquals(9876543210L, vehicleWithTransporterDTO.getTransporterInfo().getPhoneNumber());
    }



    @Test
    public void testFilterVehicles() {
        // Given
        String vehicleType = "Sedan";
        Double basePrice = 1000.0;
        Double pricePerKilometer = 10.0;
        double distance = 50.0;

        // Mock the repository method
        when(vehicleRepository.filterVehicles(vehicleType, basePrice, pricePerKilometer))
                .thenReturn(Arrays.asList(vehicle));

        // When
        List<VehicleWithTransporterDTO> result = bookingService.filterVehicles(
                vehicleType,
                basePrice,
                pricePerKilometer,
                "basePrice",
                "asc",
                null,
                null,
                null,
                null,
                distance
        );

        // Then
        assertEquals(1, result.size());
        VehicleWithTransporterDTO vehicleWithTransporterDTO = result.get(0);
        assertEquals("Car", vehicleWithTransporterDTO.getVehicleInfo().getVehicleName());
        assertEquals(1500.0, vehicleWithTransporterDTO.getVehicleInfo().getTotalPrice());
        assertEquals("transporter@example.com", vehicleWithTransporterDTO.getTransporterInfo().getEmail());
        assertEquals("Transporter", vehicleWithTransporterDTO.getTransporterInfo().getUsername());
        assertEquals(9876543210L, vehicleWithTransporterDTO.getTransporterInfo().getPhoneNumber());
    }

    @Test
    public void testFilterVehicles_NegativeDistance() {
        // Given
        double negativeDistance = -5.0;

        // When & Then
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class, () -> {
                    bookingService.filterVehicles(
                            "Sedan",
                            1000.0,
                            10.0,
                            "basePrice",
                            "asc",
                            null,
                            null,
                            null,
                            null,
                            negativeDistance
                    );
                });
        assertEquals("Distance must be a non-negative value.", thrown.getMessage());
    }


    @Test
    public void testGetVehicleStatusByEmail_Success() {
        // Given
        String email = "transporter@example.com";

        // Initialize the Vehicle instance with status
        vehicle.setStatus(VehicleStatus.AVAILABLE); // Ensure the status is set

        // Mock the repository method to return the vehicle with status
        when(vehicleRepository.findByTransporterEmail(email)).thenReturn(Optional.of(vehicle));

        // When
        VehicleStatusDTO result = bookingService.getVehicleStatusByEmail(email);

        // Log the status for debugging
        System.out.println("Vehicle status: " + result.getStatus()); // This will help you debug if necessary

        // Then
        assertEquals("V123", result.getVehicleNumber());
        assertEquals(VehicleStatus.AVAILABLE, result.getStatus()); // Check for the correct status
    }




    @Test
    public void testGetVehicleStatusByEmail_EntityNotFound() {
        // Given
        String email = "nonexistent@example.com";

        // Mock the repository method to return an empty Optional
        when(vehicleRepository.findByTransporterEmail(email)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException thrown =
                assertThrows(EntityNotFoundException.class, () -> {
                    bookingService.getVehicleStatusByEmail(email);
                });
        assertEquals("No vehicle found for transporter with email: " + email, thrown.getMessage());
    }




    private int compareByField(Vehicle v1, Vehicle v2, String sortField, String sortDirection) {
        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "asc"; // Default sorting direction
        }

        Comparator<Vehicle> comparator;

        switch (sortField != null ? sortField.toLowerCase() : "") {
            case "vehicletype":
                comparator = Comparator.comparing(Vehicle::getVehicleType, Comparator.nullsLast(Comparator.naturalOrder()));
                break;
            case "baseprice":
                comparator = Comparator.comparing(Vehicle::getBasePrice, Comparator.nullsLast(Comparator.naturalOrder()));
                break;
            case "priceperkilometer":
                comparator = Comparator.comparing(Vehicle::getPricePerKilometer, Comparator.nullsLast(Comparator.naturalOrder()));
                break;
            default:
                throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }

        return ("asc".equalsIgnoreCase(sortDirection) ? comparator : comparator.reversed()).compare(v1, v2);
    }

    @Test
    void testCompareByVehicleTypeAscending() {
        Vehicle v1 = new Vehicle("V001", "Car", "Car", 10000, 2.5, null, VehicleStatus.AVAILABLE);
        Vehicle v2 = new Vehicle("V002", "Bike", "Bike", 5000, 1.0, null, VehicleStatus.AVAILABLE);
        assertTrue(compareByField(v1, v2, "vehicleType", "asc") > 0);
    }

    @Test
    void testCompareByVehicleTypeDescending() {
        Vehicle v1 = new Vehicle("V001", "Car", "Car", 10000, 2.5, null, VehicleStatus.AVAILABLE);
        Vehicle v2 = new Vehicle("V002", "Bike", "Bike", 5000, 1.0, null, VehicleStatus.AVAILABLE);
        assertTrue(compareByField(v1, v2, "vehicleType", "desc") < 0);
    }

    @Test
    void testCompareByBasePriceAscending() {
        Vehicle v1 = new Vehicle("V001", "Car", "Car", 15000, 2.5, null, VehicleStatus.AVAILABLE);
        Vehicle v2 = new Vehicle("V002", "Bike", "Bike", 10000, 1.0, null, VehicleStatus.AVAILABLE);
        assertTrue(compareByField(v1, v2, "basePrice", "asc") > 0);
    }

    @Test
    void testCompareByBasePriceDescending() {
        Vehicle v1 = new Vehicle("V001", "Car", "Car", 15000, 2.5, null, VehicleStatus.AVAILABLE);
        Vehicle v2 = new Vehicle("V002", "Bike", "Bike", 10000, 1.0, null, VehicleStatus.AVAILABLE);
        assertTrue(compareByField(v1, v2, "basePrice", "desc") < 0);
    }

    @Test
    void testCompareByPricePerKilometerAscending() {
        Vehicle v1 = new Vehicle("V001", "Car", "Car", 15000, 3.0, null, VehicleStatus.AVAILABLE);
        Vehicle v2 = new Vehicle("V002", "Bike", "Bike", 10000, 2.5, null, VehicleStatus.AVAILABLE);
        assertTrue(compareByField(v1, v2, "pricePerKilometer", "asc") > 0);
    }

    @Test
    void testCompareByPricePerKilometerDescending() {
        Vehicle v1 = new Vehicle("V001", "Car", "Car", 15000, 3.0, null, VehicleStatus.AVAILABLE);
        Vehicle v2 = new Vehicle("V002", "Bike", "Bike", 10000, 2.5, null, VehicleStatus.AVAILABLE);
        assertTrue(compareByField(v1, v2, "pricePerKilometer", "desc") < 0);
    }





    @Test
    void testInvalidSortField() {
        Vehicle v1 = new Vehicle("V001", "Car", "Car", 10000, 2.5, null, VehicleStatus.AVAILABLE);
        Vehicle v2 = new Vehicle("V002", "Bike", "Bike", 5000, 1.0, null, VehicleStatus.AVAILABLE);
        assertThrows(IllegalArgumentException.class, () -> {
            compareByField(v1, v2, "invalidField", "asc");
        });
    }


    @Test
    public void getEarningsByTransporter_ShouldReturnEarnings() {
        // Prepare test data
        String transporterEmail = "transporter@example.com";
        Booking booking1 = new Booking();
        booking1.setRideDate(LocalDate.of(2024, 10, 22));
        booking1.setRideTime(LocalTime.of(10, 0));
        booking1.setTotalPrice(100.0);

        Booking booking2 = new Booking();
        booking2.setRideDate(LocalDate.of(2024, 10, 22));
        booking2.setRideTime(LocalTime.of(10, 0));
        booking2.setTotalPrice(150.0);

        when(bookingRepository.findByTransporterEmail(transporterEmail)).thenReturn(Arrays.asList(booking1, booking2));

        // Call the method
        List<TransporterEarningsDTO> earnings = bookingService.getEarningsByTransporter(transporterEmail);

        // Assertions
        assertEquals(1, earnings.size()); // Expecting one entry since both bookings are at the same date/time
        assertEquals(250.0, earnings.get(0).getTotalEarnings());
        assertEquals(LocalDateTime.of(2024, 10, 22, 10, 0), earnings.get(0).getDateTime());
    }

    @Test
    public void getEarningsByTransporter_NoBookings_ShouldReturnEmptyList() {
        // Prepare test data
        String transporterEmail = "transporter@example.com";
        when(bookingRepository.findByTransporterEmail(transporterEmail)).thenReturn(Arrays.asList());

        // Call the method
        List<TransporterEarningsDTO> earnings = bookingService.getEarningsByTransporter(transporterEmail);

        // Assertions
        assertEquals(0, earnings.size());
    }




}

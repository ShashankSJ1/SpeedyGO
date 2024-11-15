package com.happiest.BookingService.controller;

import com.happiest.BookingService.dto.*;
import com.happiest.BookingService.exceptions.VehicleNotFoundException;
import com.happiest.BookingService.exceptions.VehicleSaveException;
import com.happiest.BookingService.model.Booking;
import com.happiest.BookingService.model.Users;
import com.happiest.BookingService.model.Vehicle;
import com.happiest.BookingService.model.enums.RideStatus;
import com.happiest.BookingService.model.enums.VehicleStatus;
import com.happiest.BookingService.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create a ride request
    @PostMapping("/request-ride")
    public ResponseEntity<BookingDTO> requestRide(@RequestBody RideRequest rideRequest) {
        BookingDTO bookingDTO = bookingService.createRideRequest(rideRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);
    }



    // Update the status of a ride request
    @PutMapping("/update-ride-status/{id}")
    public ResponseEntity<Booking> updateRideStatus(
            @PathVariable Long id,
            @RequestBody UpdateRideStatusRequest updateRideStatusRequest) {
        Booking updatedBooking = bookingService.updateRideStatus(id, updateRideStatusRequest.getStatus());
        return ResponseEntity.ok(updatedBooking);
    }



    // Retrieve pending requests for a transporter
    @GetMapping("/transporter/{transporterEmail}/pending-requests")
    public ResponseEntity<List<Booking>> getPendingRequests(@PathVariable String transporterEmail) {
        List<Booking> pendingRequests = bookingService.getPendingRequests(transporterEmail);
        return ResponseEntity.ok(pendingRequests);
    }

    // Retrieve customer requests
    @GetMapping("/customer/{customerEmail}/requests")
    public ResponseEntity<List<Booking>> getCustomerRequests(@PathVariable String customerEmail) {
        List<Booking> customerRequests = bookingService.getCustomerRequests(customerEmail);
        return ResponseEntity.ok(customerRequests);
    }



    @GetMapping("/available")
    public ResponseEntity<List<VehicleWithTransporterDTO>> getCombinedAvailableVehicles(@RequestParam("distance") double distance) {
        return ResponseEntity.ok(bookingService.getCombinedAvailableVehicles(distance));
    }



    @GetMapping("/completed-or-rejected/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getCompletedOrRejectedBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getCompletedOrRejectedBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get list of completed or rejected bookings for customer by email
    @GetMapping("/completed-or-rejected/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getCompletedOrRejectedBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getCompletedOrRejectedBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/accepted-or-pending/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getAcceptedOrPendingBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getAcceptedOrPendingBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get list of completed or rejected bookings for customer by email
    @GetMapping("/accepted-or-pending/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getAcceptedOrPendingBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getAcceptedOrPendingBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }



    @PutMapping("/{vehicleNumber}/status")
    public ResponseEntity<String> updateVehicleStatus(@PathVariable String vehicleNumber, @RequestParam VehicleStatus status) {
        bookingService.updateVehicleStatus(vehicleNumber, status);
        return ResponseEntity.ok("Vehicle status updated successfully");
    }

    @PostMapping("/addVehicle")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle) {
        try {
            // Attempt to save the vehicle
            System.out.println(vehicle.getTransporter().getEmail());
            Vehicle savedVehicle = bookingService.saveVehicle(vehicle);
            return ResponseEntity.ok(savedVehicle);
        } catch (VehicleSaveException e) {
            // Log the error and return a meaningful error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            // Catch any unexpected errors and return a generic error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while adding the vehicle.");
        }
    }


    // Get rejected bookings for transporter by email
    @GetMapping("/rejected/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getRejectedBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getRejectedBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get rejected bookings for customer by email
    @GetMapping("/rejected/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getRejectedBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getRejectedBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get pending bookings for transporter by email
    @GetMapping("/pending/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getPendingBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getPendingBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get pending bookings for customer by email
    @GetMapping("/pending/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getPendingBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getPendingBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get accepted bookings for transporter by email
    @GetMapping("/accepted/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getAcceptedBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getAcceptedBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get accepted bookings for customer by email
    @GetMapping("/accepted/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getAcceptedBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getAcceptedBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<VehicleWithTransporterDTO> searchVehicles(
            @RequestParam("keyword") String keyword,
            @RequestParam("distance") double distance) {

        return bookingService.searchVehicles(keyword, distance);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<VehicleWithTransporterDTO>> filterVehicles(
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) Double basePrice,
            @RequestParam(required = false) Double pricePerKilometer,
            @RequestParam(required = false) String sortField1,
            @RequestParam(required = false) String sortDirection1,
            @RequestParam(required = false) String sortField2,
            @RequestParam(required = false) String sortDirection2,
            @RequestParam(required = false) String sortField3,
            @RequestParam(required = false) String sortDirection3,
            @RequestParam double distance) {

        try {
            List<VehicleWithTransporterDTO> filteredVehicles = bookingService.filterVehicles(
                    vehicleType,
                    basePrice,
                    pricePerKilometer,
                    sortField1,
                    sortDirection1,
                    sortField2,
                    sortDirection2,
                    sortField3,
                    sortDirection3,
                    distance
            );

            return ResponseEntity.ok(filteredVehicles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/vehicle-info/{email}")
    public ResponseEntity<VehicleStatusDTO> getVehicleInfoByEmail(@PathVariable String email) {
        VehicleStatusDTO vehicleStatus = bookingService.getVehicleStatusByEmail(email);
        return ResponseEntity.ok(vehicleStatus);
    }


    // Get pending bookings for transporter by email
    @GetMapping("/payment/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getPaymentBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getPaymentBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get pending bookings for customer by email
    @GetMapping("/payment/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getPaymentBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingService.getPaymentBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PutMapping("/{vehicleNumber}/updatePricing")
    public ResponseEntity<Vehicle> updateVehiclePricing(
            @PathVariable String vehicleNumber,
            @RequestParam double basePrice,
            @RequestParam double pricePerKilometer) {
        Vehicle updatedVehicle = bookingService.updateVehiclePricing(vehicleNumber, basePrice, pricePerKilometer);
        return ResponseEntity.ok(updatedVehicle);
    }

    @GetMapping("/{transporterEmail}/earnings")
    public ResponseEntity<List<TransporterEarningsDTO>> getEarnings(@PathVariable String transporterEmail) {
        List<TransporterEarningsDTO> earnings = bookingService.getEarningsByTransporter(transporterEmail);
        return ResponseEntity.ok(earnings);
    }

    @GetMapping("/{vehicleNumber}")
    public ResponseEntity<?> getVehicle(@PathVariable String vehicleNumber) {
        try {
            Vehicle vehicle = bookingService.getVehicleByNumber(vehicleNumber);
            return ResponseEntity.ok(vehicle);
        } catch (VehicleNotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + ex.getMessage());
        }
    }


}


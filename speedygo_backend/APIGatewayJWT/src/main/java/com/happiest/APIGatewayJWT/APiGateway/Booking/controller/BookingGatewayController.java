package com.happiest.APIGatewayJWT.APiGateway.Booking.controller;

import com.happiest.APIGatewayJWT.APiGateway.Booking.dto.*;
import com.happiest.APIGatewayJWT.APiGateway.Booking.enums.VehicleStatus;
import com.happiest.APIGatewayJWT.APiGateway.Booking.interfaces.BookingFeignClient;
import com.happiest.APIGatewayJWT.APiGateway.Booking.model.Booking;
import com.happiest.APIGatewayJWT.APiGateway.Booking.model.Vehicle;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingGatewayController {

    @Autowired
    private BookingFeignClient bookingFeignClient;

    @PostMapping("/request-ride")
    public ResponseEntity<BookingDTO> requestRide(@RequestBody RideRequest rideRequest) {
        return bookingFeignClient.requestRide(rideRequest);
    }

    @PutMapping("/update-ride-status/{id}")
    public ResponseEntity<Booking> updateRideStatus(
            @PathVariable Long id,
            @RequestBody UpdateRideStatusRequest updateRideStatusRequest) {
        return bookingFeignClient.updateRideStatus(id, updateRideStatusRequest);
    }




    @GetMapping("/transporter/{transporterEmail}/pending-requests")
    public ResponseEntity<List<Booking>> getPendingRequests(@PathVariable String transporterEmail) {
        return bookingFeignClient.getPendingRequests(transporterEmail);
    }

    @GetMapping("/customer/{customerEmail}/requests")
    public ResponseEntity<List<Booking>> getCustomerRequests(@PathVariable String customerEmail) {
        return bookingFeignClient.getCustomerRequests(customerEmail);
    }

    @GetMapping("/available")
    public ResponseEntity<List<VehicleWithTransporterDTO>> getCombinedAvailableVehicles(@RequestParam("distance") double distance) {
        return bookingFeignClient.getCombinedAvailableVehicles(distance);
    }



    @PutMapping("/{vehicleNumber}/status")
    public ResponseEntity<String> updateVehicleStatus(@PathVariable String vehicleNumber, @RequestParam VehicleStatus status) {
        return bookingFeignClient.updateVehicleStatus(vehicleNumber, status);
    }

    @PostMapping("/addVehicle")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle) {
        System.out.println(vehicle.getTransporter().getEmail());
        return bookingFeignClient.addVehicle(vehicle);
    }



    @GetMapping("/rejected/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getRejectedBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingFeignClient.getRejectedBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get rejected bookings for customer by email
    @GetMapping("/rejected/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getRejectedBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingFeignClient.getRejectedBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get pending bookings for transporter by email
    @GetMapping("/pending/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getPendingBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingFeignClient.getPendingBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get pending bookings for customer by email
    @GetMapping("/pending/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getPendingBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingFeignClient.getPendingBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get accepted bookings for transporter by email
    @GetMapping("/accepted/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getAcceptedBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingFeignClient.getAcceptedBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get accepted bookings for customer by email
    @GetMapping("/accepted/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getAcceptedBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingFeignClient.getAcceptedBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get list of completed or rejected bookings for customer by email
    @GetMapping("/completed-or-rejected/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getCompletedOrRejectedBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingFeignClient.getCompletedOrRejectedBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/completed-or-rejected/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getCompletedOrRejectedBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingFeignClient.getCompletedOrRejectedBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get list of accepted or pending bookings for transporter by email
    @GetMapping("/accepted-or-pending/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getAcceptedOrPendingBookingsByTransporterEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingFeignClient.getAcceptedOrPendingBookingsByTransporterEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get list of accepted or pending bookings for customer by email
    @GetMapping("/accepted-or-pending/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getAcceptedOrPendingBookingsByCustomerEmail(@PathVariable String email) {
        List<BookingDTO> bookings = bookingFeignClient.getAcceptedOrPendingBookingsByCustomerEmail(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<VehicleWithTransporterDTO> searchVehicles(
            @RequestParam("keyword") String keyword,
            @RequestParam("distance") double distance) {

        // Forward request to the booking service using Feign client
        return bookingFeignClient.searchVehicles(keyword, distance);
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
            List<VehicleWithTransporterDTO> filteredVehicles = bookingFeignClient.filterVehicles(
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
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/vehicle-info/{email}")
    public ResponseEntity<  VehicleStatusDTO> getVehicleInfoByEmail(@PathVariable("email") String email) {
        return bookingFeignClient.getVehicleStatusByEmail(email);
    }

    @GetMapping("/api/payment/transporter/{email}")
    public ResponseEntity<List<BookingDTO>> getPaymentBookingsByTransporterEmail(@PathVariable String email) {
        return bookingFeignClient.getPaymentBookingsByTransporterEmail(email);
    }

    // Get pending bookings for customer by email (through API Gateway)
    @GetMapping("/api/payment/customer/{email}")
    public ResponseEntity<List<BookingDTO>> getPaymentBookingsByCustomerEmail(@PathVariable String email) {
        return bookingFeignClient.getPaymentBookingsByCustomerEmail(email);
    }

    @PutMapping("/{vehicleNumber}/updatePricing")
    public ResponseEntity<Vehicle> updateVehiclePricing(
            @PathVariable String vehicleNumber,
            @RequestParam double basePrice,
            @RequestParam double pricePerKilometer) {
        return bookingFeignClient.updateVehiclePricing(vehicleNumber, basePrice, pricePerKilometer);
    }

    @GetMapping("/{transporterEmail}/earnings")
    public ResponseEntity<List<TransporterEarningsDTO>> getEarnings(@PathVariable String transporterEmail) {
        return bookingFeignClient.getEarnings(transporterEmail);
    }

    @GetMapping("/{vehicleNumber}")
    public ResponseEntity<?> getVehicle(@PathVariable String vehicleNumber) {
        return bookingFeignClient.getVehicle(vehicleNumber);
    }





}

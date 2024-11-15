package com.happiest.APIGatewayJWT.APiGateway.Booking.interfaces;

import com.happiest.APIGatewayJWT.APiGateway.Booking.dto.*;
import com.happiest.APIGatewayJWT.APiGateway.Booking.enums.VehicleStatus;
import com.happiest.APIGatewayJWT.APiGateway.Booking.model.Booking;
import com.happiest.APIGatewayJWT.APiGateway.Booking.model.Vehicle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "http://BookingService/api/bookings")
public interface BookingFeignClient {

    @PostMapping("/request-ride")
    ResponseEntity<BookingDTO> requestRide(@RequestBody RideRequest rideRequest);

    @PutMapping("/update-ride-status/{id}")
    ResponseEntity<Booking> updateRideStatus(
            @PathVariable("id") Long id,
            @RequestBody UpdateRideStatusRequest updateRideStatusRequest);



    @GetMapping("/transporter/{transporterEmail}/pending-requests")
    ResponseEntity<List<Booking>> getPendingRequests(@PathVariable("transporterEmail") String transporterEmail);

    @GetMapping("/customer/{customerEmail}/requests")
    ResponseEntity<List<Booking>> getCustomerRequests(@PathVariable("customerEmail") String customerEmail);

    @GetMapping("/available")
    ResponseEntity<List<VehicleWithTransporterDTO>> getCombinedAvailableVehicles(@RequestParam("distance") double distance);



    @PutMapping("/{vehicleNumber}/status")
    ResponseEntity<String> updateVehicleStatus(@PathVariable("vehicleNumber") String vehicleNumber, @RequestParam VehicleStatus status);

    @PostMapping("/addVehicle")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle);


    @GetMapping("/rejected/transporter/{email}")
    List<BookingDTO> getRejectedBookingsByTransporterEmail(@PathVariable("email") String email);

    @GetMapping("/rejected/customer/{email}")
    List<BookingDTO> getRejectedBookingsByCustomerEmail(@PathVariable("email") String email);

    @GetMapping("/pending/transporter/{email}")
    List<BookingDTO> getPendingBookingsByTransporterEmail(@PathVariable("email") String email);

    @GetMapping("/pending/customer/{email}")
    List<BookingDTO> getPendingBookingsByCustomerEmail(@PathVariable("email") String email);

    @GetMapping("/accepted/transporter/{email}")
    List<BookingDTO> getAcceptedBookingsByTransporterEmail(@PathVariable("email") String email);

    @GetMapping("/accepted/customer/{email}")
    List<BookingDTO> getAcceptedBookingsByCustomerEmail(@PathVariable("email") String email);

    @GetMapping("/completed-or-rejected/customer/{email}")
    List<BookingDTO> getCompletedOrRejectedBookingsByCustomerEmail(@PathVariable("email") String email);

    @GetMapping("/completed-or-rejected/transporter/{email}")
    List<BookingDTO> getCompletedOrRejectedBookingsByTransporterEmail(@PathVariable("email") String email);

    @GetMapping("/accepted-or-pending/transporter/{email}")
    List<BookingDTO> getAcceptedOrPendingBookingsByTransporterEmail(@PathVariable("email") String email);

    @GetMapping("/accepted-or-pending/customer/{email}")
    List<BookingDTO> getAcceptedOrPendingBookingsByCustomerEmail(@PathVariable("email") String email);



    @GetMapping("/search")
    public List<VehicleWithTransporterDTO> searchVehicles(
            @RequestParam("keyword") String keyword,
            @RequestParam("distance") double distance);

    @GetMapping("/filter")
    List<VehicleWithTransporterDTO> filterVehicles(
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) Double basePrice,
            @RequestParam(required = false) Double pricePerKilometer,
            @RequestParam(required = false) String sortField1,
            @RequestParam(required = false) String sortDirection1,
            @RequestParam(required = false) String sortField2,
            @RequestParam(required = false) String sortDirection2,
            @RequestParam(required = false) String sortField3,
            @RequestParam(required = false) String sortDirection3,
            @RequestParam double distance
    );

    @GetMapping("/vehicle-info/{email}")
    ResponseEntity<VehicleStatusDTO> getVehicleStatusByEmail(@PathVariable("email") String email);


    @GetMapping("/payment/transporter/{email}")
    ResponseEntity<List<BookingDTO>> getPaymentBookingsByTransporterEmail(@PathVariable("email") String email);

    // Get pending bookings for customer by email
    @GetMapping("/payment/customer/{email}")
    ResponseEntity<List<BookingDTO>> getPaymentBookingsByCustomerEmail(@PathVariable("email") String email);

    @PutMapping("/{vehicleNumber}/updatePricing")
    ResponseEntity<Vehicle> updateVehiclePricing(
            @PathVariable String vehicleNumber,
            @RequestParam double basePrice,
            @RequestParam double pricePerKilometer);

    @GetMapping("/{transporterEmail}/earnings")
    ResponseEntity<List<TransporterEarningsDTO>> getEarnings(@PathVariable String transporterEmail);

    @GetMapping("/{vehicleNumber}")
    ResponseEntity<?> getVehicle(@PathVariable String vehicleNumber);

}

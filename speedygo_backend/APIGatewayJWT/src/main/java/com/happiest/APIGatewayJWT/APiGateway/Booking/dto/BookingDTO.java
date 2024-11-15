package com.happiest.APIGatewayJWT.APiGateway.Booking.dto;


import com.happiest.APIGatewayJWT.APiGateway.Booking.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long requestId;      // Booking ID
    private String source;       // Source location
    private String destination;  // Destination location
    private double distance;     // Distance in kilometers
    private double totalPrice;   // Total price for the ride

    private CustomerInfoDTO customerInfo;        // Customer details
    private TransporterInfoDTO transporterInfo;  // Transporter details
    private VehicleInfoDTO vehicleInfo;          // Vehicle details

    private RideStatus rideStatus;   // Current status of the ride
    private LocalDate rideDate;      // Date of the ride
    private LocalTime rideTime;      // Time of the ride
}

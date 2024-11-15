package com.happiest.APIGatewayJWT.APiGateway.Booking.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    private String customerEmail;
    private String transporterEmail;
    private String vehicleNumber;
    private String source;
    private String destination;
    private double distance;

    private LocalDate rideDate; // New field for date
    private LocalTime rideTime; // New field for time
}

package com.happiest.BookingService.dto;

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
    private LocalDate rideDate;
    private LocalTime rideTime;
}

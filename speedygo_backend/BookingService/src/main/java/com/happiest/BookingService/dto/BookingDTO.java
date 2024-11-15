package com.happiest.BookingService.dto;

import com.happiest.BookingService.model.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long requestId;
    private String source;
    private String destination;
    private double distance;
    private double totalPrice;

    private CustomerInfoDTO customerInfo;
    private TransporterInfoDTO transporterInfo;
    private VehicleInfoDTO vehicleInfo;

    private RideStatus rideStatus;
    private LocalDate rideDate;
    private LocalTime rideTime;
}

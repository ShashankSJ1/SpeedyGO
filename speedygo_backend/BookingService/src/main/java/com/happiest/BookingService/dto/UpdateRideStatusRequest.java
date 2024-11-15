package com.happiest.BookingService.dto;

import com.happiest.BookingService.model.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRideStatusRequest {
    private RideStatus status;
}
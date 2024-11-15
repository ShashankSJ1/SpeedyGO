package com.happiest.APIGatewayJWT.APiGateway.Booking.dto;


import com.happiest.APIGatewayJWT.APiGateway.Booking.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRideStatusRequest {
    private RideStatus status;
}
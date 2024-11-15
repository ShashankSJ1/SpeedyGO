package com.happiest.APIGatewayJWT.APiGateway.Booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporterEarningsDTO {
    private LocalDateTime dateTime;
    private Double totalEarnings;
}


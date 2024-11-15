package com.happiest.APIGatewayJWT.APiGateway.Booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDTO {
    private String email;      // Customer's email
    private String name;       // Customer's name
    private Long phoneNumber; // Customer's phone number
}


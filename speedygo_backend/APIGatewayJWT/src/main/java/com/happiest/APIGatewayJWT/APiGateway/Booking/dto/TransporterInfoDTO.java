package com.happiest.APIGatewayJWT.APiGateway.Booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporterInfoDTO {
    private String email;      // Transporter's email
    private String username;   // Transporter's username
    private Long phoneNumber; // Transporter's phone number

}


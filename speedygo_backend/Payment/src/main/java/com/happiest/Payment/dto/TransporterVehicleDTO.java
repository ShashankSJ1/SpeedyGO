package com.happiest.Payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporterVehicleDTO {
    private String paymentId;
    private Integer bookingId;  // Assuming bookingId is an Integer
    private String transporterEmail;
    private String customerEmail;
    private String vehicleNumber;
    private Double totalPrice; // Include totalPrice if needed
    private String paymentStatus; // Include paymentStatus if needed
}


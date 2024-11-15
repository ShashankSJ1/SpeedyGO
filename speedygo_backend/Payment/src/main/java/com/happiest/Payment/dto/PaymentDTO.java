package com.happiest.Payment.dto;

import com.happiest.Payment.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String paymentId;
    private Long bookingId;  // Booking request ID
    private double totalPrice;
    private String customerEmail;  // Email for customer
    private String transporterEmail;  // Email for transporter
    private PaymentStatus paymentStatus;
}

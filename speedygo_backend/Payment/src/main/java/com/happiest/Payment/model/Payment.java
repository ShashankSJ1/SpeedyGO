package com.happiest.Payment.model;

import com.happiest.Payment.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    private String paymentId; // Primary Key

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "requestId")
    private Booking booking; // One-to-One relationship with Booking

    private double totalPrice; // Total Price for the ride

    @ManyToOne
    @JoinColumn(name = "customer_email", referencedColumnName = "email")
    private Users customer; // Customer email (role -> user)

    @ManyToOne
    @JoinColumn(name = "transporter_email", referencedColumnName = "email")
    private Users transporter; // Transporter email (role -> driver)


    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // Payment status (SUCCESS/FAILED)
}

package com.happiest.Payment.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.happiest.Payment.model.enums.RideStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Booking")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId; // Primary Key

    private String source;
    private String destination;
    private double distance;
    private double totalPrice;



    @ManyToOne
    @JoinColumn(name = "customer_email") // Customer email (foreign key)

    private Users customer;

    @ManyToOne
    @JoinColumn(name = "transporter_email") // Transporter email (foreign key)
    private Users transporter;

    @ManyToOne
    @JoinColumn(name = "vehicle_number") // Vehicle foreign key
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus; // PENDING, ACCEPT, REJECT, COMPLETED

    private LocalDate rideDate; // New field for ride date
    private LocalTime rideTime; // New field for ride time


}

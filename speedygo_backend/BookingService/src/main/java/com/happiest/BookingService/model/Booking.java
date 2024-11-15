package com.happiest.BookingService.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.happiest.BookingService.dto.CustomerInfoDTO;
import com.happiest.BookingService.dto.TransporterInfoDTO;
import com.happiest.BookingService.model.enums.RideStatus;
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
    private Long requestId;

    private String source;
    private String destination;
    private double distance;
    private double totalPrice;



    @ManyToOne
    @JoinColumn(name = "customer_email")
    @JsonManagedReference
    private Users customer;

    @ManyToOne
    @JoinColumn(name = "transporter_email")
    @JsonManagedReference
    private Users transporter;

    @ManyToOne
    @JoinColumn(name = "vehicle_number")
    @JsonManagedReference
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private LocalDate rideDate;
    private LocalTime rideTime;
}


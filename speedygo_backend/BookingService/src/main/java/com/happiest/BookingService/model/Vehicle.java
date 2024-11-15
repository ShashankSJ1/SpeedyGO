package com.happiest.BookingService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.happiest.BookingService.model.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Vehicle")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    private String vehicleNumber;

    private String vehicleName;
    private String vehicleType;
    private double basePrice;
    private double pricePerKilometer;

    @OneToOne
    @JoinColumn(name = "transporter_email", referencedColumnName = "email")
    @JsonBackReference
    private Users transporter;


    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
}

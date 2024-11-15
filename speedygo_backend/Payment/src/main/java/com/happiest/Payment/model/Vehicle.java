package com.happiest.Payment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.happiest.Payment.model.enums.VehicleStatus;
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
    private String vehicleNumber; // Primary Key

    private String vehicleName;
    private String vehicleType;
    private double basePrice;
    private double pricePerKilometer;

    @OneToOne
    @JoinColumn(name = "transporter_email", referencedColumnName = "email")
    private Users transporter;


    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
}

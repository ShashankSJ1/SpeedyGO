package com.happiest.BookingService.dto;

import com.happiest.BookingService.model.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleInfoDTO {
    public VehicleInfoDTO(String vehicleName, VehicleStatus status, double basePrice, double pricePerKilometer, String vehicleType, String vehicleNumber) {
        this.vehicleName = vehicleName;
        this.status = status;
        this.basePrice = basePrice;
        this.pricePerKilometer = pricePerKilometer;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
    }

    private  String vehicleName;
    private String vehicleNumber;
    private String vehicleType;
    private double pricePerKilometer;
    private double basePrice;
    private VehicleStatus status;
    private double totalPrice;



}

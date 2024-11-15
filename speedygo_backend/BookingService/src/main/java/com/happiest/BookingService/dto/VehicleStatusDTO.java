package com.happiest.BookingService.dto;

// VehicleStatusDTO.java

import com.happiest.BookingService.model.enums.VehicleStatus;

public class VehicleStatusDTO {
    private String vehicleNumber;
    private VehicleStatus status;

    // Constructor, getters, and setters
    public VehicleStatusDTO(String vehicleNumber, VehicleStatus status) {
        this.vehicleNumber = vehicleNumber;
        this.status = status;
    }

    // Getters and setters
    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }
}
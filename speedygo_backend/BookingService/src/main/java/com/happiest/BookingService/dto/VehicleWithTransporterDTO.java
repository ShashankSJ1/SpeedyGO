package com.happiest.BookingService.dto;

public class VehicleWithTransporterDTO {
    private VehicleInfoDTO vehicleInfo;
    private TransporterInfoDTO transporterInfo;

    public VehicleWithTransporterDTO(VehicleInfoDTO vehicleInfo, TransporterInfoDTO transporterInfo) {
        this.vehicleInfo = vehicleInfo;
        this.transporterInfo = transporterInfo;
    }
    public VehicleInfoDTO getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(VehicleInfoDTO vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public TransporterInfoDTO getTransporterInfo() {
        return transporterInfo;
    }

    public void setTransporterInfo(TransporterInfoDTO transporterInfo) {
        this.transporterInfo = transporterInfo;
    }
}

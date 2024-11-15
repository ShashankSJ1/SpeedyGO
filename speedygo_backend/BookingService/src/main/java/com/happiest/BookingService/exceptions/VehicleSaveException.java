package com.happiest.BookingService.exceptions;

public class VehicleSaveException extends RuntimeException {
    public VehicleSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public VehicleSaveException(String message) {
        super(message); // Constructor for cases without a cause
    }
}

package com.happiest.BookingService.exceptions;

public class RideNotFound extends RuntimeException {
    public RideNotFound(String message) {
        super(message);
    }
}

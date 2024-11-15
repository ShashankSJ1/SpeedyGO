package com.happiest.Payment.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Receipt {
    private String paymentId;
    private double totalPrice;
    private String customerEmail;
    private String transporterEmail;

    // Default constructor
    public Receipt() {
    }

    // Constructor with parameters
    @JsonCreator
    public Receipt(
            @JsonProperty("paymentId") String paymentId,
            @JsonProperty("totalPrice") double totalPrice,
            @JsonProperty("customerEmail") String customerEmail,
            @JsonProperty("transporterEmail") String transporterEmail) {
        this.paymentId = paymentId;
        this.totalPrice = totalPrice;
        this.customerEmail = customerEmail;
        this.transporterEmail = transporterEmail;
    }

    // Getters
    public String getPaymentId() {
        return paymentId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getTransporterEmail() {
        return transporterEmail;
    }

    // Method to convert Receipt to JSON
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting receipt to JSON: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("Payment ID: %s, Total Price: %.2f, Customer Email: %s, Transporter Email: %s",
                paymentId, totalPrice, customerEmail, transporterEmail);
    }
}

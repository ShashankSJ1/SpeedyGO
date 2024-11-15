package com.happiest.APIGatewayJWT.APiGateway.Payment.Repository;


import com.happiest.APIGatewayJWT.APiGateway.Payment.dto.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "http://Payment/payments")
public interface PaymentServiceFeignClient {

    // Save a payment
    @PostMapping("/save")
    ResponseEntity<PaymentDTO> savePayment(@RequestBody PaymentDTO paymentDTO);

    // Get payments by customer email
    @GetMapping("/customer/{email}")
    ResponseEntity<List<PaymentDTO>> getPaymentsByCustomerEmail(@PathVariable("email") String email);

    // Get payments by transporter email
    @GetMapping("/transporter/{email}")
    ResponseEntity<List<PaymentDTO>> getPaymentsByTransporterEmail(@PathVariable("email") String email);
}

package com.happiest.APIGatewayJWT.APiGateway.Payment.controller;


import com.happiest.APIGatewayJWT.APiGateway.Payment.Repository.PaymentServiceFeignClient;
import com.happiest.APIGatewayJWT.APiGateway.Payment.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentApiGatewayController {

    @Autowired
    private PaymentServiceFeignClient paymentServiceFeignClient;

    // Save a payment (via API Gateway)
    @PostMapping("/save")
    public ResponseEntity<PaymentDTO> savePayment(@RequestBody PaymentDTO paymentDTO) {
        return paymentServiceFeignClient.savePayment(paymentDTO);
    }

    // Get payments by customer email (via API Gateway)
    @GetMapping("/customer/{email}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByCustomerEmail(@PathVariable String email) {
        return paymentServiceFeignClient.getPaymentsByCustomerEmail(email);
    }

    // Get payments by transporter email (via API Gateway)
    @GetMapping("/transporter/{email}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByTransporterEmail(@PathVariable String email) {
        return paymentServiceFeignClient.getPaymentsByTransporterEmail(email);
    }
}

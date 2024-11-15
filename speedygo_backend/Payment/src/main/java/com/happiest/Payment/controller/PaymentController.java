package com.happiest.Payment.controller;

import com.happiest.Payment.constants.PredefinedConstant;
import com.happiest.Payment.dto.PaymentDTO;

import com.happiest.Payment.model.Payment;
import com.happiest.Payment.service.PaymentService;
import com.happiest.Payment.utility.Rbundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private static final Logger LOGGER = LogManager.getLogger(PaymentController.class);

    // Save a payment
    @PostMapping("/save")
    public ResponseEntity<PaymentDTO> savePayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            LOGGER.info(Rbundle.getKey(PredefinedConstant.PAYMENT_SAVE_ATTEMPT) + paymentDTO.getBookingId());
            PaymentDTO savedPaymentDTO = paymentService.savePayment(paymentDTO);
            LOGGER.info(Rbundle.getKey(PredefinedConstant.PAYMENT_SAVE_SUCCESS) + savedPaymentDTO.getBookingId());
            return ResponseEntity.ok(savedPaymentDTO);
        } catch (RuntimeException e) {
            LOGGER.error(Rbundle.getKey(PredefinedConstant.PAYMENT_GENERIC_ERROR) + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    // Get payments by customer email
    @GetMapping("/customer/{email}")
    public ResponseEntity<?> getPaymentsByCustomerEmail(@PathVariable String email) {
        try {
            LOGGER.info(Rbundle.getKey(PredefinedConstant.PAYMENT_FETCH_CUSTOMER) + email);
            List<Payment> payments = paymentService.getPaymentsByCustomerEmail(email);

            // Convert Payment entities to PaymentDTOs
            List<PaymentDTO> paymentDTOs = payments.stream()
                    .map(payment -> new PaymentDTO(
                            payment.getPaymentId(),
                            payment.getBooking().getRequestId(),
                            payment.getTotalPrice(),
                            payment.getCustomer().getEmail(),
                            payment.getTransporter().getEmail(),
                            payment.getPaymentStatus()
                    ))
                    .collect(Collectors.toList());



            return ResponseEntity.ok(paymentDTOs);
        } catch (Exception e) {
            LOGGER.error(Rbundle.getKey(PredefinedConstant.PAYMENT_FETCH_ERROR_CUSTOMER) + email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Get payments by transporter email
    @GetMapping("/transporter/{email}")
    public ResponseEntity<?> getPaymentsByTransporterEmail(@PathVariable String email) {
        try {
            LOGGER.info(Rbundle.getKey(PredefinedConstant.PAYMENT_FETCH_TRANSPORTER) + email);
            List<Payment> payments = paymentService.getPaymentsByTransporterEmail(email);

            // Convert Payment entities to PaymentDTOs
            List<PaymentDTO> paymentDTOs = payments.stream()
                    .map(payment -> new PaymentDTO(
                            payment.getPaymentId(),
                            payment.getBooking().getRequestId(),
                            payment.getTotalPrice(),
                            payment.getCustomer().getEmail(),
                            payment.getTransporter().getEmail(),
                            payment.getPaymentStatus()
                    ))
                    .collect(Collectors.toList());


            return ResponseEntity.ok(paymentDTOs);
        } catch (Exception e) {
            LOGGER.error(Rbundle.getKey(PredefinedConstant.PAYMENT_FETCH_ERROR_TRANSPORTER) + email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

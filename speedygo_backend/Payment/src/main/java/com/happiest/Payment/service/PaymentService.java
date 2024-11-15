package com.happiest.Payment.service;

import com.happiest.Payment.constants.PredefinedConstant;
import com.happiest.Payment.utility.Rbundle;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.happiest.Payment.dto.PaymentDTO;
import com.happiest.Payment.model.Booking;
import com.happiest.Payment.model.Payment;
import com.happiest.Payment.model.Receipt;
import com.happiest.Payment.model.Users;
import com.happiest.Payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; // Kafka template for sending messages


    // Logger for logging payment operations
    private static final Logger LOGGER = LogManager.getLogger(PaymentService.class);

    @Transactional
    public PaymentDTO savePayment(PaymentDTO paymentDTO) {
        try {
            LOGGER.info(Rbundle.getKey(PredefinedConstant.PAYMENT_SAVE_ATTEMPT) + paymentDTO.getBookingId());

            // Create Payment entity directly from PaymentDTO
            Payment payment = new Payment();
            payment.setPaymentId(paymentDTO.getPaymentId());
            payment.setTotalPrice(paymentDTO.getTotalPrice());
            payment.setPaymentStatus(paymentDTO.getPaymentStatus());

            // Create Booking object and set it to payment
            Booking booking = new Booking();
            booking.setRequestId(paymentDTO.getBookingId());
            payment.setBooking(booking);

            // Create Users objects for customer and transporter
            Users customer = new Users();
            customer.setEmail(paymentDTO.getCustomerEmail());
            payment.setCustomer(customer);

            Users transporter = new Users();
            transporter.setEmail(paymentDTO.getTransporterEmail());
            payment.setTransporter(transporter);

            // Save the payment entity to the repository
            paymentRepository.save(payment);
            LOGGER.info(Rbundle.getKey(PredefinedConstant.PAYMENT_SAVE_SUCCESS) + paymentDTO.getBookingId());

            // Prepare the receipt information to send to Kafka
            Receipt receipt = new Receipt(paymentDTO.getPaymentId(), paymentDTO.getTotalPrice(),
                    paymentDTO.getCustomerEmail(), paymentDTO.getTransporterEmail());

            // Send receipt information to Kafka topic
            kafkaTemplate.send("payment-completed", receipt.toJson());
            LOGGER.info(Rbundle.getKey(PredefinedConstant.PAYMENT_KAFKA_RECEIPT_SENT) + paymentDTO.getPaymentId());

            // Return the PaymentDTO as a response
            return paymentDTO; // Return the original DTO as it is
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(Rbundle.getKey(PredefinedConstant.PAYMENT_DATA_INTEGRITY_VIOLATION) + e.getMessage());
            throw new RuntimeException(Rbundle.getKey(PredefinedConstant.PAYMENT_DATA_INTEGRITY_VIOLATION) + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error(Rbundle.getKey(PredefinedConstant.PAYMENT_INVALID_INPUT) + e.getMessage());
            throw new RuntimeException(Rbundle.getKey(PredefinedConstant.PAYMENT_INVALID_INPUT) + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(Rbundle.getKey(PredefinedConstant.PAYMENT_GENERIC_ERROR) + e.getMessage(), e);
            throw new RuntimeException(Rbundle.getKey(PredefinedConstant.PAYMENT_GENERIC_ERROR) + e.getMessage());
        }
    }

    public List<Payment> getPaymentsByCustomerEmail(String customerEmail) {
        try {
            LOGGER.info(Rbundle.getKey(PredefinedConstant.PAYMENT_FETCH_CUSTOMER) + customerEmail);
            return paymentRepository.findByCustomerEmail(customerEmail);
        } catch (Exception e) {
            LOGGER.error(Rbundle.getKey(PredefinedConstant.PAYMENT_FETCH_ERROR_CUSTOMER) + customerEmail + ". " + e.getMessage());
            throw new RuntimeException(Rbundle.getKey(PredefinedConstant.PAYMENT_FETCH_ERROR_CUSTOMER) + customerEmail + ". " + e.getMessage());
        }
    }

    public List<Payment> getPaymentsByTransporterEmail(String transporterEmail) {
        try {
            LOGGER.info(Rbundle.getKey(PredefinedConstant.PAYMENT_FETCH_TRANSPORTER) + transporterEmail);
            return paymentRepository.findByTransporterEmail(transporterEmail);
        } catch (Exception e) {
            LOGGER.error(Rbundle.getKey(PredefinedConstant.PAYMENT_FETCH_ERROR_TRANSPORTER) + transporterEmail + ". " + e.getMessage());
            throw new RuntimeException(Rbundle.getKey(PredefinedConstant.PAYMENT_FETCH_ERROR_TRANSPORTER) + transporterEmail + ". " + e.getMessage());
        }
    }
}
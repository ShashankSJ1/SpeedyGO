package com.happiest.Payment;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import com.happiest.Payment.dto.PaymentDTO;
import com.happiest.Payment.model.Payment;
import com.happiest.Payment.model.Users;
import com.happiest.Payment.model.enums.PaymentStatus;
import com.happiest.Payment.repository.PaymentRepository;
import com.happiest.Payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private PaymentService paymentService;

    private PaymentDTO paymentDTO;
    private Payment payment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Create a sample PaymentDTO
        paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId("P001");
        paymentDTO.setTotalPrice(100.00);
        paymentDTO.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentDTO.setBookingId(1L);  // Long bookingId
        paymentDTO.setCustomerEmail("customer@example.com");
        paymentDTO.setTransporterEmail("transporter@example.com");

        // Create a sample Payment entity
        payment = new Payment();
        payment.setPaymentId(paymentDTO.getPaymentId());
        payment.setTotalPrice(paymentDTO.getTotalPrice());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setCustomer(new Users());
        payment.getCustomer().setEmail(paymentDTO.getCustomerEmail());

        payment.setTransporter(new Users());
        payment.getTransporter().setEmail(paymentDTO.getTransporterEmail());

    }

    // Test for savePayment method
    @Test
    public void testSavePayment_Success() throws Exception {
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentDTO result = paymentService.savePayment(paymentDTO);

        assertNotNull(result);
        assertEquals("P001", result.getPaymentId());
        assertEquals(100.00, result.getTotalPrice());
        assertEquals(1L, result.getBookingId());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(kafkaTemplate, times(1)).send(eq("payment-completed"), anyString());
    }

    @Test
    public void testSavePayment_DataIntegrityViolation() {
        when(paymentRepository.save(any(Payment.class))).thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.savePayment(paymentDTO);
        });

        assertTrue(exception.getMessage().contains("Data integrity violation"));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    public void testSavePayment_NullPaymentId() {
        // Setting the paymentId to null
        paymentDTO.setPaymentId(null);

        // Call the savePayment method and capture the result
        PaymentDTO result = paymentService.savePayment(paymentDTO);

        // Ensure that the returned PaymentDTO has a null paymentId (or whatever expected behavior is)
        assertNull(result.getPaymentId(), "Expected paymentId to be null");

        // Optionally verify if the repository's save method was called
        // (if using Mockito to mock PaymentRepository in your test)
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }



    // Test for getPaymentsByCustomerEmail method
    @Test
    public void testGetPaymentsByCustomerEmail_Success() {
        when(paymentRepository.findByCustomerEmail(anyString())).thenReturn(Arrays.asList(payment));

        List<Payment> result = paymentService.getPaymentsByCustomerEmail("customer@example.com");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("P001", result.get(0).getPaymentId());
        verify(paymentRepository, times(1)).findByCustomerEmail(anyString());
    }

    @Test
    public void testGetPaymentsByCustomerEmail_Error() {
        when(paymentRepository.findByCustomerEmail(anyString())).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentsByCustomerEmail("customer@example.com");
        });

        assertTrue(exception.getMessage().contains("Error occurred while fetching payments for customer email"));
    }

    // Test for getPaymentsByTransporterEmail method
    @Test
    public void testGetPaymentsByTransporterEmail_Success() {
        when(paymentRepository.findByTransporterEmail(anyString())).thenReturn(Arrays.asList(payment));

        List<Payment> result = paymentService.getPaymentsByTransporterEmail("transporter@example.com");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("P001", result.get(0).getPaymentId());
        verify(paymentRepository, times(1)).findByTransporterEmail(anyString());
    }

    @Test
    public void testGetPaymentsByTransporterEmail_Error() {
        when(paymentRepository.findByTransporterEmail(anyString())).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentsByTransporterEmail("transporter@example.com");
        });

        assertTrue(exception.getMessage().contains("Error occurred while fetching payments for transporter email"));
    }
}

package com.happiest.Payment;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.Payment.controller.PaymentController;
import com.happiest.Payment.dto.PaymentDTO;
import com.happiest.Payment.model.Booking;
import com.happiest.Payment.model.Payment;
import com.happiest.Payment.model.Users;
import com.happiest.Payment.model.enums.PaymentStatus;
import com.happiest.Payment.model.enums.Role;
import com.happiest.Payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(PaymentController.class) // This helps test only the web layer
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PaymentDTO paymentDTO;

    @BeforeEach
    public void setUp() {
        paymentDTO = new PaymentDTO("paymentId1", 101L, 500.00, "customer@example.com", "transporter@example.com", PaymentStatus.SUCCESS);
    }

    @Test
    public void testSavePayment_Success() throws Exception {
        when(paymentService.savePayment(paymentDTO)).thenReturn(paymentDTO);

        mockMvc.perform(post("/payments/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDTO)))
                .andExpect(status().isOk())  // Expecting HTTP 200 OK
                .andExpect(content().json(objectMapper.writeValueAsString(paymentDTO)));
    }

    @Test
    public void testSavePayment_Failure() throws Exception {
        when(paymentService.savePayment(paymentDTO)).thenThrow(new RuntimeException("Invalid input"));

        mockMvc.perform(post("/payments/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDTO)))
                .andExpect(status().isBadRequest());  // Expecting HTTP 400 Bad Request
    }

    @Test
    public void testGetPaymentsByCustomerEmail_Success() throws Exception {
        // Create Users for customer and transporter
        Users customer = new Users("customer@example.com", "customer1", "password1", 1234567890L, Role.CUSTOMER, null);
        Users transporter = new Users("transporter@example.com", "transporter1", "password2", 9876543210L, Role.TRANSPORTER, null);

        // Create Booking objects with proper initialization
        Booking booking1 = new Booking();
        booking1.setRequestId(101L);
        booking1.setCustomer(customer); // Setting the customer
        booking1.setTransporter(transporter); // Setting the transporter

        Booking booking2 = new Booking();
        booking2.setRequestId(102L);
        booking2.setCustomer(customer); // Setting the customer
        booking2.setTransporter(new Users("transporter2@example.com", "transporter2", "password3", 1234567891L, Role.TRANSPORTER, null)); // Setting the transporter for the second booking

        // Create Payment objects with initialized Booking and Users
        List<Payment> payments = Arrays.asList(
                new Payment("paymentId1", booking1, 500.00, customer, transporter, PaymentStatus.SUCCESS),
                new Payment("paymentId2", booking2, 1000.00, customer, new Users("transporter2@example.com", "transporter2", "password3", 1234567891L, Role.TRANSPORTER, null), PaymentStatus.FAILED)
        );

        // Create the expected PaymentDTOs
        List<PaymentDTO> paymentDTOs = Arrays.asList(
                new PaymentDTO("paymentId1", 101L, 500.00, "customer@example.com", "transporter@example.com", PaymentStatus.SUCCESS),
                new PaymentDTO("paymentId2", 102L, 1000.00, "customer@example.com", "transporter2@example.com", PaymentStatus.FAILED)
        );

        // Mock the service layer response
        when(paymentService.getPaymentsByCustomerEmail("customer@example.com")).thenReturn(payments);

        // Perform the GET request and validate the response
        mockMvc.perform(get("/payments/customer/customer@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(paymentDTOs)));
    }

    @Test
    public void testGetPaymentsByCustomerEmail_Failure() throws Exception {
        when(paymentService.getPaymentsByCustomerEmail("customer@example.com")).thenThrow(new RuntimeException("Error fetching data"));

        mockMvc.perform(get("/payments/customer/customer@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error fetching data"));
    }

    @Test
    public void testGetPaymentsByTransporterEmail_Success() throws Exception {
        // Create Users for customer and transporter
        Users customer1 = new Users("customer@example.com", "customer1", "password1", 1234567890L, Role.CUSTOMER, null);
        Users transporter = new Users("transporter@example.com", "transporter1", "password2", 9876543210L, Role.TRANSPORTER, null);
        Users customer2 = new Users("customer2@example.com", "customer2", "password3", 1234567891L, Role.CUSTOMER, null);

        // Create Booking objects with proper initialization
        Booking booking1 = new Booking();
        booking1.setRequestId(101L);
        booking1.setCustomer(customer1); // Setting the customer
        booking1.setTransporter(transporter); // Setting the transporter

        Booking booking2 = new Booking();
        booking2.setRequestId(102L);
        booking2.setCustomer(customer2); // Setting the customer
        booking2.setTransporter(transporter); // Setting the transporter

        // Create Payment objects with initialized Booking and Users
        List<Payment> payments = Arrays.asList(
                new Payment("paymentId1", booking1, 500.00, customer1, transporter, PaymentStatus.SUCCESS),
                new Payment("paymentId2", booking2, 1000.00, customer2, transporter, PaymentStatus.FAILED)
        );

        // Create the expected PaymentDTOs
        List<PaymentDTO> paymentDTOs = Arrays.asList(
                new PaymentDTO("paymentId1", 101L, 500.00, "customer@example.com", "transporter@example.com", PaymentStatus.SUCCESS),
                new PaymentDTO("paymentId2", 102L, 1000.00, "customer2@example.com", "transporter@example.com", PaymentStatus.FAILED)
        );

        // Mock the service layer response
        when(paymentService.getPaymentsByTransporterEmail("transporter@example.com")).thenReturn(payments);

        // Perform the GET request and validate the response
        mockMvc.perform(get("/payments/transporter/transporter@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(paymentDTOs)));
    }






    @Test
    public void testGetPaymentsByTransporterEmail_Failure() throws Exception {
        when(paymentService.getPaymentsByTransporterEmail("transporter@example.com")).thenThrow(new RuntimeException("Error fetching data"));

        mockMvc.perform(get("/payments/transporter/transporter@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error fetching data"));
    }
}


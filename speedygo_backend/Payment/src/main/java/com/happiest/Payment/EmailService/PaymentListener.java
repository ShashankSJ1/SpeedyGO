package com.happiest.Payment.EmailService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happiest.Payment.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Component
public class PaymentListener {

    @Autowired
    private JavaMailSender mailSender;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOGGER = LogManager.getLogger(PaymentListener.class);

    @KafkaListener(topics = "payment-completed", groupId = "email-service-group")
    public void listen(String message) {
        try {
            LOGGER.info("Received message from Kafka: {}"+ message);
            Receipt receipt = objectMapper.readValue(message, Receipt.class);
            sendEmail(receipt);
        } catch (Exception e) {
            LOGGER.error("Error processing Kafka message: {}"+ e.getMessage());
            e.printStackTrace(); // Log error
        }
    }

    private void sendEmail(Receipt receipt) {
        // Send email to customer
        SimpleMailMessage customerMessage = new SimpleMailMessage();
        customerMessage.setTo(receipt.getCustomerEmail());
        customerMessage.setSubject("Payment Receipt");
        customerMessage.setText(createEmailBody(receipt, "Customer", receipt.getCustomerEmail())); // Pass customer email
        customerMessage.setFrom("Speedy Go <shashanksj936@gmail.com>"); // Set from address
        mailSender.send(customerMessage);
        LOGGER.info("Email sent to customer: {}"+ receipt.getCustomerEmail());

        // Send email to transporter
        SimpleMailMessage transporterMessage = new SimpleMailMessage();
        transporterMessage.setTo(receipt.getTransporterEmail());
        transporterMessage.setSubject("Payment Receipt");
        transporterMessage.setText(createEmailBody(receipt, "Transporter", receipt.getTransporterEmail())); // Pass transporter email
        transporterMessage.setFrom("Speedy Go <shashanksj936@gmail.com>"); // Set from address
        mailSender.send(transporterMessage);
        LOGGER.info("Email sent to transporter: {}"+ receipt.getTransporterEmail());
    }

    private String createEmailBody(Receipt receipt, String recipientType, String recipientEmail) {
        return String.format("Dear %s,\n\n" +
                        "We are pleased to inform you that your payment has been processed successfully.\n\n" +
                        "Here are the payment details:\n" +
                        "-------------------------------------\n" +
                        "Payment ID: %s\n" +
                        "Total Price: %.2f\n" +
                        "Recipient Email: %s\n" +  // Include recipient email
                        "-------------------------------------\n\n" +
                        "If you have any questions or concerns, please do not hesitate to contact us.\n\n" +
                        "Thank you for choosing our service!\n\n" +
                        "Best Regards,\n" +
                        "Speedy Go Team",
                recipientType, receipt.getPaymentId(), receipt.getTotalPrice(), recipientEmail); // Pass recipient email
    }
}

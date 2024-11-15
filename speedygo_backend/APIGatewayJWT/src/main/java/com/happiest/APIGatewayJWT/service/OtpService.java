package com.happiest.APIGatewayJWT.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class OtpService {

    private static final Logger logger = Logger.getLogger(OtpService.class.getName());

    public Map<String, String> otpStore = new ConcurrentHashMap<>();

    @Autowired
    private JavaMailSender mailSender;

    // Generate OTP, store it, and send it via email
    public void generateOtp(String email) throws MessagingException {
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Store the OTP for the email
        otpStore.put(email, otp);

        // Log the OTP for debugging (do not log in production for security reasons)
        logger.info("Generated OTP for email " + email + ": " + otp);

        // Send OTP via email
        sendEmailWithOtp(email, otp);
    }

    // Validate OTP by checking if it matches the stored OTP
    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);

        // Log the retrieved OTP for debugging
        logger.info("Stored OTP for email " + email + ": " + storedOtp);
        logger.info("OTP received for validation: " + otp);

        // Check if the OTP is correct
        if (storedOtp != null && storedOtp.equals(otp)) {
            // Remove the OTP after successful validation
            otpStore.remove(email);
            return true;
        }

        // Log incorrect OTP attempt
        logger.warning("Invalid OTP for email " + email);
        return false;
    }

    // Send OTP email using JavaMailSender
    private void sendEmailWithOtp(String email, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Your OTP Code");
        helper.setText("Your OTP is: " + otp, true); // HTML content
        mailSender.send(message);
    }
}

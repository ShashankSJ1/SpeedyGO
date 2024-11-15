package com.happiest.APIGatewayJWT.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Send OTP Email
    public void sendOtpEmail(String to, String otp) throws MessagingException {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("Recipient email must not be null or empty.");
        }

        if (otp == null) {
            throw new IllegalArgumentException("OTP must not be null.");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);  // Set recipient email
        helper.setSubject("Your OTP Code");  // Set email subject

        // Create the HTML content for the OTP email
        String htmlContent = "<html>"
                + "<body>"
                + "<h3>Hello,</h3>"
                + "<p>Your OTP code is: <strong>" + otp + "</strong></p>"
                + "<p>This code is valid for 5 minutes.</p>"
                + "</body>"
                + "</html>";

        helper.setText(htmlContent, true);  // Set the email content as HTML
        mailSender.send(message);  // Send the email
    }
}

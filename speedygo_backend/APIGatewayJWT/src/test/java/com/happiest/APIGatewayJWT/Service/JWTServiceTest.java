package com.happiest.APIGatewayJWT.Service;

import com.happiest.APIGatewayJWT.service.JWTService;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JWTServiceTest {

    private JWTService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JWTService();
    }

    @Test
    public void testGenerateToken() {
        // Arrange
        String username = "test@example.com";

        // Act
        String token = jwtService.generateToken(username);

        // Assert
        assertNotNull(token);
        assertTrue(token.startsWith("ey")); // JWT tokens start with "ey"
    }

    @Test
    public void testExtractUserName() {
        // Arrange
        String username = "test@example.com";
        String token = jwtService.generateToken(username);

        // Act
        String extractedUsername = jwtService.extractUserName(token);

        // Assert
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testValidateTokenValid() {
        // Arrange
        String username = "test@example.com";
        String token = jwtService.generateToken(username);
        when(userDetails.getUsername()).thenReturn(username);

        // Act
        boolean isValid = jwtService.validateToken(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void testValidateTokenInvalid() {
        // Arrange
        String validUsername = "test@example.com";
        String invalidUsername = "invalid@example.com";
        String token = jwtService.generateToken(validUsername);
        when(userDetails.getUsername()).thenReturn(invalidUsername);

        // Act
        boolean isValid = jwtService.validateToken(token, userDetails);

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void testValidateTokenExpired() {
        // Arrange
        String username = "test@example.com";
        String token = jwtService.generateToken(username);

        // Simulate expiration by manipulating the expiration date
        String expiredToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)) // Issued 24 hours ago
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60)) // Expired 1 minute ago
                .signWith(jwtService.getKey())
                .compact();

        // Act
        boolean isValid = jwtService.validateToken(expiredToken, userDetails);

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void testExtractExpiration() {
        // Arrange
        String username = "test@example.com";
        String token = jwtService.generateToken(username);

        // Act
        Date expirationDate = jwtService.extractExpiration(token);

        // Assert
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }
}


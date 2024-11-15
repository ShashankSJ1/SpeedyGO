package com.happiest.BookingService.model;


import com.happiest.BookingService.model.enums.Role;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersTest {

//    @Test
//    public void testDefaultConstructor() {
//        // Arrange
//        Users user = new Users("customer@example.com", "Customer", 1234567890L);
//
//        // Act & Assert
//        assertThat(user.getEmail()).isNull();
//        assertThat(user.getUsername()).isNull();
//        assertThat(user.getPassword()).isNull();
//        assertThat(user.getPhonenumber()).isNull();
//        assertThat(user.getRoles()).isNull();
//        assertThat(user.getVehicle()).isNull();
//    }
//
//    @Test
//    public void testParameterizedConstructor() {
//        // Arrange
//        String email = "test@example.com";
//        String username = "testUser";
//        String password = "password123";
//        Long phoneNumber = 1234567890L;
//        Role role = Role.TRANSPORTER; // Use a valid enum value
//
//        // Act
//        Users user = new Users(email, username, password, phoneNumber, role, null);
//
//        // Assert
//        assertThat(user.getEmail()).isEqualTo(email);
//        assertThat(user.getUsername()).isEqualTo(username);
//        assertThat(user.getPassword()).isEqualTo(password);
//        assertThat(user.getPhonenumber()).isEqualTo(phoneNumber);
//        assertThat(user.getRoles()).isEqualTo(role);
//        assertThat(user.getVehicle()).isNull(); // Vehicle is set to null by default
//    }
//
//    @Test
//    public void testGettersAndSetters() {
//        // Arrange
//        Users user = new Users("customer@example.com", "Customer", 1234567890L);
//        String email = "test@example.com";
//        String username = "testUser";
//        String password = "password123";
//        Long phoneNumber = 1234567890L;
//        Role role = Role.CUSTOMER; // Use a valid enum value
//
//        // Act
//        user.setEmail(email);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setPhonenumber(phoneNumber);
//        user.setRoles(role);
//
//        // Assert
//        assertThat(user.getEmail()).isEqualTo(email);
//        assertThat(user.getUsername()).isEqualTo(username);
//        assertThat(user.getPassword()).isEqualTo(password);
//        assertThat(user.getPhonenumber()).isEqualTo(phoneNumber);
//        assertThat(user.getRoles()).isEqualTo(role);
//    }
//
//    @Test
//    public void testEqualsAndHashCode() {
//        // Arrange
//        Users user1 = new Users("test@example.com", "user1", "password123", 1234567890L, Role.TRANSPORTER, null);
//        Users user2 = new Users("test@example.com", "user1", "password123", 1234567890L, Role.TRANSPORTER, null);
//        Users user3 = new Users("other@example.com", "user2", "password456", 9876543210L, Role.CUSTOMER, null);
//
//        // Act & Assert
//        assertThat(user1).isEqualTo(user2); // Should be equal
//        assertThat(user1).isNotEqualTo(user3); // Should not be equal
//        assertThat(user1.hashCode()).isEqualTo(user2.hashCode()); // Same hash code
//        assertThat(user1.hashCode()).isNotEqualTo(user3.hashCode()); // Different hash code
//    }
}


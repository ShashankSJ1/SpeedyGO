package com.happiest.APIGatewayJWT.model;



import com.happiest.APIGatewayJWT.APiGateway.Booking.model.Vehicle;
import com.happiest.APIGatewayJWT.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "UsersData")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    private String email; // Primary Key

    @Column
    private String username;

    private String password;

    private Long phonenumber;

    @Enumerated(EnumType.STRING)
    private Role roles;

    // One-to-One relationship with Vehicle entity
    @OneToOne(mappedBy = "transporter", cascade = CascadeType.ALL)

    private Vehicle vehicle;
}
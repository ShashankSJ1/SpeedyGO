package com.happiest.Payment.model;


import com.happiest.Payment.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

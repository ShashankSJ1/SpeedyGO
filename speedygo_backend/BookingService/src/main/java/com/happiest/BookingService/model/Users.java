package com.happiest.BookingService.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.happiest.BookingService.model.enums.Role;
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

    @OneToOne(mappedBy = "transporter", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Vehicle vehicle;

}

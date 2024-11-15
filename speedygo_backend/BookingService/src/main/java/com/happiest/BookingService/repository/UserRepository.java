package com.happiest.BookingService.repository;

import com.happiest.BookingService.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
    Users findByEmail(String email);



}

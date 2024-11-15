package com.happiest.Payment.repository;

import com.happiest.Payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByCustomerEmail(String email);
    List<Payment> findByTransporterEmail(String email);
}

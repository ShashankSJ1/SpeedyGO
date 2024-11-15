package com.happiest.BookingService.repository;

import com.happiest.BookingService.model.Vehicle;
import com.happiest.BookingService.model.enums.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    Vehicle findByVehicleNumber(String vehicleNumber);

    @Query("SELECT v FROM Vehicle v WHERE " +
            "(LOWER(v.vehicleName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(v.vehicleType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(v.transporter.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND v.status = 'AVAILABLE'")
    List<Vehicle> searchVehicles(@Param("keyword") String keyword);


    @Query("SELECT v FROM Vehicle v WHERE v.status = 'AVAILABLE'")
    List<Vehicle> findAvailableVehicles();

    @Query("SELECT v FROM Vehicle v WHERE v.status = 'AVAILABLE' AND v.vehicleNumber NOT IN (SELECT b.vehicle.vehicleNumber FROM Booking b)")
    List<Vehicle> findAvailableVehiclesNotInBookings();

    @Query("SELECT v FROM Vehicle v WHERE v.status = 'AVAILABLE' " +
            "AND (:vehicleType IS NULL OR v.vehicleType LIKE %:vehicleType%) " +
            "AND (:basePrice IS NULL OR v.basePrice >= :basePrice) " +
            "AND (:pricePerKilometer IS NULL OR v.pricePerKilometer >= :pricePerKilometer)")
    List<Vehicle> filterVehicles(
            @Param("vehicleType") String vehicleType,
            @Param("basePrice") Double basePrice,
            @Param("pricePerKilometer") Double pricePerKilometer);


    Optional<Vehicle> findByTransporterEmail(String email);

}


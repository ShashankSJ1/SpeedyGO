package com.happiest.BookingService.repository;

import com.happiest.BookingService.model.Booking;
import com.happiest.BookingService.model.enums.VehicleStatus;
import com.happiest.BookingService.model.enums.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findByTransporterEmailAndRideStatus(String transporterEmail, RideStatus status);


    List<Booking> findByCustomerEmailAndRideStatus(String customerEmail, RideStatus status);

    @Query("SELECT b FROM Booking b WHERE b.rideStatus = 'PENDING' OR b.rideStatus = 'REJECTED' OR b.rideStatus = 'COMPLETED'")
    List<Booking> findBookingsWithStatuses();

    //  Get list of completed or rejected bookings for a given transporter email
    @Query("SELECT b FROM Booking b WHERE (b.rideStatus = 'COMPLETED' OR b.rideStatus = 'REJECTED') AND b.transporter.email = :transporterEmail")
    List<Booking> findCompletedOrRejectedByTransporterEmail(@Param("transporterEmail") String transporterEmail);

    // Get list of completed or rejected bookings for a given customer email
    @Query("SELECT b FROM Booking b WHERE (b.rideStatus = 'COMPLETED' OR b.rideStatus = 'REJECTED') AND b.customer.email = :customerEmail")
    List<Booking> findCompletedOrRejectedByCustomerEmail(@Param("customerEmail") String customerEmail);

    @Query("SELECT b FROM Booking b WHERE b.rideStatus = 'REJECTED' AND b.customer.email = :customerEmail")
    List<Booking> findRejectedByCustomerEmail(@Param("customerEmail") String customerEmail);

    @Query("SELECT b FROM Booking b WHERE b.rideStatus = 'REJECTED' AND b.transporter.email = :transporterEmail")
    List<Booking> findRejectedByTransporterEmail(@Param("transporterEmail") String transporterEmail);

    @Query("SELECT b FROM Booking b WHERE b.rideStatus = 'ACCEPTED' AND b.customer.email = :customerEmail")
    List<Booking> findAcceptedByCustomerEmail(@Param("customerEmail") String customerEmail);

    @Query("SELECT b FROM Booking b WHERE b.rideStatus = 'ACCEPTED' AND b.transporter.email = :transporterEmail")
    List<Booking> findAcceptedByTransporterEmail(@Param("transporterEmail") String transporterEmail);

    @Query("SELECT b FROM Booking b WHERE b.rideStatus = 'PENDING' AND b.customer.email = :customerEmail")
    List<Booking> findPendingByCustomerEmail(@Param("customerEmail") String customerEmail);

    @Query("SELECT b FROM Booking b WHERE b.rideStatus = 'PENDING' AND b.transporter.email = :transporterEmail")
    List<Booking> findPendingByTransporterEmail(@Param("transporterEmail") String transporterEmail);

    @Query("SELECT b FROM Booking b WHERE (b.rideStatus = 'ACCEPTED' OR b.rideStatus = 'PENDING') AND b.transporter.email = :transporterEmail")
    List<Booking> findAcceptedOrPendingByTransporterEmail(@Param("transporterEmail") String transporterEmail);

    // Get list of completed or rejected bookings for a given customer email
    @Query("SELECT b FROM Booking b WHERE (b.rideStatus = 'ACCEPTED' OR b.rideStatus = 'PENDING') AND b.customer.email = :customerEmail")
    List<Booking> findAcceptedOrPendingByCustomerEmail(@Param("customerEmail") String customerEmail);

    @Query("SELECT b FROM Booking b WHERE b.rideStatus = 'PAYMENT' AND b.transporter.email = :transporterEmail")
    List<Booking> findPaymentByTransporterEmail(@Param("transporterEmail") String transporterEmail);

    @Query("SELECT b FROM Booking b WHERE b.rideStatus = 'PAYMENT' AND b.customer.email = :customerEmail")
    List<Booking> findPaymentByCustomerEmail(@Param("customerEmail") String customerEmail);

    List<Booking> findByTransporterEmail(String transporterEmail);

}



package com.happiest.BookingService.service;

import com.happiest.BookingService.dto.*;
import com.happiest.BookingService.exceptions.ResourceNotFoundException;
import com.happiest.BookingService.exceptions.RideNotFound;
import com.happiest.BookingService.exceptions.VehicleNotFoundException;
import com.happiest.BookingService.exceptions.VehicleSaveException;
import com.happiest.BookingService.model.Booking;
import com.happiest.BookingService.model.Users;
import com.happiest.BookingService.model.Vehicle;
import com.happiest.BookingService.model.enums.VehicleStatus;
import com.happiest.BookingService.model.enums.RideStatus;
import com.happiest.BookingService.model.enums.Role;
import com.happiest.BookingService.repository.BookingRepository;
import com.happiest.BookingService.repository.UserRepository;
import com.happiest.BookingService.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    public BookingDTO createRideRequest(RideRequest rideRequest) {
        // Fetch the customer and transporter as before
        Users customer = userRepository.findByEmail(rideRequest.getCustomerEmail());
        Users transporter = userRepository.findByEmail(rideRequest.getTransporterEmail());
        Vehicle vehicle = vehicleRepository.findByVehicleNumber(rideRequest.getVehicleNumber());

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        if (transporter == null) {
            throw new IllegalArgumentException("Transporter not found");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle not found");
        }

        // Calculate the total price
        double totalPrice = vehicle.getBasePrice() +  rideRequest.getDistance() * vehicle.getPricePerKilometer();

        // Create the booking
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTransporter(transporter);
        booking.setVehicle(vehicle);
        booking.setSource(rideRequest.getSource());
        booking.setDestination(rideRequest.getDestination());
        booking.setDistance(rideRequest.getDistance());
        booking.setTotalPrice(totalPrice);
        booking.setRideStatus(RideStatus.PENDING);
        booking.setRideDate(rideRequest.getRideDate());
        booking.setRideTime(rideRequest.getRideTime());

        // Save the booking entity
        bookingRepository.save(booking);

        // Map to BookingDTO
        return new BookingDTO(
                booking.getRequestId(),
                booking.getSource(),
                booking.getDestination(),
                booking.getDistance(),
                booking.getTotalPrice(),
                new CustomerInfoDTO(customer.getEmail(), customer.getUsername(), customer.getPhonenumber()),
                new TransporterInfoDTO(transporter.getEmail(), transporter.getUsername(), transporter.getPhonenumber()),
                new VehicleInfoDTO(vehicle.getVehicleName(), vehicle.getStatus(), vehicle.getBasePrice(), vehicle.getPricePerKilometer(), vehicle.getVehicleType(), vehicle.getVehicleNumber()),
                booking.getRideStatus(),
                booking.getRideDate(),
                booking.getRideTime()
        );
    }




    public Booking updateRideStatus(Long requestId, RideStatus status) {
        Booking booking = bookingRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Ride request not found"));

        // Get the vehicle from the booking once, instead of repeating in every condition
        Vehicle vehicle = booking.getVehicle();

        // Update vehicle or perform specific actions based on status
        switch (status) {
            case ACCEPTED:
                vehicleRepository.save(vehicle);
                break;

            case REJECTED:
                vehicleRepository.save(vehicle);
                break;

            case COMPLETED:
                vehicleRepository.save(vehicle);
                break;

            case CANCELLED:
                vehicleRepository.save(vehicle);
                break;

            case PAYMENT:
                // Handle payment-specific logic if required
                vehicleRepository.save(vehicle);
                break;

            default:
                throw new IllegalArgumentException("Unknown RideStatus: " + status);
        }

        // Update booking status
        booking.setRideStatus(status);

        // Save the updated booking and return it
        return bookingRepository.save(booking);
    }




    // Retrieve all pending requests for a transporter
    public List<Booking> getPendingRequests(String transporterEmail) {
        return bookingRepository.findByTransporterEmailAndRideStatus(transporterEmail, RideStatus.PENDING);
    }

    // Retrieve all customer requests
    public List<Booking> getCustomerRequests(String customerEmail) {
        return bookingRepository.findByCustomerEmailAndRideStatus(customerEmail, RideStatus.PENDING);
    }





    public List<VehicleWithTransporterDTO> getCombinedAvailableVehicles(double distance) {
        List<VehicleWithTransporterDTO> availableVehiclesNotInBookings = getAvailableVehiclesNotInBookings(distance);
        List<Vehicle> availableVehiclesWithBookings = getAvailableVehiclesWithBookingStatuses();

        Set<String> vehicleNumbers = new HashSet<>();
        List<VehicleWithTransporterDTO> combinedVehicles = availableVehiclesNotInBookings.stream()
                .filter(vehicleDTO -> vehicleNumbers.add(vehicleDTO.getVehicleInfo().getVehicleNumber()))
                .collect(Collectors.toList());

        for (Vehicle vehicle : availableVehiclesWithBookings) {
            String vehicleNumber = vehicle.getVehicleNumber();
            if (vehicleNumbers.add(vehicleNumber)) {
                VehicleInfoDTO vehicleInfoDTO = new VehicleInfoDTO();
                vehicleInfoDTO.setVehicleName(vehicle.getVehicleName());
                vehicleInfoDTO.setVehicleNumber(vehicle.getVehicleNumber());
                vehicleInfoDTO.setBasePrice(vehicle.getBasePrice());
                vehicleInfoDTO.setVehicleType(vehicle.getVehicleType());
                vehicleInfoDTO.setPricePerKilometer(vehicle.getPricePerKilometer());
                vehicleInfoDTO.setStatus(vehicle.getStatus());

                double totalPrice = vehicle.getBasePrice() + (vehicle.getPricePerKilometer() * distance);
                vehicleInfoDTO.setTotalPrice(totalPrice);

                Users transporter = vehicle.getTransporter();
                TransporterInfoDTO transporterInfoDTO = null;
                if (transporter != null) {
                    transporterInfoDTO = new TransporterInfoDTO();
                    transporterInfoDTO.setEmail(transporter.getEmail());
                    transporterInfoDTO.setUsername(transporter.getUsername());
                    transporterInfoDTO.setPhoneNumber(transporter.getPhonenumber());
                }

                combinedVehicles.add(new VehicleWithTransporterDTO(vehicleInfoDTO, transporterInfoDTO));
            }
        }

        return combinedVehicles;
    }

    public List<VehicleWithTransporterDTO> getAvailableVehiclesNotInBookings(double distance) {
        List<Vehicle> availableVehicles = vehicleRepository.findAvailableVehiclesNotInBookings();
        List<Booking> bookingsWithStatuses = bookingRepository.findBookingsWithStatuses();
        Set<String> bookedVehicleNumbers = bookingsWithStatuses.stream()
                .map(booking -> booking.getVehicle().getVehicleNumber())
                .collect(Collectors.toSet());

        return availableVehicles.stream()
                .filter(vehicle -> !bookedVehicleNumbers.contains(vehicle.getVehicleNumber()))
                .map(vehicle -> {
                    VehicleInfoDTO vehicleInfoDTO = new VehicleInfoDTO();
                    vehicleInfoDTO.setVehicleName(vehicle.getVehicleName());
                    vehicleInfoDTO.setVehicleNumber(vehicle.getVehicleNumber());
                    vehicleInfoDTO.setVehicleType(vehicle.getVehicleType());
                    vehicleInfoDTO.setPricePerKilometer(vehicle.getPricePerKilometer());
                    vehicleInfoDTO.setBasePrice(vehicle.getBasePrice());
                    vehicleInfoDTO.setStatus(vehicle.getStatus());

                    double totalPrice = vehicle.getBasePrice() + (vehicle.getPricePerKilometer() * distance);
                    vehicleInfoDTO.setTotalPrice(totalPrice);

                    Users transporter = vehicle.getTransporter();
                    TransporterInfoDTO transporterInfoDTO = null;
                    if (transporter != null) {
                        transporterInfoDTO = new TransporterInfoDTO();
                        transporterInfoDTO.setEmail(transporter.getEmail());
                        transporterInfoDTO.setUsername(transporter.getUsername());
                        transporterInfoDTO.setPhoneNumber(transporter.getPhonenumber());
                    }

                    return new VehicleWithTransporterDTO(vehicleInfoDTO, transporterInfoDTO);
                })
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }
    public List<Vehicle> getAvailableVehiclesWithBookingStatuses() {
        // 1. Fetch all available vehicles
        List<Vehicle> availableVehicles = vehicleRepository.findAvailableVehicles();

        // 2. Fetch all bookings with statuses PENDING, REJECTED, or COMPLETED
        List<Booking> bookingsWithStatuses = bookingRepository.findBookingsWithStatuses();

        // 3. Filter vehicles that have bookings with specific statuses
        List<Vehicle> vehiclesWithBookings = availableVehicles.stream()
                .filter(vehicle -> bookingsWithStatuses.stream()
                        .anyMatch(booking -> booking.getVehicle().getVehicleNumber().equals(vehicle.getVehicleNumber())))
                .collect(Collectors.toList());

        return vehiclesWithBookings;
    }



    //Get list of bookings where rideStatus is COMPLETED or REJECTED for a given transporter email.
    public List<BookingDTO> getCompletedOrRejectedBookingsByTransporterEmail(String transporterEmail) {
        List<Booking> bookings = bookingRepository.findCompletedOrRejectedByTransporterEmail(transporterEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get list of completed or rejected bookings for a customer by email
    public List<BookingDTO> getCompletedOrRejectedBookingsByCustomerEmail(String customerEmail) {
        List<Booking> bookings = bookingRepository.findCompletedOrRejectedByCustomerEmail(customerEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<BookingDTO> getAcceptedOrPendingBookingsByTransporterEmail(String transporterEmail) {
        List<Booking> bookings = bookingRepository.findAcceptedOrPendingByTransporterEmail(transporterEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get list of completed or rejected bookings for a customer by email
    public List<BookingDTO> getAcceptedOrPendingBookingsByCustomerEmail(String customerEmail) {
        List<Booking> bookings = bookingRepository.findAcceptedOrPendingByCustomerEmail(customerEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    public void updateVehicleStatus(String vehicleNumber, VehicleStatus newStatus) {
        // Fetch the vehicle by vehicle number
        Vehicle vehicle = vehicleRepository.findById(vehicleNumber)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with number: " + vehicleNumber));

        // Update the status
        vehicle.setStatus(newStatus);

        // Save the updated vehicle back to the database
        vehicleRepository.save(vehicle);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        try {
            // Save vehicle to the database
            return vehicleRepository.save(vehicle);
        } catch (Exception e) {
            // Handle any exception, log it, and rethrow a custom exception if needed
            throw new VehicleSaveException("Failed to save the vehicle. Please try again.", e);
        }
    }

    // Get list of rejected bookings for a transporter by email
    public List<BookingDTO> getRejectedBookingsByTransporterEmail(String transporterEmail) {
        List<Booking> bookings = bookingRepository.findRejectedByTransporterEmail(transporterEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get list of rejected bookings for a customer by email
    public List<BookingDTO> getRejectedBookingsByCustomerEmail(String customerEmail) {
        List<Booking> bookings = bookingRepository.findRejectedByCustomerEmail(customerEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get list of pending bookings for a transporter by email
    public List<BookingDTO> getPendingBookingsByTransporterEmail(String transporterEmail) {
        List<Booking> bookings = bookingRepository.findPendingByTransporterEmail(transporterEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get list of pending bookings for a customer by email
    public List<BookingDTO> getPendingBookingsByCustomerEmail(String customerEmail) {
        List<Booking> bookings = bookingRepository.findPendingByCustomerEmail(customerEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BookingDTO> getPaymentBookingsByTransporterEmail(String transporterEmail) {
        List<Booking> bookings = bookingRepository.findPaymentByTransporterEmail(transporterEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get list of pending bookings for a customer by email
    public List<BookingDTO> getPaymentBookingsByCustomerEmail(String customerEmail) {
        List<Booking> bookings = bookingRepository.findPaymentByCustomerEmail(customerEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get list of accepted bookings for a transporter by email
    public List<BookingDTO> getAcceptedBookingsByTransporterEmail(String transporterEmail) {
        List<Booking> bookings = bookingRepository.findAcceptedByTransporterEmail(transporterEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get list of accepted bookings for a customer by email
    public List<BookingDTO> getAcceptedBookingsByCustomerEmail(String customerEmail) {
        List<Booking> bookings = bookingRepository.findAcceptedByCustomerEmail(customerEmail);
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert Booking entity to BookingDTO
    private BookingDTO convertToDTO(Booking booking) {
        CustomerInfoDTO customerInfo = new CustomerInfoDTO(
                booking.getCustomer().getEmail(),
                booking.getCustomer().getUsername(),
                booking.getCustomer().getPhonenumber()
        );

        TransporterInfoDTO transporterInfo = new TransporterInfoDTO(
                booking.getTransporter().getEmail(),
                booking.getTransporter().getUsername(),
                booking.getTransporter().getPhonenumber()
        );

        VehicleInfoDTO vehicleInfo = new VehicleInfoDTO(
                booking.getVehicle().getVehicleName(),
                booking.getVehicle().getStatus(),
                booking.getVehicle().getBasePrice(),
                booking.getVehicle().getPricePerKilometer(),
                booking.getVehicle().getVehicleType(),
                booking.getVehicle().getVehicleNumber()
        );

        return new BookingDTO(
                booking.getRequestId(),
                booking.getSource(),
                booking.getDestination(),
                booking.getDistance(),
                booking.getTotalPrice(),
                customerInfo,
                transporterInfo,
                vehicleInfo,
                booking.getRideStatus(),
                booking.getRideDate(),
                booking.getRideTime()
        );
    }




    public List<VehicleWithTransporterDTO> searchVehicles(String keyword, double distance) {
        List<Vehicle> vehicles = vehicleRepository.searchVehicles(keyword);
        List<VehicleWithTransporterDTO> result = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            VehicleInfoDTO vehicleInfo = new VehicleInfoDTO(
                    vehicle.getVehicleName(),
                    vehicle.getStatus(),
                    vehicle.getBasePrice(),
                    vehicle.getPricePerKilometer(),
                    vehicle.getVehicleType(),
                    vehicle.getVehicleNumber()
            );

            TransporterInfoDTO transporterInfo = null;

            if (vehicle.getTransporter() != null) {
                transporterInfo = new TransporterInfoDTO(
                        vehicle.getTransporter().getEmail(),
                        vehicle.getTransporter().getUsername(),
                        vehicle.getTransporter().getPhonenumber()
                );
            }

            // Calculate total price based on distance
            vehicleInfo.setTotalPrice(vehicle.getBasePrice() + (vehicle.getPricePerKilometer() * distance));

            // Add to result list
            result.add(new VehicleWithTransporterDTO(vehicleInfo, transporterInfo));
        }

        return result;
    }


    public List<VehicleWithTransporterDTO> filterVehicles(
            String vehicleType,
            Double basePrice,
            Double pricePerKilometer,
            String sortField1,
            String sortDirection1,
            String sortField2,
            String sortDirection2,
            String sortField3,
            String sortDirection3,
            double distance) {

        // Ensure distance is mandatory
        if (distance < 0) {
            throw new IllegalArgumentException("Distance must be a non-negative value.");
        }

        // Fetch the filtered vehicles from the database
        List<Vehicle> vehicles = vehicleRepository.filterVehicles(
                vehicleType,
                basePrice,
                pricePerKilometer
        );

        // Sort the vehicles based on the provided sort fields and directions
        vehicles.sort((v1, v2) -> {
            // Compare using the first sort field
            int result = compareByField(v1, v2, sortField1, sortDirection1);
            if (result != 0) return result; // Already sorted by the first field

            // Compare using the second sort field if provided
            if (sortField2 != null && !sortField2.isEmpty()) {
                result = compareByField(v1, v2, sortField2, sortDirection2);
                if (result != 0) return result; // Sorted by the second field
            }

            // Compare using the third sort field if provided
            if (sortField3 != null && !sortField3.isEmpty()) {
                return compareByField(v1, v2, sortField3, sortDirection3); // Finally check the third field
            }

            return 0; // If no sort fields are provided, keep the original order
        });

        // Convert to DTO format
        return vehicles.stream()
                .map(vehicle -> {
                    // Create VehicleInfoDTO
                    VehicleInfoDTO vehicleInfo = new VehicleInfoDTO(
                            vehicle.getVehicleName(),
                            vehicle.getStatus(),
                            vehicle.getBasePrice(),
                            vehicle.getPricePerKilometer(),
                            vehicle.getVehicleType(),
                            vehicle.getVehicleNumber()
                    );

                    // Calculate the total price (basePrice + distance * pricePerKilometer)
                    double totalPrice = vehicle.getBasePrice() + (vehicle.getPricePerKilometer() * distance);
                    vehicleInfo.setTotalPrice(totalPrice);

                    // Handle transporter info
                    TransporterInfoDTO transporterInfo = null;
                    if (vehicle.getTransporter() != null) {
                        transporterInfo = new TransporterInfoDTO(
                                vehicle.getTransporter().getEmail(),
                                vehicle.getTransporter().getUsername(),
                                vehicle.getTransporter().getPhonenumber()
                        );
                    }

                    // Return the combined DTO
                    return new VehicleWithTransporterDTO(vehicleInfo, transporterInfo);
                })
                .collect(Collectors.toList()); // Collect results into a list
    }

    private int compareByField(Vehicle v1, Vehicle v2, String sortField, String sortDirection) {
        // Default to ascending sort if sortDirection is not specified
        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "asc"; // Default sorting direction
        }

        Comparator<Vehicle> comparator;

        // Define comparator based on sort field
        switch (sortField != null ? sortField.toLowerCase() : "") {
            case "vehicletype":
                comparator = Comparator.comparing(Vehicle::getVehicleType, Comparator.nullsLast(Comparator.naturalOrder()));
                break;
            case "baseprice":
                comparator = Comparator.comparing(Vehicle::getBasePrice, Comparator.nullsLast(Comparator.naturalOrder()));
                break;
            case "priceperkilometer":
                comparator = Comparator.comparing(Vehicle::getPricePerKilometer, Comparator.nullsLast(Comparator.naturalOrder()));
                break;
            default:
                // Default case to handle null or empty sortField
                throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }

        // Apply sorting direction
        return ("asc".equalsIgnoreCase(sortDirection) ? comparator : comparator.reversed()).compare(v1, v2);
    }

    public VehicleStatusDTO getVehicleStatusByEmail(String email) {
        Vehicle vehicle = vehicleRepository.findByTransporterEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No vehicle found for transporter with email: " + email));

        return new VehicleStatusDTO(
                vehicle.getVehicleNumber(),
                vehicle.getStatus()
        );
    }

    public Vehicle updateVehiclePricing(String vehicleNumber, double newBasePrice, double newPricePerKilometer) {
        Vehicle vehicle = vehicleRepository.findById(vehicleNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with number: " + vehicleNumber));

        vehicle.setBasePrice(newBasePrice);
        vehicle.setPricePerKilometer(newPricePerKilometer);

        return vehicleRepository.save(vehicle);
    }

    public List<TransporterEarningsDTO> getEarningsByTransporter(String transporterEmail) {
        List<Booking> bookings = bookingRepository.findByTransporterEmail(transporterEmail);

        Map<LocalDateTime, Double> earningsMap = new HashMap<>();

        for (Booking booking : bookings) {
            LocalDateTime dateTime = LocalDateTime.of(booking.getRideDate(), booking.getRideTime());
            earningsMap.put(dateTime, earningsMap.getOrDefault(dateTime, 0.0) + booking.getTotalPrice());
        }

        return earningsMap.entrySet().stream()
                .map(entry -> new TransporterEarningsDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Vehicle getVehicleByNumber(String vehicleNumber) {
        try {
            Vehicle vehicle = vehicleRepository.findByVehicleNumber(vehicleNumber);
            if (vehicle == null) {
                throw new VehicleNotFoundException("Vehicle with number not found.");
            }
            return vehicle;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching the vehicle details: " + e.getMessage(), e);
        }
    }





}

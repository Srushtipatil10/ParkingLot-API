package com.example.Parking_lot.Repository;
import com.example.Parking_lot.Entity.VehicleDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface VehicleDetailsRepository extends JpaRepository<VehicleDetails, String> {
    Optional<VehicleDetails> findByRegistrationNumber(String registrationNumber);
}

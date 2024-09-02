package com.example.Parking_lot.Repository;
import com.example.Parking_lot.Entity.ParkSlot;
import com.example.Parking_lot.Entity.ParkingLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ParkSlotRepository extends JpaRepository<ParkSlot, Long> {
    List<ParkSlot> findBySlotType(String slotType);

    ParkSlot findFirstBySlotType(String slotType);

    ParkSlot findFirstBySlotTypeAndOccupiedFalse(String vehicleType);


    ParkSlot findFirstBySlotTypeAndLevelAndOccupiedFalse(String vehicleType, ParkingLevel level);
}


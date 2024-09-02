package com.example.Parking_lot.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "VehicleDetails")
public class VehicleDetails {
    @Id
    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "vehicle_type")
    private String vehicleType; // "bus", "car", "bike"

    @ManyToOne
    @JoinColumn(name = "level_id")
    private ParkingLevel level;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private ParkSlot parkSlot;
}

package com.example.Parking_lot.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "ParkingLevel")
public class ParkingLevel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name")
        private String name;

        @Column(name = "bus_capacity")
        private int busCapacity;

        @Column(name = "car_capacity")
        private int carCapacity;

        @Column(name = "bike_capacity")
        private int bikeCapacity;

        @Column(name = "occupied_buses")
        private int occupiedBuses;

        @Column(name = "occupied_cars")
        private int occupiedCars;

        @Column(name = "occupied_bikes")
        private int occupiedBikes;

        @OneToMany(mappedBy = "level")
        private Set<ParkSlot> slots;
    }



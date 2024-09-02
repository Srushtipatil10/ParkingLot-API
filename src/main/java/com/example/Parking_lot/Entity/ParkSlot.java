package com.example.Parking_lot.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ParkSlot")
public class ParkSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slot_type")
    private String slotType; // "bus", "car", "bike"

    @Column(name = "occupied")
    private boolean occupied;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private ParkingLevel level;

    @OneToOne(mappedBy = "parkSlot")
    private VehicleDetails vehicle;

}

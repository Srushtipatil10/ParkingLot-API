package com.example.Parking_lot.Model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkSlotModel {
    private Long id;
    private String slotType;
    private boolean occupied;
    private ParkingLevelModel level;

}

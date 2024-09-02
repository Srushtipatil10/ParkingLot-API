package com.example.Parking_lot.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ParkingLevelModel {
    private Long id;
    private String name;
    private int busCapacity;
    private int carCapacity;
    private int bikeCapacity;
    private int occupiedBuses;
    private int occupiedCars;
    private int occupiedBikes;

}

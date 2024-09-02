package com.example.Parking_lot.Model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDetailsModel {
    private String registrationNumber;
    private String vehicleType;
    private ParkSlotModel parkSlot;

}

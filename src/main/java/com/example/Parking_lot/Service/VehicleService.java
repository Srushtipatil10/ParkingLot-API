
package com.example.Parking_lot.Service;

import com.example.Parking_lot.Entity.ParkSlot;
import com.example.Parking_lot.Entity.ParkingLevel;
import com.example.Parking_lot.Entity.VehicleDetails;
import com.example.Parking_lot.Exception.CustomException;
import com.example.Parking_lot.Model.ParkSlotModel;
import com.example.Parking_lot.Model.ParkingLevelModel;
import com.example.Parking_lot.Model.VehicleDetailsModel;
import com.example.Parking_lot.Repository.ParkSlotRepository;
import com.example.Parking_lot.Repository.ParkingLevelRepository;
import com.example.Parking_lot.Repository.VehicleDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    @Autowired
    private ParkingLevelRepository levelRepository;
    @Autowired
    private VehicleDetailsRepository vehicleRepository;

    @Autowired
    private ParkSlotRepository slotRepository;
public String parkVehicle(String vehicleType, String registrationNumber) {
    if (vehicleRepository.existsById(registrationNumber)) {
        throw new CustomException("Vehicle with registration number " + registrationNumber + " is already parked");
    }

    ParkingLevel level = findAvailableParkingLevel(vehicleType);
    if (level == null) {
        throw new CustomException("No available parking space for vehicle type " + vehicleType);
    }

    ParkSlot slot = slotRepository.findFirstBySlotTypeAndLevelAndOccupiedFalse(vehicleType, level);

    while (slot == null) {
        level = findNextAvailableParkingLevel(vehicleType, level);
        if (level == null) {
            throw new CustomException("No available parking space for vehicle type " + vehicleType);
        }
        slot = slotRepository.findFirstBySlotTypeAndOccupiedFalse(vehicleType);
        if (slot == null) {
            slot = new ParkSlot();
            slot.setSlotType(vehicleType);
            slot.setOccupied(false);
            slot = slotRepository.save(slot);
        }
        slot.setLevel(level);
    }

    VehicleDetails vehicle = new VehicleDetails();
    vehicle.setRegistrationNumber(registrationNumber);
    vehicle.setVehicleType(vehicleType);
    vehicle.setLevel(level);
    vehicle.setParkSlot(slot);
    vehicleRepository.save(vehicle);

    slot.setOccupied(true);
    slotRepository.save(slot);

    updateOccupiedCount(level, vehicleType,1);
    levelRepository.save(level);

    return "Vehicle parked successfully";
}

    private ParkingLevel findAvailableParkingLevel(String vehicleType) {
        List<ParkingLevel> levels = levelRepository.findAll();

        for (ParkingLevel level : levels) {
            if (hasAvailableCapacity(level, vehicleType)) {
                return level;
            }
        }
        return null;
    }

    private ParkingLevel findNextAvailableParkingLevel(String vehicleType, ParkingLevel currentLevel) {
        List<ParkingLevel> levels = levelRepository.findAll();
        int currentIndex = levels.indexOf(currentLevel);

        for (int i = currentIndex; i < levels.size(); i++) {
            ParkingLevel level = levels.get(i);
            if (hasAvailableCapacity(level, vehicleType)) {
                return level;
            }
        }
        return null;
    }

    private boolean hasAvailableCapacity(ParkingLevel level, String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "bus":
                return level.getOccupiedBuses() < level.getBusCapacity();
            case "car":
                return level.getOccupiedCars() < level.getCarCapacity();
            case "bike":
                return level.getOccupiedBikes() < level.getBikeCapacity();
            default:
                throw new CustomException("Unknown vehicle type: " + vehicleType);
        }
    }

    private void updateOccupiedCount(ParkingLevel level, String vehicleType, int countChange) {
        switch (vehicleType.toLowerCase()) {
            case "bus":
                level.setOccupiedBuses(level.getOccupiedBuses() + countChange);
                break;
            case "car":
                level.setOccupiedCars(level.getOccupiedCars() + countChange);
                break;
            case "bike":
                level.setOccupiedBikes(level.getOccupiedBikes() + countChange);
                break;
            default:
                throw new CustomException("Unknown vehicle type: " + vehicleType);
        }
    }

    public String unparkVehicle(String registrationNumber) {

        VehicleDetails vehicle = vehicleRepository.findById(registrationNumber)
                .orElseThrow(() -> new CustomException("Vehicle with registration number " + registrationNumber + " not found"));


        ParkSlot slot = vehicle.getParkSlot();
        slot.setOccupied(false);
        slotRepository.save(slot);
        vehicleRepository.delete(vehicle);

        return "Vehicle unparked successfully";
    }

    public VehicleDetailsModel retrieveVehicleDetails(String registrationNumber) {
        VehicleDetails vehicle = vehicleRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new CustomException("Vehicle with registration number " + registrationNumber + " not found"));

        return convertToModel(vehicle);
    }

    public List<VehicleDetailsModel> getAllParkedVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    private VehicleDetailsModel convertToModel(VehicleDetails vehicle) {
        VehicleDetailsModel model = new VehicleDetailsModel();
        model.setRegistrationNumber(vehicle.getRegistrationNumber());
        model.setVehicleType(vehicle.getVehicleType());

        if (vehicle.getParkSlot() != null) {
            model.setParkSlot(convertToModel(vehicle.getParkSlot()));
        } else {
            model.setParkSlot(null);
        }

        return model;
    }

    private ParkSlotModel convertToModel(ParkSlot slot) {
        ParkSlotModel model = new ParkSlotModel();
        model.setId(slot.getId());
        model.setSlotType(slot.getSlotType());
        model.setOccupied(slot.isOccupied());

        if (slot.getLevel() != null) {
            ParkingLevelModel levelModel = new ParkingLevelModel();
            levelModel.setId(slot.getLevel().getId());
            levelModel.setName(slot.getLevel().getName());
            levelModel.setBusCapacity(slot.getLevel().getBusCapacity());
            levelModel.setCarCapacity(slot.getLevel().getCarCapacity());
            levelModel.setBikeCapacity(slot.getLevel().getBikeCapacity());
            levelModel.setOccupiedBuses(slot.getLevel().getOccupiedBuses());
            levelModel.setOccupiedCars(slot.getLevel().getOccupiedCars());
            levelModel.setOccupiedBikes(slot.getLevel().getOccupiedBikes());
            model.setLevel(levelModel);
        }

        return model;
    }
}

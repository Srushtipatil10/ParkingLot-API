
package com.example.Parking_lot.Service;

import com.example.Parking_lot.Entity.ParkSlot;
import com.example.Parking_lot.Entity.ParkingLevel;
import com.example.Parking_lot.Exception.CustomException;
import com.example.Parking_lot.Model.ParkingStatisticsModel;
import com.example.Parking_lot.Repository.ParkSlotRepository;
import com.example.Parking_lot.Repository.ParkingLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ParkSlotService {
    @Autowired
    private ParkSlotRepository slotRepository;

    @Autowired
    private ParkingLevelRepository levelRepository;

    public String createParkingLot(int numberOfLevels, int slotsPerLevel) {
        for (int i = 1; i <= numberOfLevels; i++) {
            ParkingLevel level = new ParkingLevel();
            level.setName("Level " + i);
            level.setBusCapacity(slotsPerLevel);
            level.setCarCapacity(slotsPerLevel);
            level.setBikeCapacity(slotsPerLevel);
            level.setOccupiedBuses(0);
            level.setOccupiedCars(0);
            level.setOccupiedBikes(0);

            Set<ParkSlot> slots = new HashSet<>();
            for (int j = 1; j <= slotsPerLevel; j++) {
                ParkSlot busSlot = new ParkSlot();
                busSlot.setSlotType("bus");
                busSlot.setOccupied(false);
                busSlot.setLevel(level);
                slots.add(busSlot);

                ParkSlot carSlot = new ParkSlot();
                carSlot.setSlotType("car");
                carSlot.setOccupied(false);
                carSlot.setLevel(level);
                slots.add(carSlot);

                ParkSlot bikeSlot = new ParkSlot();
                bikeSlot.setSlotType("bike");
                bikeSlot.setOccupied(false);
                bikeSlot.setLevel(level);
                slots.add(bikeSlot);
            }
            level.setSlots(slots);
            levelRepository.save(level);
            slotRepository.saveAll(slots);
        }
        return "Parking lot created successfully with " + numberOfLevels + " levels and " + slotsPerLevel + " slots per level.";
    }

public String increaseParkingLevel(int numberOfLevels, int slotsPerLevel) {
    for (int i = 1; i <= numberOfLevels; i++) {
        ParkingLevel level = new ParkingLevel();
        level.setName("Level " + (levelRepository.count() + 1));
        level.setBusCapacity(slotsPerLevel);
        level.setCarCapacity(slotsPerLevel);
        level.setBikeCapacity(slotsPerLevel);
        level.setOccupiedBuses(0);
        level.setOccupiedCars(0);
        level.setOccupiedBikes(0);

        Set<ParkSlot> slots = new HashSet<>();
        for (int j = 1; j <= slotsPerLevel; j++) {
            ParkSlot busSlot = new ParkSlot();
            busSlot.setSlotType("bus");
            busSlot.setOccupied(false);
            busSlot.setLevel(level);
            slots.add(busSlot);

            ParkSlot carSlot = new ParkSlot();
            carSlot.setSlotType("car");
            carSlot.setOccupied(false);
            carSlot.setLevel(level);
            slots.add(carSlot);

            ParkSlot bikeSlot = new ParkSlot();
            bikeSlot.setSlotType("bike");
            bikeSlot.setOccupied(false);
            bikeSlot.setLevel(level);
            slots.add(bikeSlot);
        }
        level.setSlots(slots);
        levelRepository.save(level);
        slotRepository.saveAll(slots);
    }
    return "Parking level increased successfully with " + numberOfLevels + " new levels and " + slotsPerLevel + " slots per level.";
}

    public String decreaseParkingLevel(Long levelId) {
        ParkingLevel level = levelRepository.findById(levelId)
                .orElseThrow(() -> new CustomException("Parking level with ID " + levelId + " not found"));

        if (!level.getSlots().isEmpty()) {
            throw new CustomException("Cannot delete a level with existing slots");
        }

        levelRepository.delete(level);
        return "Parking level decreased successfully";
    }

    public String decreaseParkingLevel() {
        return "Parking level decreased successfully";
    }
    public ParkingStatisticsModel getParkingLotStatistics() {
        List<ParkingLevel> levels = levelRepository.findAll();

        ParkingStatisticsModel stats = new ParkingStatisticsModel();
        stats.setLevels(levels.stream()
                .map(level -> {
                    int totalSlots = level.getSlots().size();
                    long occupiedSlots = level.getSlots().stream().filter(ParkSlot::isOccupied).count();
                    long unoccupiedSlots = totalSlots - occupiedSlots;

                    return new ParkingStatisticsModel.LevelStatistics(
                            level.getId(),
                            level.getName(),
                            totalSlots,
                            (int) occupiedSlots,
                            (int) unoccupiedSlots,
                            level.getOccupiedBuses(),
                            level.getOccupiedCars(),
                            level.getOccupiedBikes()
                    );
                }).collect(Collectors.toList()));

        return stats;
    }
}


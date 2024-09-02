package com.example.Parking_lot.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParkingStatisticsModel {
    private List<LevelStatistics> levels;

    @Getter
    @Setter
    public static class LevelStatistics {
        private Long id;
        private String name;
        private int totalSlots;
        private int occupiedSlots;
        private int unoccupiedSlots;
        private int occupiedBuses;
        private int occupiedCars;
        private int occupiedBikes;

        public LevelStatistics(Long id, String name, int totalSlots, int occupiedSlots, int unoccupiedSlots, int occupiedBuses, int occupiedCars, int occupiedBikes) {
            this.id = id;
            this.name = name;
            this.totalSlots = totalSlots;
            this.occupiedSlots = occupiedSlots;
            this.unoccupiedSlots = unoccupiedSlots;
            this.occupiedBuses = occupiedBuses;
            this.occupiedCars = occupiedCars;
            this.occupiedBikes = occupiedBikes;
        }
    }

}

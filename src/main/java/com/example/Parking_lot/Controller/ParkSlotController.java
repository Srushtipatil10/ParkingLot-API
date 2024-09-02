package com.example.Parking_lot.Controller;
import com.example.Parking_lot.Model.ParkingStatisticsModel;
import com.example.Parking_lot.Model.LevelRequest;
import com.example.Parking_lot.Service.ParkSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2.0/parking-lot")
public class ParkSlotController {
    @Autowired
    private ParkSlotService parkSlotService;

    @PostMapping("/parking")
    public ResponseEntity<String> createParkingLot(@RequestBody LevelRequest request) {
        try {
            String result = parkSlotService.createParkingLot(request.getNumberOfLevels(), request.getSlotsPerLevel());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

@PutMapping("/level")
public ResponseEntity<String> increaseLevel(@RequestBody LevelRequest request) {
    String result = parkSlotService.increaseParkingLevel(request.getNumberOfLevels(), request.getSlotsPerLevel());
    return ResponseEntity.ok(result);
}
    @DeleteMapping("/level/{level-id}")
    public ResponseEntity<String> decreaseLevel() {
        String result = parkSlotService.decreaseParkingLevel();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/statistics")
    public ResponseEntity<ParkingStatisticsModel> getParkingStatistics() {
        ParkingStatisticsModel stats = parkSlotService.getParkingLotStatistics();
        return ResponseEntity.ok(stats);
    }
}

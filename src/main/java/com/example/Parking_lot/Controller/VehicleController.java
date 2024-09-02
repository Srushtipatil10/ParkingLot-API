package com.example.Parking_lot.Controller;
import com.example.Parking_lot.Model.VehicleDetailsModel;
import com.example.Parking_lot.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2.0/parking")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/vehicles")
    public ResponseEntity<String> parkVehicle(@RequestBody VehicleDetailsModel vehicleDetailsModel) {
        String result = vehicleService.parkVehicle(vehicleDetailsModel.getVehicleType(), vehicleDetailsModel.getRegistrationNumber());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/vehicles/{registration-number}")
    public ResponseEntity<String> unparkVehicle(@PathVariable("registration-number") String registrationNumber) {
        String result = vehicleService.unparkVehicle(registrationNumber);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/vehicles/{registration-number}")
    public ResponseEntity<VehicleDetailsModel> retrieveVehicle(@PathVariable("registration-number") String registrationNumber) {
        VehicleDetailsModel vehicle = vehicleService.retrieveVehicleDetails(registrationNumber);
        return vehicle != null ? ResponseEntity.ok(vehicle) : ResponseEntity.notFound().build();
    }


    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleDetailsModel>> getAllParkedVehicles() {
        List<VehicleDetailsModel> vehicles = vehicleService.getAllParkedVehicles();
        return ResponseEntity.ok(vehicles);
    }
}
package com.ensf480final.AirlineManager.controller;

import com.ensf480final.AirlineManager.model.Aircraft;
import com.ensf480final.AirlineManager.model.Flight;
import com.ensf480final.AirlineManager.service.AircraftService;
import com.ensf480final.AirlineManager.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class AircraftController {
    @Autowired
    private AircraftService aircraftService;

    // adds a new aircraft, returns null if name already exists
    @PostMapping("/addAircraft")
    public ResponseEntity<Aircraft> addAircraft(@RequestBody Aircraft aircraft) {
        // aircraftName is null
        if (aircraft.getAircraftName() == null) {
            return ResponseEntity
                    .noContent()
                    .header("No entered aircraft name", "null")
                    .build();
        }

        // Check if aircraftName exists
        // returns null if exists, else it returns the newAircraft object
        Aircraft newAircraft = aircraftService.createAircraft(aircraft);
        if ( newAircraft == null) {
            return ResponseEntity
                    .badRequest()
                    .header("Aircraft name already exists...", aircraft.getAircraftName())
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .header("Aircraft created...", "Aircraft ID: " + String.valueOf(newAircraft.getAircraftId()))
                    .body(newAircraft);
        }
    }

    @DeleteMapping("/removeAircraftByName/{aircraftName}")
    public ResponseEntity<Aircraft> removeAircraftByName(@PathVariable String aircraftName) {
        Aircraft deletedAircraft = aircraftService.getAircraftByName(aircraftName);
        if (deletedAircraft != null) {
            Aircraft deleted = aircraftService.deleteAircraft(deletedAircraft.getAircraftId());
            return ResponseEntity
                    .ok()
                    .body(deleted);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Aircraft does not exist", "Aircraft name: " + aircraftName)
                    .build();
        }
    }

    // deletes an aircraft by aircraftId
    @DeleteMapping("/removeAircraftById/{aircraftId}")
    public ResponseEntity<Aircraft> removeAircraft(@PathVariable Long aircraftId) {
        Aircraft deletedAircraft = aircraftService.deleteAircraft(aircraftId);
        if (deletedAircraft != null) {
            return ResponseEntity
                    .ok()
                    .body(deletedAircraft);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Aircraft does not exist", "Aircraft ID: " + String.valueOf(aircraftId))
                    .build();
        }
    }

    // returns a list of all aircraft
    @GetMapping("/getAircrafts")
    public ResponseEntity<List<Aircraft>> getAircrafts() {
        List<Aircraft> aircrafts = aircraftService.getAllAircrafts();
        if (aircrafts.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(aircrafts);
        }
    }

    // Updates the name of an aircraft
    @PutMapping("renameAircraft/{aircraftId}/{aircraftName}")
    public ResponseEntity<Aircraft> renameAircraft(@PathVariable Long aircraftId, @PathVariable String aircraftName) {
        Aircraft aircraft = aircraftService.findAircraftById(aircraftId);
        if ( aircraft == null ) {
            return ResponseEntity
                    .badRequest()
                    .header("Aircraft does not exist", "Aircraft ID : " + String.valueOf(aircraftId))
                    .build();
        } else {
            aircraft.setAircraftName(aircraftName);
            Aircraft renamedAircraft = aircraftService.saveAircraft(aircraft);
            return ResponseEntity
                    .ok()
                    .body(renamedAircraft);
        }
    }
}

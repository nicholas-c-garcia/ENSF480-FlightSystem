package com.ensf480final.AirlineManager.controller;

import com.ensf480final.AirlineManager.model.Aircraft;
import com.ensf480final.AirlineManager.model.CrewMember;
import com.ensf480final.AirlineManager.service.AircraftService;
import com.ensf480final.AirlineManager.service.CrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class CrewController {
    @Autowired
    private CrewService crewService;

    @Autowired
    private AircraftService aircraftService;

    // Create a crew member with no crew
    @PostMapping("/addCrewMember")
    public ResponseEntity<CrewMember> addCrewMember(@RequestBody CrewMember crewMember) {
        CrewMember newCrewMember = crewService.saveCrewMember(crewMember);
        return ResponseEntity
                .ok()
                .body(newCrewMember);
    }

    // Creates a new crew member and adds them to an aircraft's crew
    @PostMapping("/addCrewMember/{aircraftId}")
    public ResponseEntity<CrewMember> addCrewMemberToAircraft(@RequestBody CrewMember crewMember, @PathVariable Long aircraftId) {

        Aircraft aircraftExist = aircraftService.findAircraftById(aircraftId);
        if ( aircraftExist == null ) {
            return ResponseEntity
                    .noContent()
                    .header("Aircraft not found", "Aircraft ID:" + String.valueOf(aircraftId))
                    .build();
        }

        crewMember.setAircraftId(aircraftId);
        CrewMember newCrewMember = crewService.saveCrewMember(crewMember);
        return ResponseEntity
                .ok()
                .body(newCrewMember);
    }

    // Removes a crew member specified by aircraft and crew id
    // returns null if crew member does not exist or if crew is not in aircraft's crew
    @PutMapping("/removeCrewMember/{aircraftId}/{crewId}")
    public ResponseEntity<CrewMember> removeCrewMemberInAircraft(@PathVariable Long aircraftId, @PathVariable Long crewId) {
        Aircraft aircraft = aircraftService.findAircraftById(aircraftId);
        if (aircraft == null) {
            return ResponseEntity
                    .noContent()
                    .header("Aircraft does not exist", "Aircraft ID: " + String.valueOf(aircraftId))
                    .build();
        }

        CrewMember removedCrewMember = crewService.getCrewMemberById(crewId);
        if (removedCrewMember == null) {
            return ResponseEntity
                    .badRequest()
                    .header("No Crew Member by ID", "Crew ID: " + String.valueOf(crewId))
                    .build();
        } else if (!aircraftId.equals(removedCrewMember.getAircraftId())) {
            return ResponseEntity
                    .badRequest()
                    .header("Crew Member is not in aircraft crew", "Aircraft ID: " + String.valueOf(aircraftId))
                    .build();
        } else {
            removedCrewMember.setAircraftId(null);
            CrewMember freeCrewMember = crewService.saveCrewMember(removedCrewMember);
            return ResponseEntity
                    .ok()
                    .body(freeCrewMember);
        }
    }

    // Removes a crew member specified by crewId
    // returns crew member if deleted, null if they don't exist
    @DeleteMapping("/deleteCrewMember/{crewId}")
    public ResponseEntity<CrewMember> deleteCrewMember(@PathVariable Long crewId) {
        CrewMember removedCrewMember = crewService.getCrewMemberById(crewId);
        if (removedCrewMember == null) {
            return ResponseEntity
                    .badRequest()
                    .header("No Crew Member by ID", "Crew ID: " + String.valueOf(crewId))
                    .build();
        } else {
            CrewMember deletedCrewMember = crewService.deleteCrewMember(crewId);
            return ResponseEntity
                    .ok()
                    .body(deletedCrewMember);
        }
    }

    @GetMapping("/getCrewMember/{crewId}")
    public ResponseEntity<CrewMember> getCrewMember(@PathVariable Long crewId) {
        CrewMember crewMember = crewService.getCrewMemberById(crewId);
        if ( crewMember == null) {
            return ResponseEntity
                    .noContent()
                    .header("Crew Member does not exist", "Crew ID: " + String.valueOf(crewId))
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(crewMember);
        }
    }

    @GetMapping("/getCrewMembers")
    public ResponseEntity<List<CrewMember>> getCrewMembers() {
        List<CrewMember> crewMembers = crewService.getAllCrewMembers();
        if ( crewMembers.isEmpty() ) {
            return ResponseEntity
                    .noContent()
                    .header("No crew members in database", "Hire people")
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(crewMembers);
        }
    }

    @GetMapping("/getCrewMembers/{aircraftId}")
    public ResponseEntity<List<CrewMember>> getAircraftCrew(@PathVariable Long aircraftId) {
        Aircraft aircraft = aircraftService.findAircraftById(aircraftId);
        if (aircraft == null) {
            return ResponseEntity
                    .noContent()
                    .header("Aircraft does not exist", "Aircraft ID: " + String.valueOf(aircraftId))
                    .build();
        }

        List<CrewMember> crewMembers = crewService.getAircraftCrew(aircraftId);
        if ( crewMembers.isEmpty() ) {
            return ResponseEntity
                    .noContent()
                    .header("No crew members in aircraft", "Aircraft ID: " + String.valueOf(aircraftId))
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(crewMembers);
        }
    }

}

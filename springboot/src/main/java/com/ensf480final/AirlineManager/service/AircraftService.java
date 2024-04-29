package com.ensf480final.AirlineManager.service;

import com.ensf480final.AirlineManager.model.Aircraft;
import com.ensf480final.AirlineManager.model.CrewMember;
import com.ensf480final.AirlineManager.model.Flight;
import com.ensf480final.AirlineManager.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private CrewService crewService;

    // return the new aircraft if it doesn't exist already, returns null if it does
    public Aircraft createAircraft(Aircraft aircraft) {
        Aircraft newAircraft = aircraftRepository.findByAircraftName(aircraft.getAircraftName());
        if (newAircraft == null) {
            newAircraft = aircraftRepository.save(aircraft);
            return newAircraft;
        } else {
            return null;
        }
    }

    // returns aircraft object by Id, null if it doesnt exist
    public Aircraft findAircraftById(Long aircraftId) {
        return aircraftRepository.findById(aircraftId).orElse(null);
    }

    // returns aircraft object by name, null if it doesnt exist
    public Aircraft getAircraftByName(String name) {
        return aircraftRepository.findByAircraftName(name);
    }

    // delete the aircraft
    public Aircraft deleteAircraft(Long aircraftId) {
        Aircraft deletedAircraft = findAircraftById(aircraftId);
        if (deletedAircraft != null) {

            // remove crew from aircraft, not delete them
            List<CrewMember> crewMembers = crewService.getAircraftCrew(aircraftId);
            for (CrewMember crewMember : crewMembers) {
                crewMember.setAircraftId(null);
                crewService.saveCrewMember(crewMember);
            }

            // get List of flights and delete them
            List<Flight> flights = flightService.getFlightsByAircraftId(aircraftId);
            for (Flight flight : flights) {
                flightService.deleteFlightById(flight.getFlightId());
            }
            aircraftRepository.deleteById(aircraftId);
        }
        return deletedAircraft;
    }

    // returns all aircrafts
    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }

    // save aircraft
    public Aircraft saveAircraft(Aircraft aircraft) { return aircraftRepository.save(aircraft); }
}

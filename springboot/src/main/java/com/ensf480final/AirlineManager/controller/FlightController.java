package com.ensf480final.AirlineManager.controller;

import com.ensf480final.AirlineManager.model.Aircraft;
import com.ensf480final.AirlineManager.model.Flight;
import com.ensf480final.AirlineManager.service.AircraftService;
import com.ensf480final.AirlineManager.service.FlightService;
import com.ensf480final.AirlineManager.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// This is to connect to the front end, you'll get an error if you dont add this
@CrossOrigin("http://localhost:3000")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private AircraftService aircraftService;

    @Autowired
    private SeatService seatService;

    @PostMapping("/addFlight/{aircraftId}")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight, @PathVariable Long aircraftId) {
        Aircraft aircraft = aircraftService.findAircraftById(aircraftId);
        if ( aircraft != null ) {
            flight.setAircraftId(aircraftId);
            Flight newFlight = flightService.saveFlight(flight);
            flightService.createFlightSeats(newFlight.getFlightId());
            return ResponseEntity
                    .ok()
                    .body(newFlight);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Aircraft does not exist", "Aircraft ID: " + String.valueOf(aircraftId))
                    .build();
        }
    }

    @GetMapping("/getFlights")
    public ResponseEntity<List<Flight>> getFlights() {
        List<Flight> flights = flightService.getAllFlights();
        if ( flights.isEmpty() ) {
            return ResponseEntity
                    .noContent()
                    .header("No flights in database", "Go make some")
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(flights);
        }
    }



    @GetMapping("/getFlight/{flightId}")
    public ResponseEntity<Flight> getFlightByFlightId(@PathVariable Long flightId) {
        Flight theFlight = flightService.getFlightById(flightId);
        if ( theFlight == null ) {
            return ResponseEntity
                    .noContent()
                    .header("No flight in database")
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(theFlight);
        }
    }

    @GetMapping("/getFlightsByAircraftId/{aircraftId}")
    ResponseEntity<List<Flight>> getFlightsByAircraftId(@PathVariable Long aircraftId) {
        List<Flight> flights = flightService.getFlightsByAircraftId(aircraftId);
        if ( flights.isEmpty() ) {
            return ResponseEntity
                    .noContent()
                    .header("No flights on this aircraft", "Aircraft ID: " + aircraftId)
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(flights);
        }
    }

    @GetMapping("/getFlightsByDate/{departureDate}")
    ResponseEntity<List<Flight>> getFlightsByDate(@PathVariable String departureDate) {
        List<Flight> flights = flightService.getFlightsByDepartureDate(departureDate);
        if ( flights.isEmpty() ) {
            return ResponseEntity
                    .noContent()
                    .header("No flights departing on this date", "Departure date: " + departureDate)
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(flights);
        }
    }

    @DeleteMapping("/deleteFlight/{flightId}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable Long flightId) {
        Flight deletedFlight = flightService.deleteFlightById(flightId);
        if (deletedFlight != null) {
            return ResponseEntity
                    .ok()
                    .body(deletedFlight);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Flight does not exist", flightId.toString())
                    .build();
        }
    }
}
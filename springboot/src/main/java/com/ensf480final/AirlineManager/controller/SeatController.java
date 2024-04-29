package com.ensf480final.AirlineManager.controller;

import com.ensf480final.AirlineManager.model.Account;
import com.ensf480final.AirlineManager.model.Flight;
import com.ensf480final.AirlineManager.model.Seat;
import com.ensf480final.AirlineManager.repository.SeatRepository;
import com.ensf480final.AirlineManager.service.FlightService;
import com.ensf480final.AirlineManager.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @Autowired
    private FlightService flightService;

    @GetMapping("/getSeats")
    public ResponseEntity<List<Seat>> getSeats() {
        List<Seat> seats = seatService.getAllSeats();
        if ( seats.isEmpty() ) {
            return ResponseEntity
                    .noContent()
                    .header("No seats in database", "Go make flights")
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(seats);
        }
    }

    @GetMapping("/getSeat/{seatId}")
    public ResponseEntity<Seat> getSeatbyId(@PathVariable Long seatId) {
        Seat seat = seatService.getSeatById(seatId);
        if ( seat == null ) {
            return ResponseEntity
                    .badRequest()
                    .header("Seat not found", "Seat ID: " + String.valueOf(seatId))
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(seat);
        }
    }

    @GetMapping("/getSeats/{flightId}")
    public ResponseEntity<List<Seat>> getSeatbyFlightId(@PathVariable Long flightId) {
        List<Seat> seats = seatService.getSeatsByFlightId(flightId);
        if ( seats.isEmpty() ) {
            return ResponseEntity
                    .badRequest()
                    .header("Flight does not exist", "Flight ID: " + String.valueOf(flightId))
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(seats);
        }
    }

    @PutMapping("/bookSeat/{seatId}")
    public ResponseEntity<Seat> bookSeat(@PathVariable Long seatId) {
        Seat seat = seatService.getSeatById(seatId);
        if ( seat == null ) {
            return ResponseEntity
                    .badRequest()
                    .header("Seat not found", "Seat ID: " + String.valueOf(seatId))
                    .build();
        } else {
            Seat bookedSeat = seatService.bookSeat(seatId);
            return ResponseEntity
                    .ok()
                    .body(bookedSeat);
        }
    }

    @PutMapping("/unbookSeat/{seatId}")
    public ResponseEntity<Seat> unbookSeat(@PathVariable Long seatId) {
        Seat seat = seatService.getSeatById(seatId);
        if ( seat == null ) {
            return ResponseEntity
                    .badRequest()
                    .header("Seat not found", "Seat ID: " + String.valueOf(seatId))
                    .build();
        } else {
            Seat unbookedSeat = seatService.unbookSeat(seatId);
            return ResponseEntity
                    .ok()
                    .body(unbookedSeat);
        }
    }

    /* ------------------------------ Might be used ------------------------------ */
    @PostMapping("/addSeat/{flightId}")
    public ResponseEntity<Seat> newSeat(@RequestBody Seat newSeat, Long flightId) {
        Flight temp = flightService.getFlightById(newSeat.getFlightId());
        if ( temp != null) {
            Seat returnSeat = seatService.createSeat(newSeat);
            return ResponseEntity
                    .ok()
                    .body(returnSeat);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Flight does not exist...", newSeat.getFlightId().toString())
                    .build();
        }
    }

    @DeleteMapping("/deleteSeat/{seatId}")
    public ResponseEntity<Seat> deleteSeat(@PathVariable Long seatId) {
        Seat deletedSeat = seatService.deleteSeatById(seatId);
        if (deletedSeat != null) {
            return ResponseEntity
                    .ok()
                    .body(deletedSeat);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Seat does not exist", seatId.toString())
                    .build();
        }
    }

    @PutMapping("/updateSeat/{seatId}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long seatId) {
        return ResponseEntity
                .ok()
                .body(seatService.updateBooking(seatId));
    }

}
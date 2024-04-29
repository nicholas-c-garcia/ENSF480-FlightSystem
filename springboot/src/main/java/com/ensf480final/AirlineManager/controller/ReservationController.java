package com.ensf480final.AirlineManager.controller;

import com.ensf480final.AirlineManager.model.Account;
import com.ensf480final.AirlineManager.model.Reservation;
import com.ensf480final.AirlineManager.service.AccountService;
import com.ensf480final.AirlineManager.service.EmailSenderService;
import com.ensf480final.AirlineManager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
@RestController
@CrossOrigin("http://localhost:3000")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailSenderService senderService;

    @PostConstruct
    public void init() {
        // Register EmailSenderService as an observer
        reservationService.addObserver(senderService);
    }

    @PostMapping("/addReservation/{flightId}")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation, @PathVariable Long flightId) {
        reservation.setFlightId(flightId);
        Reservation newReservation = reservationService.createReservation(reservation);
        return ResponseEntity
                .ok()
                .header(String.valueOf(reservation.getReservationId()))
                .body(newReservation);
    }

    // get reservations data from sql
    @GetMapping("/getReservations")
    public ResponseEntity<List<Reservation>> getReservation() {
        List<Reservation> reservations = reservationService.getAllReservations();
        if (reservations.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(reservations);
        }
    }

    @GetMapping("/getReservations/{flightId}")
    public ResponseEntity<List<Reservation>> getReservationByFlight(@PathVariable Long flightId) {
        List<Reservation> reservations = reservationService.getReservationByFlightId(flightId);
        if (reservations.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(reservations);
        }
    }

    @DeleteMapping("/removeReservation/{reservationId}")
    public ResponseEntity<Reservation> removeReservation(@PathVariable Long reservationId) {
        Reservation deletedReservation = reservationService.deletedReservation(reservationId);
        if (deletedReservation != null) {
            senderService.sendSimpleEmail(deletedReservation.getEmail(), "Cancelled Reservation", "Cancelled Reservation for Flight " + deletedReservation.getFlightId());
            return ResponseEntity
                    .ok()
                    .body(deletedReservation);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Reservation not found", "Reservation ID: " + String.valueOf(reservationId))
                    .build();
        }
    }

    @GetMapping("/getAccountReservations/{accountId}")
    public ResponseEntity<List<Reservation>> getAccountReservation(@PathVariable String username) {
        Account account = accountService.getAccountById(username);
        List<Reservation> reservations = reservationService.getReservationByEmail(account.getEmailAddress());
        return ResponseEntity
                .ok()
                .body(reservations);

    }
}
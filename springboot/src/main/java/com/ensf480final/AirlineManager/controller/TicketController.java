package com.ensf480final.AirlineManager.controller;

import com.ensf480final.AirlineManager.model.Ticket;
import com.ensf480final.AirlineManager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private EmailSenderService senderService;

    @PostMapping("/addTicket/{reservationId}")
    public ResponseEntity<Ticket> newTicket(@RequestBody Ticket newTicket, @PathVariable Long reservationId) {
        newTicket.setReservationId(reservationId);
        Ticket returnTicket = ticketService.createTicket(newTicket);
        String email = reservationService.findReservationById(reservationId).getEmail();
        int cost = seatService.getSeatById(newTicket.getSeatId()).getCost();
        int seat  = seatService.getSeatById(newTicket.getSeatId()).getSeatNumber();
        Long flight = seatService.getSeatById(newTicket.getSeatId()).getFlightId();
        senderService.sendSimpleEmail(email, "Ticket Booked", "Seat " + seat + " booked on Flight " + flight + "\nCost: " + cost);
        return ResponseEntity
                .ok()
                .body(returnTicket);
    }

    @GetMapping("/getTickets")
    public ResponseEntity<List<Ticket>> getTickets() {
        return ResponseEntity
                .ok()
                .body(ticketService.getAllTickets());
    }

    @GetMapping("/getTickets/{reservationId}")
    public ResponseEntity<List<Ticket>> getTicketsByReservationId(@PathVariable Long reservationId) {
        return ResponseEntity
                .ok()
                .body(ticketService.getByReservationId(reservationId));
    }

    @DeleteMapping("/deleteTicket/{ticketId}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable Long ticketId) {
        Ticket deletedTicket = ticketService.deleteTicketById(ticketId);
        if (deletedTicket != null) {
            return ResponseEntity
                    .ok()
                    .body(deletedTicket);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Ticket does not exist", Long.toString(ticketId))
                    .build();
        }
    }
}
package com.ensf480final.AirlineManager.service;

import java.util.List;

import com.ensf480final.AirlineManager.model.Ticket;
import com.ensf480final.AirlineManager.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private ReservationService reservationService;

    public Ticket createTicket(Ticket ticket) {
        seatService.bookSeat(ticket.getSeatId());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getByReservationId(Long reservationId) { return ticketRepository.findByReservationId(reservationId); }

    public List<Ticket> getByFlightId(Long flightId) { return ticketRepository.findByFlightId(flightId); }

    public Ticket getBySeatId(Long seatId) { return ticketRepository.findBySeatId(seatId); }

    // unsure about below, add function to get by reservation id?
    public Ticket getTicketById(Long ticketID) {
        return ticketRepository.findById(ticketID).orElse(null);
    }

    public Ticket deleteTicketById(Long ticketId) {
        Ticket deletedTicket = getTicketById(ticketId);
        if (deletedTicket != null) {
            seatService.unbookSeat(deletedTicket.getSeatId());
            ticketRepository.deleteById(ticketId);
        }
        return deletedTicket;
    }


}
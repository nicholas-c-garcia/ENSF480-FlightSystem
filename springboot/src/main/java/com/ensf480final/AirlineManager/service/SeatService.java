package com.ensf480final.AirlineManager.service;

import com.ensf480final.AirlineManager.model.Account;
import com.ensf480final.AirlineManager.model.Flight;
import com.ensf480final.AirlineManager.model.Seat;
import com.ensf480final.AirlineManager.model.Ticket;
import com.ensf480final.AirlineManager.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketService ticketService;

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public Seat updateBooking(Long seatId) {
        Seat foundSeat = getSeatById(seatId);
        foundSeat.setBooked(true);
        return seatRepository.save(foundSeat);
    }

    public Seat bookSeat(Long seatId) {
        Seat foundSeat = getSeatById(seatId);
        foundSeat.setBooked(true);
        return seatRepository.save(foundSeat);
    }

    public Seat unbookSeat(Long seatId) {
        Seat foundSeat = getSeatById(seatId);
        foundSeat.setBooked(false);
        return seatRepository.save(foundSeat);
    }

    public List<Seat> getSeatsByFlightId(Long id) {
        return seatRepository.findByFlightId(id);
    }

    public Seat getSeatById(Long id) {
        return seatRepository.findById(id).orElse(null);
    }

    public Seat deleteSeatById(Long seatId) {
        Seat deletedSeat = getSeatById(seatId);
        if (deletedSeat != null) {
            // also delete the ticket associated to this, if there is one!!!!
            Ticket ticket = ticketService.getBySeatId(seatId);
            if ( ticket != null ) {
                ticketService.deleteTicketById(ticket.getTicketId());
            }
            seatRepository.deleteById(seatId);
        }
        return deletedSeat;
    }

}
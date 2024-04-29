package com.ensf480final.AirlineManager.repository;

import com.ensf480final.AirlineManager.model.Reservation;
import com.ensf480final.AirlineManager.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByFlightId(Long flightId);
    List<Ticket> findByReservationId(Long reservationId);

    Ticket findBySeatId(Long seatId);

}
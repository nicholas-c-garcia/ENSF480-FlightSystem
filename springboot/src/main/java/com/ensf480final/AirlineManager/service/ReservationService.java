package com.ensf480final.AirlineManager.service;

import com.ensf480final.AirlineManager.model.Reservation;
import com.ensf480final.AirlineManager.model.Ticket;
import com.ensf480final.AirlineManager.repository.ReservationObserver;
import com.ensf480final.AirlineManager.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TicketService ticketService;

    private List<ReservationObserver> observers = new ArrayList<>();

    public void addObserver(ReservationObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(Reservation reservation) {
        for (ReservationObserver observer : observers) {
            observer.onReservationCreated(reservation);
        }
    }
    public Reservation createReservation(Reservation reservation) {
        notifyObservers(reservation);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation deletedReservation(Long reservationId) {
        Reservation deletedReservation = findReservationById(reservationId);
        if (deletedReservation != null) {
            List<Ticket> tickets = ticketService.getByReservationId(reservationId);
            for ( Ticket ticket : tickets) {
                ticketService.deleteTicketById(ticket.getTicketId());
            }
            reservationRepository.deleteById(reservationId);
        }
        return deletedReservation;
    }

    public Reservation findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }

    public List<Reservation> getReservationByFlightId(Long flightId) {
        return reservationRepository.findByFlightId(flightId);
    }

    public List<Reservation> getReservationByEmail(String email) {
        return reservationRepository.findByEmail(email);
    }
}
package com.ensf480final.AirlineManager.service;

import com.ensf480final.AirlineManager.model.Account;
import com.ensf480final.AirlineManager.model.Flight;
import com.ensf480final.AirlineManager.model.Reservation;
import com.ensf480final.AirlineManager.model.Seat;
import com.ensf480final.AirlineManager.repository.FlightRepository;
import com.ensf480final.AirlineManager.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private ReservationService reservationService;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public List<Flight> getFlightsByDepartureDate(String departureDate) {
        return flightRepository.findByDepartureDate(departureDate);
    }

    public List<Flight> getFlightsByAircraftId(Long aircraftId) {
        return flightRepository.findByAircraftId(aircraftId);
    }

    public Flight deleteFlightById(Long flightId) {
        Flight deletedFlight = getFlightById(flightId);
        if (deletedFlight != null) {
            List<Reservation> reservations = reservationService.getReservationByFlightId(flightId);
            for (Reservation reservation : reservations) {
                reservationService.deletedReservation(reservation.getReservationId());
            }

            List<Seat> seats = seatService.getSeatsByFlightId(flightId);
            for (Seat seat : seats) {
                seatService.deleteSeatById(seat.getSeatId());
            }
            flightRepository.deleteById(flightId);
        }
        return deletedFlight;
    }

    public void createFlightSeats(Long flightId) {
        for(int i = 1; i < 13; i++) {
            Seat newSeat = new Seat();
            newSeat.setCost(300);
            newSeat.setSeatNumber(i);
            newSeat.setBooked(false);
            newSeat.setType("First Class");
            newSeat.setFlightId(flightId);
            seatService.createSeat(newSeat);
        }

        for(int i = 13; i < 29; i++){
            Seat newSeat = new Seat();
            newSeat.setCost(200);
            newSeat.setSeatNumber(i);
            newSeat.setBooked(false);
            newSeat.setType("Business Class");
            newSeat.setFlightId(flightId);
            seatService.createSeat(newSeat);
        }

        for(int i = 29; i < 49; i++) {
            Seat newSeat = new Seat();
            newSeat.setCost(100);
            newSeat.setSeatNumber(i);
            newSeat.setBooked(false);
            newSeat.setType("Commercial Class");
            newSeat.setFlightId(flightId);
            seatService.createSeat(newSeat);
        }
    }
}
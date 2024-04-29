package com.ensf480final.AirlineManager.repository;

import com.ensf480final.AirlineManager.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByFlightId(Long flightId);

    List<Reservation> findByEmail(String email);
}
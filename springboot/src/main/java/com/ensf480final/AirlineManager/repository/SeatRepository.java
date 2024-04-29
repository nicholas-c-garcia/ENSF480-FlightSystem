package com.ensf480final.AirlineManager.repository;

import com.ensf480final.AirlineManager.model.CrewMember;
import com.ensf480final.AirlineManager.model.Flight;
import com.ensf480final.AirlineManager.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightId(Long flightId);
}
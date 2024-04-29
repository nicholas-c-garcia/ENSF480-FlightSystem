package com.ensf480final.AirlineManager.repository;

import com.ensf480final.AirlineManager.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByDepartureDate(String departureDate);

    List<Flight> findByAircraftId(Long aircraftId);

}
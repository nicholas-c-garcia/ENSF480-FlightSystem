package com.ensf480final.AirlineManager.repository;

import com.ensf480final.AirlineManager.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    Aircraft findByAircraftName(String aircraftName);
}

package com.ensf480final.AirlineManager.repository;

import com.ensf480final.AirlineManager.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrewRepository extends JpaRepository<CrewMember, Long> {
    List<CrewMember> findByAircraftId(Long aircraftId);
    List<CrewMember> findByType(String type);
    List<CrewMember> findByAircraftIdAndType(Long aircraftId, String type);
}

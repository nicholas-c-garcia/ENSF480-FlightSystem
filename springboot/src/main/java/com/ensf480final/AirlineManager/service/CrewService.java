package com.ensf480final.AirlineManager.service;

import com.ensf480final.AirlineManager.model.CrewMember;
import com.ensf480final.AirlineManager.repository.CrewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewService {
    @Autowired
    private CrewRepository crewRepository;
    public CrewMember saveCrewMember(CrewMember crewMember) {
        return crewRepository.save(crewMember);
    }

    public CrewMember deleteCrewMember(Long crewId) {
        CrewMember deletedCrewMember = getCrewMemberById(crewId);
        if (deletedCrewMember != null) {
            crewRepository.delete(deletedCrewMember);
        }
        return deletedCrewMember;
    }

    public List<CrewMember> getAllCrewMembers() {
        return crewRepository.findAll();
    }

    public List<CrewMember> getAircraftCrew(Long aircraftId) {
        return crewRepository.findByAircraftId(aircraftId);
    }

    public CrewMember getCrewMemberById(Long crewId) {
        return crewRepository.findById(crewId).orElse(null);
    }

    public List<CrewMember> getAllPilots() { return crewRepository.findByType("Pilot"); }

    public List<CrewMember> getAllStewards() { return crewRepository.findByType("Steward"); }

    public List<CrewMember> getAircraftPilots(Long aircraftId) { return crewRepository.findByAircraftIdAndType(aircraftId, "Pilot"); }

    public List<CrewMember> getAircraftStewards(Long aircraftId) { return crewRepository.findByAircraftIdAndType(aircraftId, "Steward"); }
}

package com.ensf480final.AirlineManager.service;

import com.ensf480final.AirlineManager.model.Account;
import com.ensf480final.AirlineManager.model.Aircraft;
import com.ensf480final.AirlineManager.model.CrewMember;
import com.ensf480final.AirlineManager.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StartupService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CrewService crewService;

    @Autowired
    private AircraftService aircraftService;

    @Autowired
    private FlightService flightService;

    public void addAccountsOnStartup() {
        Account admin1 = new Account();
        admin1.setFirstName("Admin");
        admin1.setLastName("One");
        admin1.setEmailAddress("admin1@gmail.com");
        admin1.setUsername("ADMIN1");
        admin1.setPassword("password");
        admin1.setAccessLevel("Admin");

        Account admin2 = new Account();
        admin2.setFirstName("Admin");
        admin2.setLastName("Two");
        admin2.setEmailAddress("admin2@gmail.com");
        admin2.setUsername("ADMIN2");
        admin2.setPassword("password");
        admin2.setAccessLevel("Admin");

        Account agent1 = new Account();
        agent1.setFirstName("Agent");
        agent1.setLastName("One");
        agent1.setEmailAddress("agent1@gmail.com");
        agent1.setUsername("AGENT1");
        agent1.setPassword("password");
        agent1.setAccessLevel("Agent");

        Account agent2 = new Account();
        agent2.setFirstName("Agent");
        agent2.setLastName("Two");
        agent2.setEmailAddress("agent2@gmail.com");
        agent2.setUsername("AGENT2");
        agent2.setPassword("password");
        agent2.setAccessLevel("Agent");

        accountService.saveAccount(admin1);
        accountService.saveAccount(admin2);
        accountService.saveAccount(agent1);
        accountService.saveAccount(agent2);
    }

    public void createAircraftsAndCrewAndFlights() {

        /*--------------------- create first aircraft and crew if it exists---------------------*/
        Aircraft aircraft1 = new Aircraft();
        aircraft1.setAircraftName("Moussavi-Boeing");
        Aircraft newAircraft1 = aircraftService.createAircraft(aircraft1);
        if ( newAircraft1 != null ) {
            CrewMember pilot1 = new CrewMember();
            pilot1.setFirstName("Buzz");
            pilot1.setLastName("Lightyear");
            pilot1.setType("Pilot");
            pilot1.setAircraftId(newAircraft1.getAircraftId());
            crewService.saveCrewMember(pilot1);

            CrewMember steward1 = new CrewMember();
            steward1.setFirstName("Anya");
            steward1.setLastName("Stark");
            steward1.setType("Steward");
            steward1.setAircraftId(newAircraft1.getAircraftId());
            crewService.saveCrewMember(steward1);

            Flight flight1 = new Flight();
            flight1.setAircraftId(newAircraft1.getAircraftId());
            flight1.setOrigin("Calgary");
            flight1.setDestination("Edmonton");
            flight1.setDepartureDate("2023-12-14");
            flight1.setDepartureTime("9:00");
            Flight newFlight1 = flightService.saveFlight(flight1);
            flightService.createFlightSeats(newFlight1.getFlightId());
        }

        Aircraft aircraft2 = new Aircraft();
        aircraft2.setAircraftName("Aardvark Avenger");
        Aircraft newAircraft2 = aircraftService.createAircraft(aircraft2);
        if ( newAircraft2 != null ) {
            CrewMember pilot2 = new CrewMember();
            pilot2.setFirstName("John");
            pilot2.setLastName("Lang");
            pilot2.setType("Pilot");
            pilot2.setAircraftId(newAircraft2.getAircraftId());
            crewService.saveCrewMember(pilot2);

            CrewMember steward2 = new CrewMember();
            steward2.setFirstName("Iggy");
            steward2.setLastName("Jojo");
            steward2.setType("Steward");
            steward2.setAircraftId(newAircraft2.getAircraftId());
            crewService.saveCrewMember(steward2);

            Flight flight2 = new Flight();
            flight2.setAircraftId(newAircraft2.getAircraftId());
            flight2.setOrigin("Calgary");
            flight2.setDestination("Philippines");
            flight2.setDepartureDate("2024-01-12");
            flight2.setDepartureTime("12:30");
            Flight newFlight2 = flightService.saveFlight(flight2);
            flightService.createFlightSeats(newFlight2.getFlightId());
        }
        return;
    }

}

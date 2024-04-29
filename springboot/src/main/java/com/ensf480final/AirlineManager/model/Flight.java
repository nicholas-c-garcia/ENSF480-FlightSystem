package com.ensf480final.AirlineManager.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Flight {

    @Id
    @GeneratedValue
    private Long flightId;
    private String origin;
    private String destination;
    private String departureDate;
    private String departureTime;

    private Long aircraftId;

    // Getters
    public Long getFlightId() {
        return flightId;
    }
    public String getDepartureTime() {
        return departureTime;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDestination() {
        return destination;
    }

    public Long getAircraftId() { return aircraftId; }

    // Setters
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setAircraftId(Long aircraftId) { this.aircraftId = aircraftId; }

}
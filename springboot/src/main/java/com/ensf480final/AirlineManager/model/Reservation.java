package com.ensf480final.AirlineManager.model;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Long reservationId;

    private boolean insurance;

    private String email;

    private Long cost;

    private Long flightId;

    public Reservation() {

    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long c) {
        this.cost = c;
    }

    public boolean getInsurance() { return insurance; }

    public Long getFlightId() { return flightId; }

    public String getEmail() { return email; }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public void setInsurance(boolean bool) {
        this.insurance = bool;
    }

    public void setFlightId(Long flightID) { this.flightId = flightID; }

    public void setEmail(String email) { this.email = email; }


}
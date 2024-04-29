package com.ensf480final.AirlineManager.model;

import javax.persistence.*;

@Entity
public class Seat {
    // needs editing --> flightID references Flight class, join tables??
    @Id
    @GeneratedValue
    private Long seatId;
    private int seatNumber;
    private boolean booked;
    private int cost;
    private String type;
    private Long flightId;

    public Seat() {}

    public Seat(Flight flight, int seatNumber, boolean booked, int cost, String type) {
        this.seatNumber = seatNumber;
        this.booked = booked;
        this.cost = cost;
        this.type = type;
    }

    // getter for seatId
    public Long getSeatId() {
        return seatId;
    }

    // Getter for seatNumber
    public int getSeatNumber() {
        return seatNumber;
    }

    // Setter for seatNumber
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Getter for booked
    public boolean isBooked() {
        return this.booked;
    }

    // Setter for booked
    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    // Getter for cost
    public int getCost() {
        return cost;
    }

    // Setter for cost
    public void setCost(int cost) {
        this.cost = cost;
    }

    // Getter for type
    public String getType() {
        return type;
    }

    // Setter for type
    public void setType(String type) {
        this.type = type;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getFlightId() { return flightId; }
}


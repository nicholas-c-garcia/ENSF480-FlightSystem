package com.ensf480final.AirlineManager.model;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue
    private Long ticketId;

    private Long flightId;

    private Long reservationId;

    private Long seatId;

    public Long getTicketId() {
        return this.ticketId;
    }

    public Long getSeatId() { return this.seatId; }

    public Long getReservationId() { return this.reservationId; }

    public Long getFlightId() { return this.flightId; }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public void setSeatId(Long seatId) { this.seatId = seatId; }

}
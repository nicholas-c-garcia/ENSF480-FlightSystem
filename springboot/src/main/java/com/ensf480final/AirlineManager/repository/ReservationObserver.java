package com.ensf480final.AirlineManager.repository;

import com.ensf480final.AirlineManager.model.Reservation;

public interface ReservationObserver {
    void onReservationCreated(Reservation reservation);
}

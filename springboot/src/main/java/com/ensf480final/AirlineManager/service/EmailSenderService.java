package com.ensf480final.AirlineManager.service;

import com.ensf480final.AirlineManager.model.Reservation;
import com.ensf480final.AirlineManager.repository.ReservationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService implements ReservationObserver {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void onReservationCreated(Reservation reservation) {
        sendSimpleEmail(reservation.getEmail(), "Flight Reservation", "Reservation made for flight " + reservation.getFlightId() + "\nTotal Cost: " + reservation.getCost());
    }
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("flightmanager480@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");

    }
}

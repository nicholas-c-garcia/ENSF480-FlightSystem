package com.ensf480final.AirlineManager.repository;

import com.ensf480final.AirlineManager.model.Account;
import com.ensf480final.AirlineManager.model.CreditCard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    CreditCard findByCreditCardNumber(Long creditCardNumber);
}

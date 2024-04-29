package com.ensf480final.AirlineManager.service;

import com.ensf480final.AirlineManager.model.Account;
import com.ensf480final.AirlineManager.model.CreditCard;
import com.ensf480final.AirlineManager.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
public class CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;

    public CreditCard getCreditCardById(Long creditCardId) {
        return creditCardRepository.findById(creditCardId).orElse(null);
    }

    public CreditCard saveCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    public void deleteCreditCard(Long creditCardId) {
        if ( getCreditCardById(creditCardId) == null ) {
            return;
        } else {
            creditCardRepository.deleteById(creditCardId);
        }
    }
}

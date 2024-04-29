package com.ensf480final.AirlineManager.controller;

import java.util.List;

import com.ensf480final.AirlineManager.model.Account;
import com.ensf480final.AirlineManager.service.AccountService;
import com.ensf480final.AirlineManager.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ensf480final.AirlineManager.model.CreditCard;
import com.ensf480final.AirlineManager.repository.CreditCardRepository;
@RestController
@CrossOrigin("http://localhost:3000")
public class CreditCardController {
    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private AccountService accountService;


    // add a new credit card to the database
    @PostMapping("/addCreditCard")
    ResponseEntity<CreditCard> addCreditCard(@RequestBody CreditCard creditCard) {
        CreditCard newCreditCard = creditCardService.saveCreditCard(creditCard);
        return ResponseEntity
                .ok()
                .body(newCreditCard);
    }

    // retrieve credit card by username/accountId, return null if account does not exist
    @GetMapping("/getCreditCard/{username}")
    public ResponseEntity<CreditCard> getCreditCard(@PathVariable String username) {
        Account account = accountService.getAccountById(username);
        if (account != null) {
            Long creditCardId = account.getCreditCardId();
            CreditCard creditCard = creditCardService.getCreditCardById(creditCardId);
            return ResponseEntity
                    .ok()
                    .body(creditCard);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Account does not exist", username)
                    .build();
        }
    }

    // update a Customer's credit card by username
    @PutMapping("/updateCreditCard/{username}")
    public ResponseEntity<CreditCard> updateCreditCard(@RequestBody CreditCard newCreditCard, @PathVariable String username) {
        Account account = accountService.getAccountById(username);
        if (account != null) {
            // retrieve credit card id associated to account
            Long creditCardId = account.getCreditCardId();

            // set newCreditCard Id as account's credit card id
            // save newCreditCard object with id into repo
            newCreditCard.setCreditCardId(creditCardId);
            CreditCard updatedCreditCard = creditCardService.saveCreditCard(newCreditCard);
            return ResponseEntity
                    .ok()
                    .body(updatedCreditCard);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Username does not exist", username)
                    .build();
        }
    }
}

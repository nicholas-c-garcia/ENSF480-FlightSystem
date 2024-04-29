package com.ensf480final.AirlineManager.service;

import java.util.List;

import com.ensf480final.AirlineManager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensf480final.AirlineManager.model.Account;
import com.ensf480final.AirlineManager.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private ReservationService reservationService;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(String username) {
        return accountRepository.findById(username).orElse(null);
    }

    public Account getAccountByEmailAddress(String emailAddress) { return accountRepository.findByEmailAddress(emailAddress); }
    
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account deleteAccountById(String accountId) {
        Account deletedAccount = getAccountById(accountId);
        if (deletedAccount != null) {
            List<Reservation> reservations = reservationService.getReservationByEmail(deletedAccount.getEmailAddress());
            for ( Reservation reservation : reservations ) {
                reservationService.deletedReservation(reservation.getReservationId());
            }
            creditCardService.deleteCreditCard(deletedAccount.getCreditCardId());
            accountRepository.deleteById(accountId);
        }
        return deletedAccount;
    }

    public List<Account> getRegisteredUsers() {
        return accountRepository.findByAccessLevel("Registered");
    }
}

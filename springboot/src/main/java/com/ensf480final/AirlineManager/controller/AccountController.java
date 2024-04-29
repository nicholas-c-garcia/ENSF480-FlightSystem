package com.ensf480final.AirlineManager.controller;

import com.ensf480final.AirlineManager.model.Account;
import com.ensf480final.AirlineManager.model.Reservation;
import com.ensf480final.AirlineManager.service.AccountService;

import java.util.ArrayList;
import java.util.List;

import com.ensf480final.AirlineManager.service.CrewService;
import com.ensf480final.AirlineManager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ReservationService reservationService;

    // Creates a new account if there was no pre-existing username / email address
    @PostMapping("/addAccount")
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        Account existUsername = accountService.getAccountById(account.getUsername());
        Account existEmail = accountService.getAccountByEmailAddress(account.getEmailAddress());
        if ( existUsername == null && existEmail == null) {
            Account newAccount = accountService.saveAccount(account);
            return ResponseEntity
                    .ok()
                    .body(newAccount);
        } else {
            HttpHeaders headers = new HttpHeaders();
            if (existUsername != null) {
                headers.add("Username already exists", "Username: " + existUsername.getUsername());
            }
            if (existEmail != null) {
                headers.add("Email address already exists", "Email Address: " + existEmail.getEmailAddress());
            }
            return ResponseEntity
                    .badRequest()
                    .headers(headers)
                    .build();
        }
    }

    @PutMapping("/updateAccount")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account updatedAccount = accountService.getAccountById(account.getUsername());
        if ( updatedAccount != null) {
            Account newAccount = accountService.saveAccount(account);
            return ResponseEntity
                    .ok()
                    .body(newAccount);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Account does not exist", "Account ID: " + account.getUsername())
                    .build();
        }
    }


    // Gets list of accounts
    @GetMapping("/getAccounts")
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .header("No accounts", "Business is bad")
                    .build();
        } else {
            return  ResponseEntity
                    .ok()
                    .body(accounts);
        }
    }

    // Returns an account by username, null if it doesn't exist
    @GetMapping("/getAccountById/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable String accountId) {
        Account account = accountService.getAccountById(accountId);
        if ( account != null ) {
            return ResponseEntity
                    .ok()
                    .body(account);
        } else {
            return  ResponseEntity
                    .badRequest()
                    .header("Username does not exist", accountId)
                    .build();
        }
    }

    // Returns an account by email address, null if it doesn't exist
    @GetMapping("/getAccountByEmail/{emailAddress}")
    public ResponseEntity<Account> getAccountByEmailAddress(@PathVariable String emailAddress) {
        Account account = accountService.getAccountByEmailAddress(emailAddress);
        if ( account != null ) {
            return ResponseEntity
                    .ok()
                    .body(account);
        } else {
            return  ResponseEntity
                    .badRequest()
                    .header("Email address does not exist", emailAddress)
                    .build();
        }
    }

    // Deletes an account by accountId
    @DeleteMapping("/deleteAccount/{accountId}")
    public ResponseEntity<Account> deleteAccount(@PathVariable String accountId) {
        Account deletedAccount = accountService.deleteAccountById(accountId);
        if (deletedAccount != null) {
            return ResponseEntity
                    .ok()
                    .body(deletedAccount);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header("Account does not exist", accountId)
                    .build();
        }
    }

    @GetMapping("/getAccountsByFlight/{flightId}")
    public ResponseEntity<List<Account>> getAccountByFlightId(@PathVariable Long flightId) {
        List<Reservation> reservations = reservationService.getReservationByFlightId(flightId);
        if ( reservations.isEmpty() ) {
            return ResponseEntity
                    .noContent()
                    .header("No reservations in flight", "Flight ID: " + String.valueOf(flightId))
                    .build();
        } else {
            List<Account> accounts = new ArrayList<>();
            for (Reservation reservation : reservations) {
                accounts.add(accountService.getAccountByEmailAddress(reservation.getEmail()));
            }
            return ResponseEntity
                    .ok()
                    .body(accounts);
        }
    }

    @GetMapping("/getRegisteredUsers")
    public ResponseEntity<List<Account>> getRegisteredUsers() {
        List<Account> accounts = accountService.getRegisteredUsers();
        if ( accounts.isEmpty() ) {
            return ResponseEntity
                    .noContent()
                    .header("No registered users", "Go make some")
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(accounts);
        }
    }
}

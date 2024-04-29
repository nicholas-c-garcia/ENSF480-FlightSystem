package com.ensf480final.AirlineManager.model;

import javax.persistence.*;

@Entity
public class CreditCard {
    @Id
    @GeneratedValue
    private Long creditCardId;
    private Long creditCardNumber;
    private Long cvv;
    private Long expiryMonth;
    private Long expiryYear;

    // Getters
    public Long getCreditCardId() { return creditCardId; }
    public Long getCreditCardNumber() {
        return creditCardNumber;
    }
    public Long getCVV() {
        return cvv;
    }
    public Long getExpiryMonth() {
        return expiryMonth;
    }
    public Long getExpiryYear() {
        return expiryYear;
    }
    public Account getAccount(Account account) {
        return account;
    }
    // Setters
    public void setCreditCardId(Long creditCardId) { this.creditCardId = creditCardId; }
    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setCVV(Long cvv) {
        this.cvv = cvv;
    }

    public void setExpiryMonth(Long expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public void setExpiryYear(Long expiryYear) {
        this.expiryYear = expiryYear;
    }
}

package com.ensf480final.AirlineManager.model;

import javax.persistence.*;

@Entity
public class Account extends Person{
    @Id
    private String username;
    private String emailAddress;
    private String password;
    private String accessLevel;
    private Long creditCardId;


    // Constructors
    public Account(){
        super();
    }

    public Account(String firstName, String lastName, String emailAddress, String username, String password, String accessLevel) {
        super(firstName, lastName);
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    // Getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Long getCreditCardId() { return creditCardId; }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCreditCardId(Long creditCardId) { this.creditCardId = creditCardId; }

}



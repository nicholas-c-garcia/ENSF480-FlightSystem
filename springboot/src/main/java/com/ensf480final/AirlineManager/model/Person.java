package com.ensf480final.AirlineManager.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person {
    private String firstName;
    private String lastName;

    public Person(){}
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // getters
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    // Setters
    public void setLastName(String lname) {
        this.lastName = lname;
    }

    public void setFirstName(String fname) {
        this.firstName = fname;
    }

}
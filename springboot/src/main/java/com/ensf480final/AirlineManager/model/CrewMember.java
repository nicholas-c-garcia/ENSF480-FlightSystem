package com.ensf480final.AirlineManager.model;

import javax.persistence.*;

@Entity
public class CrewMember extends Person {
    // crew could be a parent class to pilot and steward?
    @Id
    @GeneratedValue
    private Long crewId;
    // more variables?
    private String type;

    private Long aircraftId;

    // Default constructor
    public CrewMember() {
        // Default constructor is needed by JPA
    }

    // Getter for crewID
    public Long getCrewId() {
        return crewId;
    }

    // Setter for crewID
    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    // Getter for type
    public String getType() {
        return type;
    }

    // Setter for type
    public void setType(String type) {
        this.type = type;
    }

    public Long getAircraftId() { return aircraftId; }
    public void setAircraftId(Long aircraftId) { this.aircraftId = aircraftId; }

}


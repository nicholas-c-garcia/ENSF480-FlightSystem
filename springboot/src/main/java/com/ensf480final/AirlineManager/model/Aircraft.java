package com.ensf480final.AirlineManager.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Aircraft {

    @Id
    @GeneratedValue
    private Long aircraftId;
    private String aircraftName;

    // getters
    public Long getAircraftId() {
        return aircraftId;
    }

    public String getAircraftName() { return aircraftName; }

    // setters
    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public void setAircraftName(String aircraftName) { this.aircraftName = aircraftName; }
}
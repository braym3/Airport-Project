package com.example.airportproject.model;

import java.util.UUID;

public class Terminal {
    private UUID id;
    private Integer number;

    public Terminal() {

    }

    public Terminal(Integer number){
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
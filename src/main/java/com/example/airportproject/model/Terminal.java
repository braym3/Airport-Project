package com.example.airportproject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Terminal {
    private UUID id;
    private Integer number;

    private List<Gate> gates;

    public Terminal(Integer number){
        this.number = number;
        this.gates = new ArrayList<>();
    }

    public Terminal(Integer number, List<Gate> gates) {
        this.number = number;
        this.gates = gates;
    }

    public Terminal(UUID id, Integer number){
        this.id = id;
        this.number = number;
        this.gates = new ArrayList<>();
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

    public List<Gate> getGates() {
        return gates;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }
}
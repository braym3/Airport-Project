package com.example.airportproject.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Terminal {
    private UUID id;

    @NotNull
    @Min(0)
    @Max(10)
    private Integer number;

    private List<Gate> gates;

    public Terminal(@NotNull Integer number){
        this.number = number;
        this.gates = new ArrayList<>();
    }

    public Terminal(UUID id, @NotNull Integer number){
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

    public @NotNull Integer getNumber() {
        return number;
    }

    public void setNumber(@NotNull @Min(0) @Max(10) Integer number) {
        this.number = number;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }
}
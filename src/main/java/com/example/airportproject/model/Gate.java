package com.example.airportproject.model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class Gate {
    private UUID id;
    @NotNull
    @Min(0)
    @Max(50)
    private Integer number;
    @NotNull
    private UUID terminalId;

    public Gate(@NotNull Integer number, @NotNull UUID terminalId) {
        this.number = number;
        this.terminalId = terminalId;
    }

    public Gate(UUID id, @NotNull Integer number, @NotNull UUID terminalId) {
        this.id = id;
        this.number = number;
        this.terminalId = terminalId;
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

    public void setNumber(@NotNull @Min(0) @Max(50) Integer number) {
        this.number = number;
    }

    public @NotNull UUID getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(@NotNull UUID terminalId) {
        this.terminalId = terminalId;
    }

}
package com.example.airportproject.model;


import java.util.UUID;

public class Gate {
    private UUID id;
    private Integer number;
//    @ManyToOne
//    @JoinColumn(name = "terminal_id")
    private UUID terminalId;

    public Gate(Integer number, UUID terminalId) {
        this.number = number;
        this.terminalId = terminalId;
    }

    public Gate(UUID id, Integer number, UUID terminalId) {
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public UUID getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(UUID terminalId) {
        this.terminalId = terminalId;
    }

}
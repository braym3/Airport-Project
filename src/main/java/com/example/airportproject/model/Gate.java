package com.example.airportproject.model;


import java.util.UUID;

public class Gate {
    private UUID id;
    private Integer number;
//    @ManyToOne
//    @JoinColumn(name = "terminal_id")
    private Terminal terminal;

    public Gate() {

    }

    public Gate(Integer number, Terminal terminal) {
        this.number = number;
        this.terminal = terminal;
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

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

}
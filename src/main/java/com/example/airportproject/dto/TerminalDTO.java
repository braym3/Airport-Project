package com.example.airportproject.dto;

import java.util.List;
import java.util.UUID;

public class TerminalDTO {
    private UUID id;
    private Integer number;
    private List<GateDTO> gates;

    public TerminalDTO() {
    }

    public TerminalDTO(UUID id, Integer number, List<GateDTO> gates) {
        this.id = id;
        this.number = number;
        this.gates = gates;
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

    public List<GateDTO> getGates() {
        return gates;
    }

    public void setGates(List<GateDTO> gates) {
        this.gates = gates;
    }
}

package com.example.airportproject.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.UUID;

public class GateDTO {
    private UUID id;
    private Integer number;
    private Integer terminalNumber;

    @JsonView(Views.IncludeSchedule.class)
    private List<GateSlotDTO> schedule;

    public GateDTO() {
    }

    public GateDTO(UUID id, Integer number, Integer terminalNumber, List<GateSlotDTO> schedule) {
        this.id = id;
        this.number = number;
        this.terminalNumber = terminalNumber;
        this.schedule = schedule;
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
    public Integer getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(Integer terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public List<GateSlotDTO> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<GateSlotDTO> schedule) {
        this.schedule = schedule;
    }
}

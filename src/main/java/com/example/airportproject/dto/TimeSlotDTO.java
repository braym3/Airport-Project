package com.example.airportproject.dto;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.Gate;
import com.example.airportproject.model.ImpactEvent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class TimeSlotDTO {
    private UUID id;
    private LocalDateTime startTime, endTime;
    private Integer gateNumber;
    private Integer terminalNumber;
    private Integer runwayNumber;
    private FlightDTO flight;
    private ImpactEventDTO impactEvent;

    public TimeSlotDTO() {
    }

    public TimeSlotDTO(UUID id, LocalDateTime startTime, LocalDateTime endTime, Integer gateNumber, Integer terminalNumber, Integer runwayNumber, FlightDTO flight, ImpactEventDTO impactEvent) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gateNumber = gateNumber;
        this.terminalNumber = terminalNumber;
        this.runwayNumber = runwayNumber;
        this.flight = flight;
        this.impactEvent = impactEvent;
    }

    public TimeSlotDTO(LocalDateTime startTime, LocalDateTime endTime, Integer gateNumber, Integer terminalNumber, Integer runwayNumber, FlightDTO flight, ImpactEventDTO impactEvent) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.gateNumber = gateNumber;
        this.terminalNumber = terminalNumber;
        this.runwayNumber = runwayNumber;
        this.flight = flight;
        this.impactEvent = impactEvent;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(Integer gateNumber) {
        this.gateNumber = gateNumber;
    }

    public Integer getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(Integer terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public Integer getRunwayNumber() {
        return runwayNumber;
    }

    public void setRunwayNumber(Integer runwayNumber) {
        this.runwayNumber = runwayNumber;
    }

    public FlightDTO getFlight() {
        return flight;
    }

    public void setFlight(FlightDTO flight) {
        this.flight = flight;
    }

    public ImpactEventDTO getImpactEvent() {
        return impactEvent;
    }

    public void setImpactEvent(ImpactEventDTO impactEvent) {
        this.impactEvent = impactEvent;
    }
}

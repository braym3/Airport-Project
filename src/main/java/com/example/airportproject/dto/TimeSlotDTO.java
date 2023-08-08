package com.example.airportproject.dto;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.Gate;
import com.example.airportproject.model.ImpactEvent;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class TimeSlotDTO {
    private UUID id;
    private LocalDateTime startTime, endTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID gateId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer gateNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID terminalId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer terminalNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID runwayId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer runwayNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FlightDTO flight;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ImpactEventDTO impactEvent;

    public TimeSlotDTO() {
    }

    public TimeSlotDTO(UUID id, LocalDateTime startTime, LocalDateTime endTime, UUID gateId, Integer gateNumber, UUID terminalId, Integer terminalNumber, UUID runwayId, Integer runwayNumber, FlightDTO flight, ImpactEventDTO impactEvent) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gateId = gateId;
        this.gateNumber = gateNumber;
        this.terminalId = terminalId;
        this.terminalNumber = terminalNumber;
        this.runwayId = runwayId;
        this.runwayNumber = runwayNumber;
        this.flight = flight;
        this.impactEvent = impactEvent;
    }

    public TimeSlotDTO(LocalDateTime startTime, LocalDateTime endTime, UUID gateId, Integer gateNumber, UUID terminalId, Integer terminalNumber, UUID runwayId, Integer runwayNumber, FlightDTO flight, ImpactEventDTO impactEvent) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.gateId = gateId;
        this.gateNumber = gateNumber;
        this.terminalId = terminalId;
        this.terminalNumber = terminalNumber;
        this.runwayId = runwayId;
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

    public UUID getGateId() {
        return gateId;
    }

    public void setGateId(UUID gateId) {
        this.gateId = gateId;
    }

    public Integer getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(Integer gateNumber) {
        this.gateNumber = gateNumber;
    }

    public UUID getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(UUID terminalId) {
        this.terminalId = terminalId;
    }

    public Integer getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(Integer terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public UUID getRunwayId() {
        return runwayId;
    }

    public void setRunwayId(UUID runwayId) {
        this.runwayId = runwayId;
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

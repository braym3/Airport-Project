package com.example.airportproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.UUID;

public class GateSlotDTO {
    private UUID id;
    private LocalDateTime startTime, endTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer runwayNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FlightDTO flight;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ImpactEventDTO impactEvent;

    public GateSlotDTO() {
    }

    public GateSlotDTO(UUID id, LocalDateTime startTime, LocalDateTime endTime, Integer runwayNumber, FlightDTO flight, ImpactEventDTO impactEvent) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.runwayNumber = runwayNumber;
        this.flight = flight;
        this.impactEvent = impactEvent;
    }

    public GateSlotDTO(LocalDateTime startTime, LocalDateTime endTime, Integer runwayNumber, FlightDTO flight, ImpactEventDTO impactEvent) {
        this.startTime = startTime;
        this.endTime = endTime;
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

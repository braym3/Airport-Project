package com.example.airportproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
* Represents a time slot during which a flight occupies a gate.
*/
public class TimeSlot {

    private UUID id;
    @NotNull
    private LocalDateTime startTime, endTime;

    // time slot can be held by gate or runway
    private Gate gate;

    // time slot can be occupied by a flight (using a gate/runway), impact event (closing a gate/runway)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Flight flight;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ImpactEvent impactEvent;


    /**
     * Constructs a TimeSlot object with the specified ID, Gate object, Flight object, start time, end time, and ImpactEvent object.
     * @param id the ID of the time slot
     * @param gate the Gate object that the time slot belongs to
     * @param flight the Flight object occupying the time slot
     * @param startTime the start time of the time slot
     * @param endTime the end time of the time slot
     * @param impactEvent the ImpactEvent object occupying the time slot
     */
    public TimeSlot(UUID id, Gate gate, Flight flight, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, ImpactEvent impactEvent) {
        this.id = id;
        this.gate = gate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.flight = flight;
        this.impactEvent = impactEvent;
    }

    /**
     * Constructs a TimeSlot object with the specified Gate object, Flight object, start time, end time, and ImpactEvent object.
     * @param gate the Gate object that the time slot belongs to
     * @param flight the Flight object occupying the time slot
     * @param startTime the start time of the time slot
     * @param endTime the end time of the time slot
     * @param impactEvent the ImpactEvent object occupying the time slot
     */
    public TimeSlot(Gate gate, Flight flight, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, ImpactEvent impactEvent) {
        this.gate = gate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.flight = flight;
        this.impactEvent = impactEvent;
    }

    public TimeSlot(){
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**
    * Retrieves the start time of the time slot.
     * @return the start time
    */
    public @NotNull LocalDateTime getStartTime() {
        return startTime;
    }

    /**
    * Sets the start time of the time slot.
     * @param startTime the start time
    */
    public void setStartTime(@NotNull LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Retrieves the end time of the time slot.
     * @return the end time
     */
    public @NotNull LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the time slot.
     * @param endTime the end time
     */
    public void setEndTime(@NotNull LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Retrieves the Flight object occupying the time slot.
     * @return the Flight object
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the Flight object occupying the time slot.
     * @param flight the Flight object
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Retrieves the impact event object occupying the time slot.
     * @return the ImpactEvent object
     */
    public ImpactEvent getImpactEvent() {
        return impactEvent;
    }

    /**
     * Sets the impact event object occupying the time slot.
     * @param impactEvent the ImpactEvent object
     */
    public void setImpactEvent(ImpactEvent impactEvent) {
        this.impactEvent = impactEvent;
    }

    public boolean overlaps(LocalDateTime otherStartTime, LocalDateTime otherEndTime){
        return this.startTime.isBefore(otherEndTime) && otherStartTime.isBefore(this.endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(getId(), timeSlot.getId()) && Objects.equals(getStartTime(), timeSlot.getStartTime()) && Objects.equals(getEndTime(), timeSlot.getEndTime()) && Objects.equals(getGate(), timeSlot.getGate()) && Objects.equals(getFlight(), timeSlot.getFlight()) && Objects.equals(getImpactEvent(), timeSlot.getImpactEvent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartTime(), getEndTime(), getGate(), getFlight(), getImpactEvent());
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", gate= T" + gate.getTerminal().getNumber() + " gate " + gate.getNumber() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", flight=" + flight.getFlightIata() +
                ", impactEvent=" + impactEvent.getType() +
                '}';
    }
}

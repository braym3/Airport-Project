package com.example.airportproject.model;

import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
* Represents a time slot during which a flight or impact event occupies a gate or runway.
*/
public class TimeSlot {

    private UUID id;
    @NotNull
    private LocalDateTime startTime, endTime;

    // time slot can be held by gate or runway
    private Gate gate;
    private Runway runway;

    // time slot can be occupied by a flight (using a gate/runway), impact event (closing a gate/runway)
    private Flight flight;
    private ImpactEvent impactEvent;


    /**
     * Constructs a TimeSlot object for a Runway with the specified Runway object, start time, and end time.
     * @param runway the Runway object that the time slot belongs to (can be null if the time slot belongs to a gate)
     * @param startTime the start time of the time slot
     * @param endTime the end time of the time slot
     */
    public TimeSlot(Runway runway, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {
        this.gate = null;
        this.runway = runway;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructs a TimeSlot object for a Gate with the specified Gate object, start time, and end time.
     * @param gate the Gate object that the time slot belongs to (can be null if the time slot belongs to a runway)
     * @param startTime the start time of the time slot
     * @param endTime the end time of the time slot
     */
    public TimeSlot(Gate gate, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {
        this.gate = gate;
        this.runway = null;
        this.startTime = startTime;
        this.endTime = endTime;
    }

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
     * Constructs a TimeSlot object with the specified ID, Runway object, Flight object, start time, end time, and ImpactEvent object.
     * @param id the ID of the time slot
     * @param runway the Runway object that the time slot belongs to
     * @param flight the Flight object occupying the time slot
     * @param startTime the start time of the time slot
     * @param endTime the end time of the time slot
     * @param impactEvent the ImpactEvent object occupying the time slot
     */
    public TimeSlot(UUID id, Runway runway, Flight flight, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, ImpactEvent impactEvent) {
        this.id = id;
        this.gate = null;
        this.runway = runway;
        this.startTime = startTime;
        this.endTime = endTime;
        this.flight = flight;
        this.impactEvent = impactEvent;
    }

  /**
   * Constructs a complete TimeSlot object with the specified ID, Gate object, Flight object, start time, end time, ImpactEvent object, and Runway object.
   * @param id the ID of the time slot
   * @param gate the Gate object that the time slot belongs to
   * @param flight the Flight object occupying the time slot
   * @param startTime the start time of the time slot
   * @param endTime the end time of the time slot
   * @param impactEvent the ImpactEvent object occupying the time slot
   * @param runway the Runway object that the time slot belongs to
   */
  public TimeSlot(
      UUID id,
      Gate gate,
      Flight flight,
      @NotNull LocalDateTime startTime,
      @NotNull LocalDateTime endTime,
      ImpactEvent impactEvent,
      Runway runway) {
        this.id = id;
        this.gate = gate;
        this.flight = flight;
        this.startTime = startTime;
        this.endTime = endTime;
        this.impactEvent = impactEvent;
        this.runway = runway;
    }

    /**
     * Constructs a TimeSlot object with the specified Gate object, Flight object, start time, end time, and ImpactEvent object.
     * @param gate the Gate object that the time slot belongs to
     * @param flight the Flight object occupying the time slot
     * @param startTime the start time of the time slot
     * @param endTime the end time of the time slot
     * @param impactEvent the ImpactEvent object occupying the time slot
     */
    public TimeSlot(Gate gate, Flight flight, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, ImpactEvent impactEvent, Runway runway) {
        this.id = UUID.randomUUID();
        this.gate = gate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.flight = flight;
        this.impactEvent = impactEvent;
        this.runway = runway;
    }

    public TimeSlot(){
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public Runway getRunway() {
        return runway;
    }

    public void setRunway(Runway runway) {
        this.runway = runway;
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

    public Duration getTimeSlotDuration(){
        return Duration.between(this.startTime, this.endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(getId(), timeSlot.getId()) && Objects.equals(getStartTime(), timeSlot.getStartTime()) && Objects.equals(getEndTime(), timeSlot.getEndTime()) && Objects.equals(getGate(), timeSlot.getGate()) && Objects.equals(getFlight(), timeSlot.getFlight()) && Objects.equals(getImpactEvent(), timeSlot.getImpactEvent());
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                (gate != null ? ", gate= T" + gate.getTerminal().getNumber() + " gate " + gate.getNumber() : "") +
                (runway != null ? ", runway= " + runway.getNumber() : "") +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                (flight != null ? ", flight=" + flight.getFlightIata() : "") +
                (impactEvent != null ? ", impactEvent=" + impactEvent.getType() : "") +
                '}';
    }
}

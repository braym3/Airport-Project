package com.example.airportproject.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
* Represents a time slot during which a flight occupies a gate.
*/
public class TimeSlot {

    private UUID id;
    @NotNull
    private LocalDateTime startTime, endTime;
    // time slot can be occupied by a flight (using a gate or runway)
    private UUID flightId;


    /**
     * Constructs a TimeSlot object with the specified ID, flight ID, start time, and end time.
     * @param id the ID of the time slot
     * @param flightId the ID of the flight occupying the time slot
     * @param startTime the start time of the time slot
     * @param endTime the end time of the time slot
     */
    public TimeSlot(UUID id, UUID flightId, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.flightId = flightId;
    }

    /**
    * Constructs a TimeSlot object with the specified start time, end time, and flight ID.
     * @param flightId the ID of the flight occupying the time slot
     * @param startTime the start time of the time slot
     * @param endTime the end time of the time slot
    */
    public TimeSlot(UUID flightId, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.flightId = flightId;
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
     * Retrieves the ID of the flight occupying the time slot.
     * @return the flight ID
     */
    public UUID getFlightId() {
        return flightId;
    }

    /**
     * Sets the ID of the flight occupying the time slot.
     * @param flightId the flight ID
     */
    public void setFlightId(UUID flightId) {
        this.flightId = flightId;
    }

    public boolean overlaps(LocalDateTime otherStartTime, LocalDateTime otherEndTime){
        return this.startTime.isBefore(otherEndTime) && otherStartTime.isBefore(this.endTime);
    }
}

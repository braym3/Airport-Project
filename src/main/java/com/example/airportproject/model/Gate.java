package com.example.airportproject.model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.*;

/**
* Represents a gate in an airport
*/
public class Gate {
    private UUID id;
    @NotNull
    @Min(0)
    @Max(50)
    private Integer number;
    @NotNull
    private UUID terminalId;

    private List<TimeSlot> schedule;

    /**
     * Constructs a new Gate object with the specified ID and gate number.
     * @param id the gate ID
     * @param number the gate number
     */
    public Gate(UUID id, @NotNull Integer number) {
        this.id = id;
        this.number = number;
        this.schedule = new ArrayList<>();
    }

    /**
    * Constructs a new Gate object with the specified gate number and terminal ID.
     * @param number the gate number
     * @param terminalId the ID of the Terminal that the Gate belongs to
    */
    public Gate(@NotNull Integer number, @NotNull UUID terminalId) {
        this.number = number;
        this.terminalId = terminalId;
        this.schedule = new ArrayList<>();
    }

    /**
     * Constructs a new Gate object with the specified gate number, terminal ID and schedule.
     * @param number the gate number
     * @param terminalId the ID of the Terminal that the Gate belongs to
     * @param schedule a List of time slots for when the gate is occupied
     */
    public Gate(@NotNull Integer number, @NotNull UUID terminalId, List<TimeSlot> schedule) {
        this.number = number;
        this.terminalId = terminalId;
        this.schedule = schedule;
    }

    /**
     * Constructs a new Gate object with the specified ID, gate number, and terminal ID.
     * @param id the unique ID of the gate
     * @param number the gate number
     * @param terminalId the ID of the Terminal that the Gate belongs to
     */
    public Gate(UUID id, @NotNull Integer number, @NotNull UUID terminalId) {
        this.id = id;
        this.number = number;
        this.terminalId = terminalId;
        this.schedule = new ArrayList<>();
    }

    /**
    * Returns the ID of the gate.
     * @return the ID of the gate
    */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the ID of the gate.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns the gate number.
     * @return the gate number
     */
    public @NotNull Integer getNumber() {
        return number;
    }

    /**
    * Sets the gate number.
     * @param number the gate number
    */
    public void setNumber(@NotNull @Min(0) @Max(50) Integer number) {
        this.number = number;
    }

    /**
    * Returns the ID of the Terminal that the gate belongs to.
     * @return the ID of the associated terminal
    */
    public @NotNull UUID getTerminalId() {
        return terminalId;
    }

    /**
    * Sets the ID of the terminal that the gate belongs to.
     * @param terminalId the ID of the associated terminal
    */
    public void setTerminalId(@NotNull UUID terminalId) {
        this.terminalId = terminalId;
    }

    public List<TimeSlot> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<TimeSlot> schedule) {
        this.schedule = schedule;
    }

    public void addTimeSlot(TimeSlot timeSlot){
        this.schedule.add(timeSlot);
    }
}
package com.example.airportproject.model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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
    private Terminal terminal;

    private List<TimeSlot> schedule;

    /**
     * Constructs a new Gate object with the specified ID, gate number.
     * @param id the gate ID
     * @param number the gate number
     */
    public Gate(UUID id, @NotNull Integer number) {
        this.id = id;
        this.number = number;
        this.terminal = null;
        this.schedule = new ArrayList<>();
    }

    /**
     * Constructs a new Gate object with the specified ID, gate number, and terminal.
     * @param id the gate ID
     * @param number the gate number
     * @param terminal the Terminal object that this gate belongs to
     */
    public Gate(UUID id, @NotNull Integer number, @NotNull Terminal terminal) {
        this.id = id;
        this.number = number;
        this.terminal = terminal;
        this.schedule = new ArrayList<>();
    }

    /**
    * Constructs a new Gate object with the specified gate number and terminal.
     * @param number the gate number
     * @param terminal the Terminal object that the Gate belongs to
    */
    public Gate(@NotNull Integer number, @NotNull Terminal terminal) {
        this.number = number;
        this.terminal = terminal;
        this.schedule = new ArrayList<>();
    }

    /**
     * Constructs a new Gate object with the specified gate number, terminal and schedule.
     * @param number the gate number
     * @param terminal the Terminal object that the Gate belongs to
     * @param schedule a List of time slots for when the gate is occupied
     */
    public Gate(@NotNull Integer number, @NotNull Terminal terminal, List<TimeSlot> schedule) {
        this.number = number;
        this.terminal = terminal;
        this.schedule = schedule;
    }

    /**
     * Constructs a new Gate object with the specified ID, gate number, terminal and schedule.
     * @param id the gate ID
     * @param number the gate number
     * @param terminal the Terminal object that the Gate belongs to
     * @param schedule a List of time slots for when the gate is occupied
     */
    public Gate(UUID id, @NotNull Integer number, @NotNull Terminal terminal, List<TimeSlot> schedule) {
        this.id = id;
        this.number = number;
        this.terminal = terminal;
        this.schedule = schedule;
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
    * Returns the Terminal object that the gate belongs to.
     * @return the associated Terminal object
    */
    public @NotNull Terminal getTerminal() {
        return terminal;
    }

    /**
    * Sets the Terminal object that the gate belongs to.
     * @param terminal the associated Terminal object
    */
    public void setTerminal(@NotNull Terminal terminal) {
        this.terminal = terminal;
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
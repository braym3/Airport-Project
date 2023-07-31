package com.example.airportproject.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.*;

/**
* Represents a gate in an airport
*/
public class Gate implements Schedulable{
    private UUID id;
    @Min(0)
    @Max(50)
    private int number;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Terminal terminal;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TimeSlot> schedule;

    /**
     * Constructs a new Gate object with the specified ID, gate number.
     * @param id the gate ID
     * @param number the gate number
     */
    public Gate(UUID id, int number) {
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
    public Gate(UUID id, int number, @NotNull Terminal terminal) {
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
    public Gate(int number, @NotNull Terminal terminal) {
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
    public Gate(int number, @NotNull Terminal terminal, List<TimeSlot> schedule) {
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
    public Gate(UUID id, int number, @NotNull Terminal terminal, List<TimeSlot> schedule) {
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
    public int getNumber() {
        return number;
    }

    /**
    * Sets the gate number.
     * @param number the gate number
    */
    public void setNumber(@Min(0) @Max(50) int number) {
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
        return new ArrayList<>(schedule);
    }

    public void setSchedule(List<TimeSlot> schedule) {
        this.schedule = schedule;
    }

    public void addTimeSlot(TimeSlot timeSlot){
        this.schedule.add(timeSlot);
    }

    @Override
    public TimeSlot findClosestAvailableSlot(LocalDateTime startTime, LocalDateTime endTime) {
        return null;
    }

    @Override
    public List<TimeSlot> getScheduleOfAvailability(LocalDateTime scheduleStartTime, LocalDateTime scheduleEndTime) {
        // copy of gate schedule
        List<TimeSlot> occupiedSchedule = new ArrayList<>(schedule);
        List<TimeSlot> availableTimes = new ArrayList<>();

        // if the schedule is empty add a slot of availability for the whole duration
        if (occupiedSchedule.isEmpty()) {
            availableTimes.add(new TimeSlot(this, scheduleStartTime, scheduleEndTime));
        }else{
            // check if the schedule is ordered -- print times
            System.out.println("\nNEW GATE:");
            occupiedSchedule.forEach(timeSlot -> System.out.println("\nStart time: " + timeSlot.getStartTime() + ", End time: " + timeSlot.getEndTime()));

            // check time between schedule start time and the first occupied slot
            TimeSlot firstSlot = occupiedSchedule.get(0);
            if(!scheduleStartTime.isEqual(firstSlot.getStartTime())){
                availableTimes.add(new TimeSlot(this, scheduleStartTime, firstSlot.getStartTime()));
            }

            // calculate available slots between the occupied slots
            for(int i = 0; i < occupiedSchedule.size() - 1; i++){
                TimeSlot currentSlot = occupiedSchedule.get(i);
                TimeSlot nextSlot = occupiedSchedule.get(i + 1);

                if(!currentSlot.getEndTime().isEqual(nextSlot.getStartTime())){
                    availableTimes.add(new TimeSlot(this, currentSlot.getEndTime(), nextSlot.getStartTime()));
                }
            }

            // check time between the last occupied slot and the schedule end time
            TimeSlot lastSlot = occupiedSchedule.get(occupiedSchedule.size()-1);
            if(!scheduleEndTime.isEqual(lastSlot.getEndTime())){
                availableTimes.add(new TimeSlot(this, lastSlot.getEndTime(), scheduleEndTime));
            }
        }
        return availableTimes;
    }

    public boolean isAvailableAtTimeSlot(LocalDateTime startTime, LocalDateTime endTime){
        for(TimeSlot timeSlot : this.schedule){
            if(timeSlot.overlaps(startTime, endTime)){
                return false;
            }
        }
        return true;
    }

    public void printSchedule(){
        System.out.println(getSchedule().toString());
    }

    @Override
    public String toString() {
        return "Gate{" +
                "id=" + id +
                ", number=" + number +
                ", terminal=" + terminal.toString() +
                ", schedule=" + schedule.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gate gate = (Gate) o;
        return Objects.equals(getId(), gate.getId()) && Objects.equals(getNumber(), gate.getNumber()) && Objects.equals(getTerminal(), gate.getTerminal()) && Objects.equals(getSchedule(), gate.getSchedule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getTerminal(), getSchedule());
    }
}
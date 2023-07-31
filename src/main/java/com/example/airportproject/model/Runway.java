package com.example.airportproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Runway implements Schedulable{
    private UUID id;
    @Min(0)
    @Max(50)
    private int number;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TimeSlot> schedule;

    public Runway(int number, List<TimeSlot> schedule) {
        this.number = number;
        this.schedule = schedule;
    }

    public Runway(UUID id, int number, List<TimeSlot> schedule) {
        this.id = id;
        this.number = number;
        this.schedule = schedule;
    }

    /**
     * Constructs a new Runway object with the specified ID, runway number.
     * @param id the runway ID
     * @param number the runway number
     */
    public Runway(UUID id, int number) {
        this.id = id;
        this.number = number;
        this.schedule = new ArrayList<>();
    }

    /**
     * Constructs a new Runway object with the specified runway number.
     * @param number the runway number
     */
    public Runway(int number) {
        this.number = number;
        this.schedule = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<TimeSlot> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<TimeSlot> schedule) {
        this.schedule = schedule;
    }

    public boolean isAvailableAtTimeSlot(LocalDateTime startTime, LocalDateTime endTime){
        for(TimeSlot timeSlot : this.schedule){
            if(timeSlot.overlaps(startTime, endTime)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void addTimeSlot(TimeSlot timeSlot) {

    }

    public TimeSlot findClosestAvailableSlot(LocalDateTime startTime, LocalDateTime endTime){
        // keep track of the minimum difference between the original slot time and the chosen slot time
//        Duration minDifference;
//        for(TimeSlot timeSlot : this.schedule){
//            if()
//        }
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
            System.out.println("\nNEW RUNWAY:");
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

    public void printSchedule(){
        System.out.println(getSchedule().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Runway runway = (Runway) o;
        return getNumber() == runway.getNumber() && Objects.equals(getId(), runway.getId()) && Objects.equals(getSchedule(), runway.getSchedule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getSchedule());
    }

    @Override
    public String toString() {
        return "Runway{" +
                "id=" + id +
                ", number=" + number +
                ", schedule=" + schedule.toString() +
                '}';
    }
}

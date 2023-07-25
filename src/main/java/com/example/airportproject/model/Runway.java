package com.example.airportproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Runway {
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
                ", schedule=" + schedule +
                '}';
    }
}

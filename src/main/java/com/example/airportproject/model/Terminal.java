package com.example.airportproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
* Represents a terminal in an airport
*/
public class Terminal {
    private UUID id;

    @NotNull
    @Min(0)
    @Max(10)
    private Integer number;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Gate> gates;

    /**
    * Constructs a new Terminal object with the specified terminal number.
     * @param number the terminal number
    */
    public Terminal(@NotNull Integer number){
        this.number = number;
        this.gates = new ArrayList<>();
    }

    /**
    * Constructs a new Terminal object with the specified ID and terminal number.
     * @param id the unique ID of the terminal
     * @param number the terminal number
    */
    public Terminal(UUID id, @NotNull Integer number){
        this.id = id;
        this.number = number;
        this.gates = new ArrayList<>();
    }

    /**
     * Constructs a new Terminal object with the specified ID, terminal number, and List of Gates belonging to it.
     * @param id the unique ID of the terminal
     * @param number the terminal number
     * @param gates the List of Gates that belong to the terminal
     */
    public Terminal(UUID id, @NotNull Integer number, List<Gate> gates){
        this.id = id;
        this.number = number;
        this.gates = gates;
    }

    /**
    * Returns the ID of the terminal.
     * @return the ID of the terminal
    */
    public UUID getId() {
        return id;
    }

    /**
    * Sets the ID of the terminal.
     * @param id the ID of the terminal
    */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns the terminal number.
     * @return the terminal number
     */
    public @NotNull Integer getNumber() {
        return number;
    }


    /**
    * Sets the terminal number.
     * @param number the terminal number
    */
    public void setNumber(@NotNull @Min(0) @Max(10) Integer number) {
        this.number = number;
    }

    /**
    * Returns the List of Gates associated with the terminal.
     * @return the List of associated gates
    */
    public List<Gate> getGates() {
        return gates;
    }

    /**
    * Sets the List of Gates associated with the terminal.
     * @param gates the List of associated gates
    */
    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }
}
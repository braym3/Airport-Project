package com.example.airportproject.service.gates;

import com.example.airportproject.model.Gate;

import java.util.List;
import java.util.UUID;

/**
 * Defines the contract for managing gates.
 * It provides operations to fetch gates, add a new gates, remove gates, and update a gate.
 */
public interface GateService {

    /**
     * Creates a new record in the database with the data from a Gate object
     * @param gate The Gate to persist
     * @return The created Gate
     */
    Gate createGate(Gate gate);

    /**
     * Returns the Gate with the given id
     * @param id The id of the gate to find
     * @return Gate object with the matching id
     */
    Gate get(UUID id);

    /**
     * Returns a List of all Gates in the database
     * @return List of all Gate objects found
     */
    List<Gate> getAll();

    /**
     * Removes the Gate with the corresponding id from the database
     * @param id The id used to identify the Gate to delete
     * @return The deleted Gate object
     */
    Gate remove(UUID id);

    /**
     * Updates the number of the gate in the database that corresponds to the provided id
     * @param id The id used to identify the Gate to update
     * @param number The new number of the gate
     * @return The updated Gate object
     */
    Gate updateNumber(UUID id, int number);
}
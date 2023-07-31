package com.example.airportproject.service.gates;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.Terminal;
import com.example.airportproject.model.TimeSlot;

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
     * Updates the gate record of the corresponding id with the specified attributes
     * @param id The id used to identify the Gate to update
     * @param number The number of the gate
     * @param terminal The Terminal object that the gate is associated with
     * @param schedule The List of TimeSlot objects that represent the gate's schedule of occupied times
     * @return The updated Gate object
     */
    Gate update(UUID id, int number, Terminal terminal, List<TimeSlot> schedule);

    void addGateSlot(TimeSlot timeSlot);

    TimeSlot getGateTimeSlotByFlightId(UUID flightId);

    List<TimeSlot> getScheduleForGate(UUID gateId);

    List<TimeSlot> getAllGatesTimeSlots();

    void removeTimeSlotForGate(UUID timeSlotId);

    TimeSlot removeGateTimeSlotByFlightId(UUID flightId);
}
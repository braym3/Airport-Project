package com.example.airportproject.repository;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.TimeSlot;

import java.util.List;
import java.util.UUID;


public interface GateRepo{
    Gate create(Gate gate);

    List<Gate> getAll();

    Gate get(UUID id);

    // gate - updated gate object to save the updated values of
    Gate update(Gate gate);

    Gate remove(UUID id);

    // clear table
    void clear();

    void addGateSlot(TimeSlot timeSlot);

    TimeSlot getGateTimeSlotByFlightId(UUID flightId);

    void removeTimeSlotForGate(UUID timeSlotId);
}

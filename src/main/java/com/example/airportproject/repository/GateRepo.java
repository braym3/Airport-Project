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

    void remove(UUID id);

    // clear table
    void clear();

    void addGateSlot(TimeSlot timeSlot);

    TimeSlot getGateTimeSlotByFlightId(UUID flightId);

    List<TimeSlot> getScheduleForGate(UUID gateId);

    List<TimeSlot> getAllGatesTimeSlots();

    void removeTimeSlotForGate(UUID timeSlotId);

    void removeGateTimeSlotByFlightId(UUID flightId);
}

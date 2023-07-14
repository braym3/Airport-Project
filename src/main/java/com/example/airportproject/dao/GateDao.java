package com.example.airportproject.dao;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.TimeSlot;

import java.util.List;
import java.util.UUID;

public interface GateDao {
    List<Gate> getAll();

    Gate get(UUID id);

    Gate create(Gate gate);

    Gate update(Gate gate);

    Gate remove(UUID id);

    void addGateSlot(TimeSlot timeSlot);

    TimeSlot getGateTimeSlotByFlightId(UUID flightId);

    List<TimeSlot> getScheduleForGate(UUID gateId);

    List<TimeSlot> getAllGatesTimeSlots();

    void removeTimeSlotForGate(UUID timeSlotId);
}

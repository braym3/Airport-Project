package com.example.airportproject.dao;

import com.example.airportproject.model.Runway;
import com.example.airportproject.model.TimeSlot;

import java.util.List;
import java.util.UUID;

public interface RunwayDao {
    List<Runway> getAll();

    Runway get(UUID id);

    Runway create(Runway runway);

    Runway update(Runway runway);

    Runway remove(UUID id);

    void addRunwaySlot(TimeSlot timeSlot);

    TimeSlot getRunwayTimeSlotByFlightId(UUID flightId);

    List<TimeSlot> getScheduleForRunway(UUID runwayId);

    List<TimeSlot> getAllRunwayTimeSlots();

    void removeTimeSlotForRunway(UUID timeSlotId);
}

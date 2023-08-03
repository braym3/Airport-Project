package com.example.airportproject.repository;

import com.example.airportproject.model.Runway;
import com.example.airportproject.model.TimeSlot;

import java.util.List;
import java.util.UUID;

public interface RunwayRepo {
    Runway create(Runway runway);

    List<Runway> getAll();

    Runway get(UUID id);

    Runway update(Runway runway);

    Runway remove(UUID id);

    void clear();

    void addRunwaySlot(TimeSlot timeSlot);

    TimeSlot getRunwayTimeSlotByFlightId(UUID flightId);

    List<TimeSlot> getScheduleForRunway(UUID runwayId);

    List<TimeSlot> getAllRunwayTimeSlots();

    void removeTimeSlotForRunway(UUID timeSlotId);

    TimeSlot removeRunwayTimeSlotByFlightId(UUID flightId);

    void clearRunwayTimeSlots();
}

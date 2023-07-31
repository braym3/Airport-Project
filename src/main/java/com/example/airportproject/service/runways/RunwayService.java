package com.example.airportproject.service.runways;

import com.example.airportproject.model.Runway;
import com.example.airportproject.model.TimeSlot;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface RunwayService {
    @Transactional
    Runway createRunway(Runway runway);

    @Transactional
    Runway get(UUID id);

    @Transactional
    List<Runway> getAll();

    @Transactional
    Runway remove(UUID id);

    @Transactional
    Runway update(UUID id, int number, List<TimeSlot> schedule);

    @Transactional
    void addRunwaySlot(TimeSlot timeSlot);

    TimeSlot getRunwayTimeSlotByFlightId(UUID flightId);

    List<TimeSlot> getScheduleForRunway(UUID runwayId);

    List<TimeSlot> getAllRunwayTimeSlots();

    void removeTimeSlotForRunway(UUID timeSlotId);

    TimeSlot removeRunwayTimeSlotByFlightId(UUID flightId);
}

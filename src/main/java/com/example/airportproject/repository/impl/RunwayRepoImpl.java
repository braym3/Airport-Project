package com.example.airportproject.repository.impl;

import com.example.airportproject.model.Runway;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.repository.RunwayRepo;
import com.example.airportproject.repository.impl.mapper.RunwayMapper;
import com.example.airportproject.repository.impl.mapper.TimeSlotMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RunwayRepoImpl implements RunwayRepo {
    private final RunwayMapper runwayMapper;
    private final TimeSlotMapper timeSlotMapper;

    public RunwayRepoImpl(RunwayMapper runwayMapper, TimeSlotMapper timeSlotMapper){
        this.runwayMapper = runwayMapper;
        this.timeSlotMapper = timeSlotMapper;
    }

    @Override
    public Runway create(Runway runway) {
        runwayMapper.create(runway);
        return runway;
    }

    @Override
    public List<Runway> getAll() {
        return runwayMapper.getAll();
    }

    @Override
    public Runway get(UUID id) {
        return runwayMapper.get(id);
    }

    @Override
    public Runway update(Runway runway) {
        return runwayMapper.update(runway);
    }

    @Override
    public void remove(UUID id) {
        runwayMapper.remove(id);
    }

    @Override
    public void clear() {
        runwayMapper.clear();
    }

    @Override
    public void addRunwaySlot(TimeSlot timeSlot){
        timeSlotMapper.createTimeSlot(timeSlot);
    }

    @Override
    public TimeSlot getRunwayTimeSlotByFlightId(UUID flightId) {
        return timeSlotMapper.getRunwayTimeSlotByFlightId(flightId);
    }

    @Override
    public List<TimeSlot> getScheduleForRunway(UUID runwayId) {
        return timeSlotMapper.selectTimeSlotsForRunway(runwayId);
    }

    @Override
    public List<TimeSlot> getAllRunwayTimeSlots() {
        return timeSlotMapper.selectAllRunwayTimeSlots();
    }

    @Override
    public void removeTimeSlotForRunway(UUID timeSlotId){
        timeSlotMapper.removeTimeSlot(timeSlotId);
    }

    @Override
    public void removeRunwayTimeSlotByFlightId(UUID flightId) {
        timeSlotMapper.removeRunwayTimeSlotByFlightId(flightId);
    }
}

package com.example.airportproject.dao.impl;

import com.example.airportproject.dao.RunwayDao;
import com.example.airportproject.model.Runway;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.repository.RunwayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RunwayDaoImpl implements RunwayDao {
    private final RunwayRepo runwayRepo;

    @Autowired
    public RunwayDaoImpl(RunwayRepo runwayRepo) {
        this.runwayRepo = runwayRepo;
    }

    @Override
    public List<Runway> getAll() {
        return runwayRepo.getAll();
    }

    @Override
    public Runway get(UUID id) {
        return runwayRepo.get(id);
    }

    @Override
    public Runway create(Runway runway) {
        return runwayRepo.create(runway);
    }

    @Override
    public Runway update(Runway runway) {
        return runwayRepo.update(runway);
    }

    @Override
    public Runway remove(UUID id) {
        return runwayRepo.remove(id);
    }

    @Override
    public void addRunwaySlot(TimeSlot timeSlot) {
        runwayRepo.addRunwaySlot(timeSlot);
    }

    @Override
    public TimeSlot getRunwayTimeSlotByFlightId(UUID flightId) {
        return runwayRepo.getRunwayTimeSlotByFlightId(flightId);
    }

    @Override
    public List<TimeSlot> getScheduleForRunway(UUID runwayId) {
        return runwayRepo.getScheduleForRunway(runwayId);
    }

    @Override
    public List<TimeSlot> getAllRunwayTimeSlots() {
        return runwayRepo.getAllRunwayTimeSlots();
    }

    @Override
    public void removeTimeSlotForRunway(UUID timeSlotId) {
        runwayRepo.removeTimeSlotForRunway(timeSlotId);
    }

    @Override
    public TimeSlot removeRunwayTimeSlotByFlightId(UUID flightId) {
        return runwayRepo.removeRunwayTimeSlotByFlightId(flightId);
    }
}

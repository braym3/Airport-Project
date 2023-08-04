package com.example.airportproject.dao.impl;

import com.example.airportproject.dao.GateDao;
import com.example.airportproject.model.Gate;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.repository.GateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class GateDaoImpl implements GateDao {
    private final GateRepo gateRepo;

    @Autowired
    public GateDaoImpl(GateRepo gateRepo) {
        this.gateRepo = gateRepo;
    }

    @Override
    public List<Gate> getAll() {
        return gateRepo.getAll();
    }

    @Override
    public Gate get(UUID id) {
        return gateRepo.get(id);
    }

    @Override
    public Gate create(Gate gate) {
        return gateRepo.create(gate);
    }

    @Override
    public Gate update(Gate gate) {
        return gateRepo.update(gate);
    }

    @Override
    public void remove(UUID id) {
        gateRepo.remove(id);
    }

    @Override
    public void addGateSlot(TimeSlot timeSlot) {
        gateRepo.addGateSlot(timeSlot);
    }

    @Override
    public TimeSlot getGateTimeSlotByFlightId(UUID flightId) {
        return gateRepo.getGateTimeSlotByFlightId(flightId);
    }

    @Override
    public List<TimeSlot> getScheduleForGate(UUID gateId) {
        return gateRepo.getScheduleForGate(gateId);
    }

    @Override
    public List<TimeSlot> getAllGatesTimeSlots() {
        return gateRepo.getAllGatesTimeSlots();
    }


    @Override
    public void removeTimeSlotForGate(UUID timeSlotId) {
        gateRepo.removeTimeSlotForGate(timeSlotId);
    }

    @Override
    public void removeGateTimeSlotByFlightId(UUID flightId) {
        gateRepo.removeGateTimeSlotByFlightId(flightId);
    }
}

package com.example.airportproject.repository.impl;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.repository.GateRepo;
import com.example.airportproject.repository.impl.mapper.GateMapper;
import com.example.airportproject.repository.impl.mapper.TimeSlotMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class GateRepoImpl implements GateRepo {
    private final GateMapper gateMapper;
    private final TimeSlotMapper timeSlotMapper;

    public GateRepoImpl(GateMapper gateMapper, TimeSlotMapper timeSlotMapper){
        this.gateMapper = gateMapper;
        this.timeSlotMapper = timeSlotMapper;
    }

    @Override
    public Gate create(Gate gate) {
        gateMapper.create(gate);
        return gate;
    }

    @Override
    public List<Gate> getAll() {
        return gateMapper.getAll();
    }

    @Override
    public Gate get(UUID id) {
        return gateMapper.get(id);
    }

    @Override
    public Gate update(Gate gate) {
        return gateMapper.update(gate);
    }

    @Override
    public Gate remove(UUID id) {
        return gateMapper.remove(id);
    }

    @Override
    public void clear() {
        gateMapper.clear();
    }

    @Override
    public void addGateSlot(TimeSlot timeSlot){
        timeSlotMapper.createTimeSlotForGate(timeSlot);
    }

    @Override
    public TimeSlot getGateTimeSlotByFlightId(UUID flightId) {
        return timeSlotMapper.getGateTimeSlotByFlightId(flightId);
    }

    @Override
    public List<TimeSlot> getScheduleForGate(UUID gateId) {
        return timeSlotMapper.selectTimeSlotsForGate(gateId);
    }

    @Override
    public List<TimeSlot> getAllGatesTimeSlots() {
        return timeSlotMapper.selectAllGatesTimeSlots();
    }

    @Override
    public void removeTimeSlotForGate(UUID timeSlotId){
        timeSlotMapper.removeTimeSlotForGate(timeSlotId);
    }
}

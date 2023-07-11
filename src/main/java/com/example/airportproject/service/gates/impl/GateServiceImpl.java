package com.example.airportproject.service.gates.impl;

import com.example.airportproject.dao.GateDao;
import com.example.airportproject.model.Gate;
import com.example.airportproject.model.Terminal;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.repository.GateRepo;
import com.example.airportproject.service.gates.GateService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Primary
@Service
public class GateServiceImpl implements GateService {
    private final GateDao gateDao;

    public GateServiceImpl(GateDao gateDao) {
        this.gateDao = gateDao;
    }

    @Override
    @Transactional
    public Gate createGate(Gate gate) {
        return gateDao.create(gate);
    }



    @Override
    @Transactional
    public Gate get(UUID id) {
        return gateDao.get(id); //.orElseThrow(GateNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    @Override
    @Transactional
    public List<Gate> getAll() {
        return gateDao.getAll();
    }

    @Override
    @Transactional
    public Gate remove(UUID id) {
        Gate removed = this.get(id);
        gateDao.remove(id);
        return removed;
    }

    @Override
    @Transactional
    public Gate update(UUID id, int number, Terminal terminal, List<TimeSlot> schedule) {
        Gate gate = this.get(id);

        // update the gate attributes if the corresponding parameters are provided
        if(number != gate.getNumber()){
            gate.setNumber(number);
        }
        if(terminal != null){
            gate.setTerminal(terminal);
        }
        if(schedule != null){
            gate.setSchedule(schedule);
        }
        // save and return the updated gate record
        return gateDao.update(gate);               // save the updated Gate and return it
    }

    @Override
    @Transactional
    public void addGateSlot(TimeSlot timeSlot){
        gateDao.addGateSlot(timeSlot);
    }

    @Override
    public TimeSlot getGateTimeSlotByFlightId(UUID flightId) {
        return gateDao.getGateTimeSlotByFlightId(flightId);
    }

    @Override
    public void removeTimeSlotForGate(UUID timeSlotId){
        gateDao.removeTimeSlotForGate(timeSlotId);
    }
}

package com.example.airportproject.service.gates.impl;

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
    private final GateRepo gateRepo;

    public GateServiceImpl(GateRepo gateRepo) {
        this.gateRepo = gateRepo;
    }

    @Override
    @Transactional
    public Gate createGate(Gate gate) {
        return gateRepo.create(gate);
    }



    @Override
    @Transactional
    public Gate get(UUID id) {
        return gateRepo.get(id); //.orElseThrow(GateNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    @Override
    @Transactional
    public List<Gate> getAll() {
        return gateRepo.getAll();
    }

    @Override
    @Transactional
    public Gate remove(UUID id) {
        Gate removed = this.get(id);
        gateRepo.remove(id);
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
        return gateRepo.update(gate);               // save the updated Gate and return it
    }

    @Override
    @Transactional
    public void addGateSlot(TimeSlot timeSlot){
        gateRepo.addGateSlot(timeSlot);
    }

    @Override
    public TimeSlot getGateTimeSlotByFlightId(UUID flightId) {
        return gateRepo.getGateTimeSlotByFlightId(flightId);
    }

    @Override
    public void removeTimeSlotForGate(UUID timeSlotId){
        gateRepo.removeTimeSlotForGate(timeSlotId);
    }
}

package com.example.airportproject.service.gates.impl;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.repository.GateRepo;
import com.example.airportproject.service.gates.GateService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
    public Gate createGate(Gate gate) {
        return gateRepo.create(gate);
    }



    @Override
    public Gate get(UUID id) {
        return gateRepo.get(id); //.orElseThrow(GateNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    @Override
    public List<Gate> getAll() {
        return gateRepo.getAll();
    }

    @Override
    public Gate remove(UUID id) {
        Gate removed = this.get(id);
        gateRepo.remove(id);
        return removed;
    }

    @Override
    public Gate updateNumber(UUID id, int number) {
        Gate gate = this.get(id);

        gate.setNumber(number);

        return gateRepo.update(gate);               // save the updated Gate and return it
    }

    @Override
    public TimeSlot addGateSlot(UUID gateId, TimeSlot timeSlot){
        gateRepo.addGateSlot(gateId, timeSlot);
        return timeSlot;
    }

}

package com.example.airportproject.service.gates;

import com.example.airportproject.exception.GateNotFoundException;
import com.example.airportproject.model.Gate;
import com.example.airportproject.repository.GateRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Primary
@Service
public class GateServiceImpl implements GateService{
    private final GateRepository gateRepo;

    public GateServiceImpl(GateRepository gateRepo) {
        this.gateRepo = gateRepo;
    }

    @Override
    public Gate createGate(Gate gate) {
        return gateRepo.save(gate);
    }



    @Override
    public Gate get(UUID id) {
        return gateRepo.findById(id).orElseThrow(GateNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    @Override
    public List<Gate> getAll() {
        return gateRepo.findAll();
    }

    @Override
    public Gate remove(UUID id) {
        Gate removed = this.get(id);
        gateRepo.deleteById(id);
        return removed;
    }

    @Override
    public Gate updateNumber(UUID id, int number) {
        Gate gate = this.get(id);

        gate.setNumber(number);

        return gateRepo.save(gate);               // save the updated Gate and return it
    }

}

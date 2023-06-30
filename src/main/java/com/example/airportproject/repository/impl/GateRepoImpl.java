package com.example.airportproject.repository.impl;

import com.example.airportproject.model.Gate;
import com.example.airportproject.repository.GateRepo;
import com.example.airportproject.repository.impl.mapper.GateMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class GateRepoImpl implements GateRepo {
    private final GateMapper gateMapper;

    public GateRepoImpl(GateMapper gateMapper){
        this.gateMapper = gateMapper;
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
}

package com.example.airportproject.repository.impl;

import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.repository.ImpactEventRepo;
import com.example.airportproject.repository.impl.mapper.ImpactEventMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ImpactEventRepoImpl implements ImpactEventRepo {

    private final ImpactEventMapper impactEventMapper;

    public ImpactEventRepoImpl(ImpactEventMapper impactEventMapper) {
        this.impactEventMapper = impactEventMapper;
    }

    @Override
    public ImpactEvent create(ImpactEvent impactEvent) {
        impactEventMapper.create(impactEvent);
        return impactEvent;
    }

    @Override
    public List<ImpactEvent> getAll() {
        return impactEventMapper.getAll();
    }

    @Override
    public ImpactEvent get(UUID id) {
        return impactEventMapper.get(id);
    }

    @Override
    public ImpactEvent remove(UUID id) {
        return impactEventMapper.remove(id);
    }

    @Override
    public void clear() {
        impactEventMapper.clear();
    }
}

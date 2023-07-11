package com.example.airportproject.dao.impl;

import com.example.airportproject.dao.ImpactEventDao;
import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.repository.ImpactEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ImpactEventDaoImpl implements ImpactEventDao {
    private final ImpactEventRepo impactEventRepo;

    @Autowired
    public ImpactEventDaoImpl(ImpactEventRepo impactEventRepo) {
        this.impactEventRepo = impactEventRepo;
    }

    @Override
    public List<ImpactEvent> getAll() {
        return impactEventRepo.getAll();
    }

    @Override
    public ImpactEvent get(UUID id) {
        return impactEventRepo.get(id);
    }

    @Override
    public ImpactEvent create(ImpactEvent impactEvent) {
        return impactEventRepo.create(impactEvent);
    }

    @Override
    public ImpactEvent update(ImpactEvent impactEvent) {
        return impactEventRepo.update(impactEvent);
    }

    @Override
    public ImpactEvent remove(UUID id) {
        return impactEventRepo.remove(id);
    }
}

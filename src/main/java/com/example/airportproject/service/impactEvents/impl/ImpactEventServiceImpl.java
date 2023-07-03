package com.example.airportproject.service.impactEvents.impl;

import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.repository.ImpactEventRepo;
import com.example.airportproject.service.impactEvents.ImpactEventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImpactEventServiceImpl implements ImpactEventService {
    private final ImpactEventRepo impactEventRepo;

    public ImpactEventServiceImpl(ImpactEventRepo impactEventRepo) {
        this.impactEventRepo = impactEventRepo;
    }



    @Override
    public ImpactEvent create(ImpactEvent impactEvent) {
        return impactEventRepo.create(impactEvent);
    }

    @Override
    public ImpactEvent get(UUID id) {
        return impactEventRepo.get(id);
    }

    @Override
    public List<ImpactEvent> getAll() {
        return impactEventRepo.getAll();
    }

    @Override
    public ImpactEvent remove(UUID id) {
        ImpactEvent impactEvent = impactEventRepo.get(id);
        impactEventRepo.remove(id);
        return impactEvent;
    }
}

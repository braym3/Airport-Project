package com.example.airportproject.service.impactEvents.impl;

import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.repository.ImpactEventRepo;
import com.example.airportproject.service.impactEvents.ImpactEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImpactEventServiceImpl implements ImpactEventService {
    private final ImpactEventRepo impactEventRepo;
    private ImpactEventTimeSlotHandler impactEventTimeSlotHandler;

    @Autowired
    public ImpactEventServiceImpl(ImpactEventRepo impactEventRepo, @Lazy ImpactEventTimeSlotHandler impactEventTimeSlotHandler) {
        this.impactEventRepo = impactEventRepo;
        this.impactEventTimeSlotHandler = impactEventTimeSlotHandler;
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

    @Override
    public List<TimeSlot> triggerImpactEvents(){
        return impactEventTimeSlotHandler.triggerImpactEvents();
    }
}

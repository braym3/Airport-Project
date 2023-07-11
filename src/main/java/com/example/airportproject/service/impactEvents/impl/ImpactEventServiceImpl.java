package com.example.airportproject.service.impactEvents.impl;

import com.example.airportproject.dao.ImpactEventDao;
import com.example.airportproject.model.Gate;
import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.repository.ImpactEventRepo;
import com.example.airportproject.service.impactEvents.ImpactEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ImpactEventServiceImpl implements ImpactEventService {
    private final ImpactEventDao impactEventDao;
    private final ImpactEventTimeSlotHandler impactEventTimeSlotHandler;

    @Autowired
    public ImpactEventServiceImpl(ImpactEventDao impactEventDao, @Lazy ImpactEventTimeSlotHandler impactEventTimeSlotHandler) {
        this.impactEventDao = impactEventDao;
        this.impactEventTimeSlotHandler = impactEventTimeSlotHandler;
    }

    @Override
    public ImpactEvent create(ImpactEvent impactEvent) {
        return impactEventDao.create(impactEvent);
    }

    @Override
    public ImpactEvent get(UUID id) {
        return impactEventDao.get(id);
    }

    @Override
    public List<ImpactEvent> getAll() {
        return impactEventDao.getAll();
    }

    @Override
    public ImpactEvent update(UUID id, String type, String description, BigDecimal probability) {
        ImpactEvent impactEvent = this.get(id);

        // update the impact event attributes if the corresponding parameters are provided
        if(type != null){
            impactEvent.setType(type);
        }
        if(description != null){
            impactEvent.setDescription(description);
        }
        if(probability != null){
            impactEvent.setProbability(probability);
        }
        // save and return the updated impact event record
        return impactEventDao.update(impactEvent);
    }

    @Override
    public ImpactEvent remove(UUID id) {
        ImpactEvent impactEvent = impactEventDao.get(id);
        impactEventDao.remove(id);
        return impactEvent;
    }

    @Override
    public List<TimeSlot> triggerImpactEvents(){
        return impactEventTimeSlotHandler.triggerImpactEvents();
    }
}

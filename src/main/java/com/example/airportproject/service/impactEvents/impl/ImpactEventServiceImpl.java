package com.example.airportproject.service.impactEvents.impl;

import com.example.airportproject.dao.ImpactEventDao;
import com.example.airportproject.dto.ImpactEventDTO;
import com.example.airportproject.model.Flight;
import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.model.enums.Entity;
import com.example.airportproject.service.impactEvents.ImpactEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    public ImpactEvent update(UUID id, String type, String description, BigDecimal probability, int minDuration, int maxDuration, Entity impacts) {
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
        impactEvent.setMinDuration(minDuration);
        impactEvent.setMaxDuration(maxDuration);
        if(impacts != null){
            impactEvent.setImpacts(impacts);
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

    @Override
    public void createHistorySlot(Flight flight, UUID oldGateId, UUID newGateId, LocalDateTime oldDepTime, LocalDateTime newDepTime, LocalDateTime oldArrTime, LocalDateTime newArrTime, UUID impactTimeSlotId, UUID oldRunwayId, UUID newRunwayId){
        impactEventDao.createHistorySlot(flight, oldGateId, newGateId, oldDepTime, newDepTime, oldArrTime, newArrTime, impactTimeSlotId, oldRunwayId, newRunwayId);
    }

    @Override
    public List<Objects> getAllHistory(){
        return impactEventDao.getAllHistory();
    }

    @Override
    public List<Objects> getHistoryForImpactEventTimeSlotId(UUID impactTimeSlotId){
        return impactEventDao.getHistoryForImpactEventTimeSlotId(impactTimeSlotId);
    }

    @Override
    public ImpactEventDTO convertToDTO(ImpactEvent impactEvent){
        ImpactEventDTO impactEventDTO = new ImpactEventDTO();
        impactEventDTO.setId(impactEvent.getId());
        impactEventDTO.setType(impactEvent.getType());
        impactEventDTO.setDescription(impactEvent.getDescription());
        impactEventDTO.setProbability(impactEvent.getProbability());
        impactEventDTO.setMinDuration(impactEvent.getMinDuration());
        impactEventDTO.setMaxDuration(impactEvent.getMaxDuration());
        impactEventDTO.setImpacts(impactEvent.getImpacts().getName());
        return impactEventDTO;
    }
}

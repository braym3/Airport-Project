package com.example.airportproject.repository.impl;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.repository.ImpactEventRepo;
import com.example.airportproject.repository.impl.mapper.ImpactEventMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    public ImpactEvent update(ImpactEvent impactEvent) {
        return impactEventMapper.update(impactEvent);
    }

    @Override
    public ImpactEvent remove(UUID id) {
        return impactEventMapper.remove(id);
    }

    @Override
    public void clear() {
        impactEventMapper.clear();
    }

    @Override
    public void createHistorySlot(Flight flight, UUID oldGateId, UUID newGateId, LocalDateTime oldDepTime, LocalDateTime newDepTime, LocalDateTime oldArrTime, LocalDateTime newArrTime, UUID impactTimeSlotId){
        impactEventMapper.createHistorySlot(flight, oldGateId, newGateId, oldDepTime, newDepTime, oldArrTime, newArrTime, impactTimeSlotId);
    }

    @Override
    public List<Objects> getAllHistory(){
        return impactEventMapper.getAllHistory();
    }

    @Override
    public List<Objects> getHistoryForImpactEventTimeSlotId(UUID impactTimeSlotId){
        return impactEventMapper.getHistoryForImpactEventTimeSlotId(impactTimeSlotId);
    }
}

package com.example.airportproject.dao.impl;

import com.example.airportproject.dao.ImpactEventDao;
import com.example.airportproject.model.Flight;
import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.repository.ImpactEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    @Override
    public void createHistorySlot(Flight flight, UUID oldGateId, UUID newGateId, LocalDateTime oldDepTime, LocalDateTime newDepTime, LocalDateTime oldArrTime, LocalDateTime newArrTime, UUID impactTimeSlotId, UUID oldRunwayId, UUID newRunwayId){
        impactEventRepo.createHistorySlot(flight, oldGateId, newGateId, oldDepTime, newDepTime, oldArrTime, newArrTime, impactTimeSlotId, oldRunwayId, newRunwayId);
    }

    @Override
    public List<Objects> getAllHistory(){
        return impactEventRepo.getAllHistory();
    }

    @Override
    public List<Objects> getHistoryForImpactEventTimeSlotId(UUID impactTimeSlotId){
        return impactEventRepo.getHistoryForImpactEventTimeSlotId(impactTimeSlotId);
    }
}

package com.example.airportproject.dao;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.ImpactEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface ImpactEventDao {
    List<ImpactEvent> getAll();
    ImpactEvent get(UUID id);
    ImpactEvent create(ImpactEvent impactEvent);
    ImpactEvent update(ImpactEvent impactEvent);
    ImpactEvent remove(UUID id);

    void createHistorySlot(Flight flight, UUID oldGateId, UUID newGateId, LocalDateTime oldDepTime, LocalDateTime newDepTime, LocalDateTime oldArrTime, LocalDateTime newArrTime, UUID impactTimeSlotId);

    List<Objects> getAllHistory();

    List<Objects> getHistoryForImpactEventTimeSlotId(UUID impactTimeSlotId);
}

package com.example.airportproject.repository;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.ImpactEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface ImpactEventRepo {
    ImpactEvent create(ImpactEvent impactEvent);

    List<ImpactEvent> getAll();

    ImpactEvent get(UUID id);

    ImpactEvent update(ImpactEvent impactEvent);

    ImpactEvent remove(UUID id);

    void clear();

    void createHistorySlot(Flight flight, UUID oldGateId, UUID newGateId, LocalDateTime oldDepTime, LocalDateTime newDepTime, LocalDateTime oldArrTime, LocalDateTime newArrTime, UUID impactTimeSlotId, UUID oldRunwayId, UUID newRunwayId);

    List<Objects> getAllHistory();

    List<Objects> getHistoryForImpactEventTimeSlotId(UUID impactTimeSlotId);
}

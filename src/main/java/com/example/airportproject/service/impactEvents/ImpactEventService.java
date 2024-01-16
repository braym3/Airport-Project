package com.example.airportproject.service.impactEvents;

import com.example.airportproject.dto.ImpactEventDTO;
import com.example.airportproject.model.*;
import com.example.airportproject.model.enums.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Defines the contract for managing impact events, which are random events that have a chance of occurring at an airport and will have an impact on the flight schedule.
 * It provides operations to fetch impact event records, add a new impact event, and remove impact events.
 */
public interface ImpactEventService {
    /**
     * Creates a new record in the database with the data from an ImpactEvent object
     * @param impactEvent The ImpactEvent to persist
     * @return The created ImpactEvent
     */
    ImpactEvent create(ImpactEvent impactEvent);

    /**
     * Returns the ImpactEvent with the given id
     * @param id The id of the impact event to find
     * @return the ImpactEvent object with the matching id
     */
    ImpactEvent get(UUID id);

    /**
     * Returns a List of all ImpactEvents in the database
     * @return List of all ImpactEvent objects found
     */
    List<ImpactEvent> getAll();

    /**
     * Updates the impact event record of the corresponding id with the specified attributes
     * @param id The id used to identify the Gate to update
     * @param type The impact event type
     * @param description The description of the impact event
     * @param probability The probability of the impact event occurring
     * @return The updated ImpactEvent object
     */
    ImpactEvent update(UUID id, String type, String description, BigDecimal probability, int minDuration, int maxDuration, Entity impacts);

    /**
     * Removes the ImpactEvent with the corresponding id from the database
     * @param id The id used to identify the ImpactEvent to delete
     * @return The deleted ImpactEvent object
     */
    ImpactEvent remove(UUID id);

    List<TimeSlot> triggerImpactEvents();

    void createHistorySlot(Flight flight, UUID oldGateId, UUID newGateId, LocalDateTime oldDepTime, LocalDateTime newDepTime, LocalDateTime oldArrTime, LocalDateTime newArrTime, UUID impactTimeSlotId, UUID oldRunwayId, UUID newRunwayId);

    List<Objects> getAllHistory();

    List<Objects> getHistoryForImpactEventTimeSlotId(UUID impactTimeSlotId);

    ImpactEventDTO convertToDTO(ImpactEvent impactEvent);
}

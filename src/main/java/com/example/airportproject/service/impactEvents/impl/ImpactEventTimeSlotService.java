package com.example.airportproject.service.impactEvents.impl;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.service.flights.FlightService;
import com.example.airportproject.service.gates.GateService;
import com.example.airportproject.service.impactEvents.ImpactEventService;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ImpactEventTimeSlotService {

    private ImpactEventService impactEventService;
    private FlightService flightService;
    private GateService gateService;

    @Value("${airportproject.airportcode}")
    String airportCode;

    public LocalDateTime createRandomStartTime(LocalDateTime firstFlightTime, LocalDateTime lastFlightTime){
        // generate a random start time between the first and last flight times
        return LocalDateTime.of(firstFlightTime.toLocalDate(), LocalTime.MIN).plusMinutes(ThreadLocalRandom.current().nextLong(
                ChronoUnit.MINUTES.between(firstFlightTime, lastFlightTime)
        ));
    }

    public LocalDateTime createRandomEndTime(LocalDateTime eventStartTime, LocalDateTime lastFlightTime){
        // calculate max duration based on the time remaining until the last flight
        long maxDuration = ChronoUnit.MINUTES.between(eventStartTime, lastFlightTime);
        // generate random duration between 1 minute and the maximum duration
        long randomDuration = ThreadLocalRandom.current().nextLong(1, maxDuration + 1);
        // generate a random end time by adding the random duration to the event start time
        return eventStartTime.plusMinutes(randomDuration);
    }

    public TimeSlot createImpactEventTimeSlot(UUID impactEventId){
        // event should happen in time period between first and last flight
        // get the time of the first flight
        LocalDateTime firstFlightTime = flightService.getFirstFlightTime(airportCode);
        // get the time of the last flight
        LocalDateTime lastFlightTime = flightService.getLastFlightTime(airportCode);

        // generate random start and end time for the event (between these 2 flight times)
        LocalDateTime eventStartTime = createRandomStartTime(firstFlightTime, lastFlightTime);
        LocalDateTime eventEndTime = createRandomEndTime(eventStartTime, lastFlightTime);
        return new TimeSlot(null, eventStartTime, eventEndTime, impactEventId);
    }
    public void closeRandomGate(UUID impactEventId){
        // get all gates
        List<Gate> gates = gateService.getAll();

        // choose random gate from list of gates
        int randomIndex = (int) (Math.random() * gates.size());
        Gate selectedGate = gates.get(randomIndex);

        // create impact event for gate closure
        TimeSlot gateClosedSlot = createImpactEventTimeSlot(impactEventId);
        // re-organise schedule and persist the updated gate with its new schedule
        redoSchedule(selectedGate, gateClosedSlot);
    }

    private void redoSchedule(Gate selectedGate, TimeSlot impactEventTimeSlot){

    }
}

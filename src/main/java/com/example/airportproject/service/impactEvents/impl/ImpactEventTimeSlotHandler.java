package com.example.airportproject.service.impactEvents.impl;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.Gate;
import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.service.flights.FlightService;
import com.example.airportproject.service.gates.GateService;
import com.example.airportproject.service.impactEvents.ImpactEventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ImpactEventTimeSlotHandler{

    private final ImpactEventService impactEventService;
    private final FlightService flightService;
    private final GateService gateService;

    @Value("${airportproject.airportcode}")
    String airportCode;

    public ImpactEventTimeSlotHandler(ImpactEventService impactEventService, FlightService flightService, GateService gateService) {
        this.impactEventService = impactEventService;
        this.flightService = flightService;
        this.gateService = gateService;
    }

    public LocalDateTime createRandomStartTime(LocalDateTime firstFlightTime, LocalDateTime lastFlightTime){
        // generate a random start time between the first and last flight times
        return LocalDateTime.of(firstFlightTime.toLocalDate(), LocalTime.MIN).plusMinutes(ThreadLocalRandom.current().nextLong(
                ChronoUnit.MINUTES.between(firstFlightTime, lastFlightTime)
        ));
    }

    public LocalDateTime createRandomEndTime(LocalDateTime eventStartTime, ImpactEvent impactEvent){
        // generate random duration between the event's minimum duration and maximum duration
        long randomDuration =
            ThreadLocalRandom.current().nextLong(impactEvent.getMinDuration(), impactEvent.getMaxDuration());
        // generate a random end time by adding the random duration to the event start time
        return eventStartTime.plusMinutes(randomDuration);
    }

    public TimeSlot createImpactEventTimeSlot(Gate gate, ImpactEvent impactEvent){
        // event should happen in time period between first and last flight
        // get the time of the first flight
        LocalDateTime firstFlightTime = flightService.getFirstFlightTime(airportCode);
        // get the time of the last flight
        LocalDateTime lastFlightTime = flightService.getLastFlightTime(airportCode);

        // generate random start and end time for the event (between these 2 flight times)
        LocalDateTime eventStartTime = createRandomStartTime(firstFlightTime, lastFlightTime);
        LocalDateTime eventEndTime = createRandomEndTime(eventStartTime, impactEvent);
        return new TimeSlot(gate, null, eventStartTime, eventEndTime, impactEvent);
    }

    public TimeSlot closeRandomGate(ImpactEvent impactEvent){
        // get all gates
        List<Gate> gates = gateService.getAll();

        // choose random gate from list of gates
        int randomIndex = (int) (Math.random() * gates.size());
        Gate selectedGate = gates.get(randomIndex);

        // create impact event for gate closure
        TimeSlot gateClosedSlot = createImpactEventTimeSlot(selectedGate, impactEvent);
        // save the gate closed time slot to the gate slots table
        gateService.addGateSlot(gateClosedSlot);

        // re-organise schedule and persist the updated gate with its new schedule
        redoGateSchedule(selectedGate, gateClosedSlot, gates);

        return gateClosedSlot;
    }

    private List<Flight> getImpactedFlights(Gate affectedGate, TimeSlot newTimeSlot){
        List<Flight> impactedFlights = new ArrayList<>();
        for(TimeSlot gateOccupiedSlot : affectedGate.getSchedule()){
            if(gateOccupiedSlot.getFlight() != null && gateOccupiedSlot.overlaps(newTimeSlot.getStartTime(), newTimeSlot.getEndTime())){
                impactedFlights.add(gateOccupiedSlot.getFlight());
            }
        }
        return impactedFlights;
    }

    private Duration calculateTimeDifference(LocalDateTime start, LocalDateTime end){
        return Duration.between(start, end);
    }

    // trying to keep the departure or arrival time of the flight as close to the original as possible
    private void redoGateSchedule(Gate selectedGate, TimeSlot impactEventTimeSlot, List<Gate> gates){
        // identify which flights are assigned to this gate in the affected time frame
        List<Flight> impactedFlights = getImpactedFlights(selectedGate, impactEventTimeSlot);
        // for each flight that needs to be re-assigned try to find the closest availability
        for(Flight impactedFlight : impactedFlights){
            Gate bestAvailableGate = null;
            // the minimum time difference between the original scheduled time and new available time
            Duration minTimeDifference = null;
            LocalDateTime bestAvailableStartTime = null, bestAvailableEndTime = null;

            TimeSlot orignalFlightTimeSlot = gateService.getGateTimeSlotByFlightId(impactedFlight.getId());
            LocalDateTime originalStartTime = orignalFlightTimeSlot.getStartTime();
            LocalDateTime originalEndTime = orignalFlightTimeSlot.getEndTime();

            // remove the old flight timeslot from the impacted gate
            gateService.removeTimeSlotForGate(orignalFlightTimeSlot.getId());

            // the amount of time (minutes) that the flight requires to occupy the gate
            long requiredMinutes = calculateTimeDifference(originalStartTime, originalEndTime).toMinutes();

            for(Gate gate : gates){
                // for every gate except for the affected gate, check best suitable availability
                if(!selectedGate.getId().equals(gate.getId())){
                    // get the schedule for the current gate
                    List<TimeSlot> gateSchedule = gate.getSchedule();

                    if(gateSchedule.isEmpty()){
                        bestAvailableGate = gate;
                        bestAvailableStartTime = originalStartTime;
                        bestAvailableEndTime = originalEndTime;
                        // exit the loop if an empty gate is found - the minimum time difference has been found
                        break;
                    }else{
                        for(int i = 0; i < gateSchedule.size(); i++){
                            TimeSlot currentSlot = gateSchedule.get(i);
                            // set the previous time slot if the current time slot is not the first in the schedule
                            TimeSlot previousSlot = (i > 0) ? gateSchedule.get(i-1) : null;

                            if(previousSlot != null && currentSlot.getStartTime().isAfter(previousSlot.getEndTime())){
                                // calculate the gap between the current time slot and the previous time slot
                                long gapMinutes = calculateTimeDifference(previousSlot.getEndTime(), currentSlot.getStartTime()).toMinutes();
                                // if the impacted flight can fit into this gap, set the current best fit to the closest time slot in the gap
                                if(gapMinutes >= requiredMinutes){
                                    Duration timeDifference = calculateTimeDifference(originalStartTime, previousSlot.getEndTime());

                                    if(bestAvailableStartTime == null || timeDifference.abs().compareTo(minTimeDifference.abs()) < 0){
                                        bestAvailableGate = gate;
                                        bestAvailableStartTime = previousSlot.getEndTime();
                                        bestAvailableEndTime = previousSlot.getEndTime().plusMinutes(requiredMinutes);
                                        minTimeDifference = timeDifference; // could add runway taxi time
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // create new best time slot and add it to the gate schedule
            assert bestAvailableStartTime != null;
            TimeSlot newTimeSlot = new TimeSlot(bestAvailableGate, impactedFlight, bestAvailableStartTime, bestAvailableEndTime, null);

            // add and save the new time slot for the flight to the best available gate found
            bestAvailableGate.addTimeSlot(newTimeSlot);
            gateService.addGateSlot(newTimeSlot);
        }
    }

    public List<TimeSlot> triggerImpactEvents() {
        List<TimeSlot> triggeredEvents = new ArrayList<>();
        List<ImpactEvent> impactEvents = impactEventService.getAll();
        triggeredEvents.add(closeRandomGate(impactEvents.get(0)));

        // random probability of event happening
        double randomValue = ThreadLocalRandom.current().nextDouble();
        if(randomValue <= impactEvents.get(2).getProbability().doubleValue()){
            triggeredEvents.add(closeRandomGate(impactEvents.get(2)));
        }
        // get all impact events
        // randomise the chance of each of them happening based on their probability
        // carry out the triggered impact events - reassign schedule accordingly
        // add that impact event to the list of triggered events
        return triggeredEvents;
    }
}

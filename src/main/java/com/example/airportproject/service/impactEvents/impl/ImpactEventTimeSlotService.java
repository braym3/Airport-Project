package com.example.airportproject.service.impactEvents.impl;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.Gate;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.service.flights.FlightService;
import com.example.airportproject.service.gates.GateService;
import com.example.airportproject.service.impactEvents.ImpactEventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ImpactEventTimeSlotService {

    private ImpactEventService impactEventService;
    private FlightService flightService;
    private GateService gateService;

    @Value("${airportproject.airportcode}")
    String airportCode;

    public ImpactEventTimeSlotService(ImpactEventService impactEventService, FlightService flightService, GateService gateService) {
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
        redoGateSchedule(selectedGate, gateClosedSlot, gates);
        System.out.println("T" + selectedGate.getTerminal().getNumber() + " gate " + selectedGate.getNumber() + " closed from: " + gateClosedSlot.getStartTime() + " until " + gateClosedSlot.getEndTime());
    }

    private List<Flight> getImpactedFlights(Gate affectedGate, TimeSlot newTimeSlot){
        List<Flight> impactedFlights = new ArrayList<>();
        for(TimeSlot gateOccupiedSlot : affectedGate.getSchedule()){
            if(gateOccupiedSlot.getFlightId() != null && gateOccupiedSlot.overlaps(newTimeSlot.getStartTime(), newTimeSlot.getEndTime())){
                impactedFlights.add(flightService.get(gateOccupiedSlot.getFlightId()));
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
            Duration minTimeDifference = null;
            LocalDateTime bestAvailableStartTime = null, bestAvailableEndTime = null;

            TimeSlot orignalFlightTimeSlot = gateService.getGateTimeSlotByFlightId(impactedFlight.getId());
            LocalDateTime originalStartTime = orignalFlightTimeSlot.getStartTime();
            LocalDateTime originalEndTime = orignalFlightTimeSlot.getEndTime();
            Long requiredMinutes = calculateTimeDifference(originalStartTime, originalEndTime).toMinutes();

            for(Gate gate : gates){
                // for every gate except for the affected gate, check best suitable availability
                if(!selectedGate.getId().equals(gate.getId())){
                    List<TimeSlot> gateSchedule = gate.getSchedule();

                    for(int i = 0; i < gateSchedule.size(); i++){
                        TimeSlot currentSlot = gateSchedule.get(i);
                        TimeSlot previousSlot = (i > 0) ? gateSchedule.get(i-1) : null;

                        if(previousSlot != null && currentSlot.getStartTime().isAfter(previousSlot.getEndTime())){
                            Long gapMinutes = calculateTimeDifference(previousSlot.getEndTime(), currentSlot.getStartTime()).toMinutes();
                            if(gapMinutes >= requiredMinutes){
                                Duration timeDifference = calculateTimeDifference(originalStartTime, previousSlot.getEndTime());

                                if(bestAvailableStartTime == null || timeDifference.abs().compareTo(minTimeDifference.abs()) < 0){
                                    bestAvailableGate = gate;
                                    bestAvailableStartTime = previousSlot.getEndTime();
                                    bestAvailableEndTime = previousSlot.getEndTime().plus(calculateTimeDifference(originalStartTime, originalEndTime));

                                    minTimeDifference = timeDifference;
                                }
                            }
                        }
                    }
                }
            }
            System.out.print("\nFlight reassigned: " +
                    "\nold start time = " + originalStartTime +
                    "\nold end time = " + originalEndTime +
                    "\nbest gate = T" + bestAvailableGate.getTerminal().getNumber() + " gate " + bestAvailableGate.getNumber() +
                    "\nbest start time = " + bestAvailableStartTime +
                    "\nbest end time = " + bestAvailableEndTime + "\n");
        }
        // consider gates that can accommodate the affected flights - based on size, taxi time to runway, availability etc

        // find the available slots of other gates that are long enough for the flight
                // find the amount of time between the gate slot availability and the original flight time (add runway taxi time from new gate)
                // save the gate and time slot if it is the closest match found yet

        // assign the gate to the corresponding flight at the best timeslot found

    }
}

package com.example.airportproject.service.runways.impl;

import com.example.airportproject.model.*;
import com.example.airportproject.repository.FlightRepo;
import com.example.airportproject.service.gates.GateService;
import com.example.airportproject.service.runways.RunwayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FlightSchedulerService {

    private final FlightRepo flightRepo;
    private final GateService gateService;
    private final RunwayService runwayService;

    @Value("#{${airportproject.airportcode}}")
    String airportCode;
    @Value("${gate.departureSlot}")
    int gateDepartureSlotDuration;
    @Value("${gate.arrivalSlot}")
    int gateArrivalSlotDuration;
    @Value("${runway.departureSlot}")
    int runwayDepartureSlotDuration;
    @Value("${runway.arrivalSlot}")
    int runwayArrivalSlotDuration;
    private final Logger logger = LoggerFactory.getLogger(FlightSchedulerService.class);

    public FlightSchedulerService(FlightRepo flightRepo, GateService gateService, RunwayService runwayService) {
        this.flightRepo = flightRepo;
        this.gateService = gateService;
        this.runwayService = runwayService;
    }

    public void scheduleFlights(){
        // Get the list of sorted flights (both arrivals and departures)
        logger.debug("RunwayAssigner getting ordered flights with airport code {}", airportCode);
        List<Flight> sortedFlights = flightRepo.getOrderedFlights(airportCode);

        // Get the list of runways at the airport
        logger.debug("RunwayAssigner getting all runways");
        List<Runway> runways = runwayService.getAll();

        // Get the list of gates at the airport
        logger.debug("RunwayAssigner getting all runways");
        List<Gate> gates = gateService.getAll();

        for(Flight flight : sortedFlights){
            // calculate the start and end time of the runway slot wanted for the flight's departure/arrival time
            LocalDateTime runwayStartTime = getRunwaySlotStartTime(flight);
            LocalDateTime runwayEndTime = getRunwaySlotEndTime(flight);

//            Runway assignedRunway = assignRunwayToFlight(flight, runways);
//
//            if(assignedRunway != null){
//                // try to assign gate
//                // set the allotted start and end time of the flight using the gate
//                LocalDateTime gateStartTime = getGateSlotStartTime(flight);
//                LocalDateTime gateEndTime = getGateSlotEndTime(flight);
//
//                // find all available gates for the preferred time of the flight
//                List<Gate> availableGates = findAvailableGates(gateStartTime, gateEndTime, gates); // could add preference for min taxi time to chosen runway
//                // assign the flight to a random gate from the list of those available
//                assignGate(flight, gateStartTime, gateEndTime, availableGates);
//            }else {
//                // handle conflict - no space in runway schedule
//
//            }
        }

    }

    // returns a list of gates that are available between the specified start and end time
    private List<Gate> findAvailableGates(LocalDateTime startTime, LocalDateTime endTime, List<Gate> gates){
        List<Gate> availableGates = new ArrayList<>();
        for(Gate gate : gates){
            // check if the gate is available during the start time and end time
            if(gate.isAvailableAtTimeSlot(startTime, endTime)){
                availableGates.add(gate);
            }
        }
        if(availableGates.isEmpty()){
            // handle conflict - no available gate for corresponding runway slot
            return null;
//            Map<Gate, TimeSlot> closestAvailable = findClosestAvailableGateSlot();
//            if(!closestAvailable.isEmpty()){
//                closestAvailable
        }
        return availableGates;
    }

    private Map<Gate, TimeSlot> findClosestAvailableRunwaySlot() {
        Map<Gate, TimeSlot> closestAvailableSlot = new HashMap<>();
        return closestAvailableSlot;
    }

    // assigns a randomly chosen gate, from a list of available gates, to a flight
    private void assignGate(Flight flight, LocalDateTime startTime, LocalDateTime endTime, List<Gate> availableGates){
        if(availableGates.size() > 0){
            // choose a random gate from those available
            Gate chosenGate = availableGates.get(new Random().nextInt(availableGates.size()));

            // add a new time slot to the gate schedule
            TimeSlot timeSlot = new TimeSlot(chosenGate, flight, startTime, endTime, null);
            chosenGate.addTimeSlot(timeSlot);
            // add the new time slot to the database
            gateService.addGateSlot(timeSlot);

            // set the Gate ID in the Flight object
            flight.setGate(chosenGate);
            flightRepo.update(flight);
        }

    }

    private boolean isDeparture(Flight flight){
        return flight.getDepIata().equals(airportCode);
    }

    private LocalDateTime getGateSlotStartTime(Flight flight){
        LocalDateTime startTime = null;
        if(isDeparture(flight)){
            // if flight is departing - the flight occupies the gate from a number of minutes (gate departure slot length) before departure until departure time
            startTime = flight.getDepTime().minusMinutes(gateDepartureSlotDuration);
        }else {
            // if flight is arriving - the flight occupies the gate from arrival time until a number of minutes (gate arrival slot length) after arrival
            startTime = flight.getArrTime();
        }
        return startTime;
    }

    private LocalDateTime getGateSlotEndTime(Flight flight){
        LocalDateTime endTime = null;
        if(isDeparture(flight)){
            // if flight is departing - the flight occupies the gate from a number of minutes (gate departure slot length) before departure until departure time
            endTime = flight.getDepTime();
        }else {
            // if flight is arriving - the flight occupies the gate from arrival time until a number of minutes (gate arrival slot length) after arrival
            endTime = flight.getArrTime().plusMinutes(gateArrivalSlotDuration);
        }
        return endTime;
    }

    private LocalDateTime getRunwaySlotStartTime(Flight flight){
        LocalDateTime startTime = null;
        if(isDeparture(flight)){
            // if flight is departing - the flight occupies the runway from departure time until a number of minutes after departure time (runway departure slot duration)
            startTime = flight.getDepTime();
        }else {
            // if flight is arriving - the flight occupies the runway from a number of minutes before arrival time (runway arrival slot duration) until the arrival time
            startTime = flight.getArrTime().minusMinutes(runwayArrivalSlotDuration);
        }
        return startTime;
    }

    private LocalDateTime getRunwaySlotEndTime(Flight flight){
        LocalDateTime endTime = null;
        if(isDeparture(flight)){
            // if flight is departing - the flight occupies the runway from departure time until a number of minutes after departure time (runway departure slot duration)
            endTime = flight.getDepTime().plusMinutes(runwayDepartureSlotDuration);
        }else {
            // if flight is arriving - the flight occupies the runway from a number of minutes before arrival time (runway arrival slot duration) until the arrival time
            endTime = flight.getArrTime();
        }
        return endTime;
    }




//    public Gate assignGateToFlight(F){
//        for(Gate gate : gates){
//            // check if the gate is available during the start time and end time
//            if(gate.isAvailableAtTimeSlot(startTime, endTime)){
//                return gate;
//            }
//        }
//        // if no runway is available between the preferred start and end time, find the closest available time
//        Runway closestAvailableRunway = null;
//        TimeSlot closestAvailableTime = null;
//        Duration runwaySlotDuration = Duration.between(startTime, endTime);
//
//
//        return null;
//    }
//
//    private Duration calculateTimeDifference(LocalDateTime start, LocalDateTime end){
//        return Duration.between(start, end);
//    }

    // find the closest available runway to the flight time
//    public List<Object> assignRunwayToFlight(LocalDateTime startTime, LocalDateTime endTime, List<Runway> runways){
//        for(Runway runway : runways){
//            // check if the runway is available during the start time and end time
//            if(runway.isAvailableAtTimeSlot(startTime, endTime)){
//                return Arrays.asList(runway, startTime, endTime);
//            }
//        }
//        // if no runway is available between the preferred start and end time, find the closest available time
//        Runway closestAvailableRunway = null;
//        LocalDateTime closestStartTime = null, closestEndTime = null;
//        Duration minTimeDifference = null;
//        Duration runwaySlotDuration = Duration.between(startTime, endTime);
//
//        // the amount of time (minutes) that the flight requires to occupy the runway
//        long requiredMinutes = calculateTimeDifference(startTime, endTime).toMinutes();
//
//        // for every runway check best suitable availability
//        for(Runway runway : runways){
//            // get the schedule for the current runway
//            List<TimeSlot> runwaySchedule = runway.getSchedule();
//
//            if(runwaySchedule.isEmpty()){
//                closestAvailableRunway = runway;
//                closestStartTime = startTime;
//                closestEndTime = endTime;
//                // exit the loop if an empty runway is found - the minimum time difference has been found
//                break;
//            }else{
//                for(int i = 0; i < runwaySchedule.size(); i++){
//                    TimeSlot currentSlot = runwaySchedule.get(i);
//                    // set the previous time slot if the current time slot is not the first in the schedule
//                    TimeSlot previousSlot = (i > 0) ? runwaySchedule.get(i-1) : null;
//
//                    // check if there's a previous slot to look between, and the space between slots contains time after the original start time (don't want to reschedule a slot to before the original time)
//                    if(previousSlot != null && currentSlot.getStartTime().isAfter(previousSlot.getEndTime()) && (previousSlot.getEndTime().isAfter(startTime) || currentSlot.getStartTime().isAfter(endTime))){
//                        // calculate the gap between the current time slot and the previous time slot
//                        long gapMinutes = calculateTimeDifference(previousSlot.getEndTime(), currentSlot.getStartTime()).toMinutes();
//                        // if the flight can fit into this gap, set the current best fit to the closest time slot in the gap
//                        if(gapMinutes >= requiredMinutes){
//                            Duration timeDifference = calculateTimeDifference(startTime, previousSlot.getEndTime());
//
//                            if(closestStartTime == null || timeDifference.abs().compareTo(minTimeDifference.abs()) < 0){
//                                closestAvailableRunway = runway;
//                                closestStartTime = previousSlot.getEndTime();
//                                closestEndTime = previousSlot.getEndTime().plusMinutes(requiredMinutes);
//                                minTimeDifference = timeDifference; // could add runway taxi time
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//        // try to find gate that matches
//        assert bestAvailableStartTime != null;
//        TimeSlot newTimeSlot = new TimeSlot(bestAvailableGate, impactedFlight, bestAvailableStartTime, bestAvailableEndTime, null);
//
//
//
//        return null;
//    }

//    public void assignFlight(Flight flight, List<Runway> runways, List<Gate> gates){
//        // for one flight
//        // calculate the start and end time of the runway slot wanted for the flight's departure/arrival time
//        LocalDateTime runwayStartTime = getRunwaySlotStartTime(flight);
//        LocalDateTime runwayEndTime = getRunwaySlotEndTime(flight);
//
//        Runway assignedRunway = assignRunwayToFlight(flight, runways);
//
//        if(assignedRunway != null){
//            // try to assign gate
//            // set the allotted start and end time of the flight using the gate
//            LocalDateTime gateStartTime = getGateSlotStartTime(flight);
//            LocalDateTime gateEndTime = getGateSlotEndTime(flight);
//
//        // go through all runways
//
//            // assign a runway (based on startTime, endTime)
//            Map<Runway, TimeSlot> runwayAvailable = assignRunwayToFlight(runwayStartTime, runwayEndTime, runways);
//            TimeSlot runwayTimeSlot = runwayAvailable.g
//        // go through all gates
//            // does a gate match the runway slot?
//            // no
//                // recursive call using startTime + 1 min, endTime + 1 min
//            // yes
//                // assign the runway & gate - persist
//        }
//
//    }


//    public void assignFlights(List<Flight> flights, List<Runway> runways, List<Gate> gates){
//        // uses simple greedy algorithm
//        // the goal is to minimise the deviation from the original flight times
//
//        // for each runway
//        for(Runway runway : runways){
//            runway.getSchedule().sort(Comparator.comparing(TimeSlot::getStartTime));
//        }
//        for(Gate gate : gates){
//            gate.getSchedule().sort(Comparator.comparing(TimeSlot::getStartTime));
//        }
//        // iterate through all flights to optimise runway and gate allocations
//        for(Flight flight : flights){
//            if(flight.getDepIata().equals(airportCode)){
//                // if departing
//                //
//            }else if(flight.getArrIata().equals(airportCode)){
//                // if arriving
//            }
//        }
//    }

    public void optimiseSchedule(){

    }

    public void handleConflicts(){

    }
}

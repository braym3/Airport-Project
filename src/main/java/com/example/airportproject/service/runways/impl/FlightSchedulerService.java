package com.example.airportproject.service.runways.impl;

import com.example.airportproject.model.*;
import com.example.airportproject.repository.FlightRepo;
import com.example.airportproject.service.flights.FlightService;
import com.example.airportproject.service.gates.GateService;
import com.example.airportproject.service.runways.RunwayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlightSchedulerService {

    private final FlightRepo flightRepo;
    private final FlightService flightService;
    private final GateService gateService;
    private final RunwayService runwayService;

    @Value("${airportproject.airportcode}")
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

    public FlightSchedulerService(FlightRepo flightRepo, FlightService flightService, GateService gateService, RunwayService runwayService) {
        this.flightRepo = flightRepo;
        this.flightService = flightService;
        this.gateService = gateService;
        this.runwayService = runwayService;
    }

    // initialize runway and gate assignments for each flight in the airport
    public void assignRunwaysAndGates(){
        // Get the list of sorted flights (both arrivals and departures)
        logger.debug("FlightSchedulerService getting ordered flights with airport code {}", airportCode);
        List<Flight> sortedFlights = flightRepo.getOrderedFlights(airportCode);

        // assign a runway and a gate to each flight
        scheduleFlights(sortedFlights, false);
    }

    public void scheduleFlights(List<Flight> flights, boolean reassigning){
        // Get the list of runways at the airport
        logger.debug("FlightSchedulerService getting all runways");
        List<Runway> runways = runwayService.getAll();

        // Get the list of gates at the airport
        logger.debug("FlightSchedulerService getting all runways");
        List<Gate> gates = gateService.getAll();

        // Get the start and end time of the daily schedule (first and last flight times)
        logger.debug("FlightSchedulerService getting first and last flight times with airport code {}", airportCode);
        LocalDateTime firstFlightTime = flightService.getFirstFlightTime(airportCode);
        LocalDateTime lastFlightTime = flightService.getLastFlightTime(airportCode);

        // Get the schedules of available times for all runways and gates
        Map<Runway, List<TimeSlot>> runwaySchedulesOfAvailability = runways.stream().collect(Collectors.toMap(runway -> runway, runway -> runway.getScheduleOfAvailability(firstFlightTime, lastFlightTime)));
        Map<Gate, List<TimeSlot>> gateSchedulesOfAvailability = gates.stream().collect(Collectors.toMap(gate -> gate, gate -> gate.getScheduleOfAvailability(firstFlightTime, lastFlightTime)));

        for(Flight flight : flights){
            boolean successfullyAssigned = findClosestAvailableSlots(flight, flight.isDeparture(airportCode), runwaySchedulesOfAvailability, gateSchedulesOfAvailability, reassigning);
        }
    }

    // returns a boolean for whether it was successful in assigning a suitable pair of runway and gate time slots for the flight
    private boolean findClosestAvailableSlots(Flight flight, boolean isDeparture, Map<Runway, List<TimeSlot>> runwayAvailabilityMap, Map<Gate, List<TimeSlot>> gateAvailabilityMap, boolean reassigning){
        LocalDateTime originalTime = flight.getFlightTimeForAirport(airportCode);
        LocalDateTime closestIntersectingTime = null;
        Gate closestGate = null;
        Runway closestRunway = null;
        long closestDuration = Long.MAX_VALUE;

        for (Map.Entry<Gate, List<TimeSlot>> gateEntry : gateAvailabilityMap.entrySet()) {
            Gate gate = gateEntry.getKey();
            List<TimeSlot> gateTimeSlots = gateEntry.getValue();

//            System.out.println("Gate = T" + gate.getTerminal().getNumber() + " " + gate.getNumber());
//            System.out.println(gateTimeSlots.toString());
//            System.out.println();

            for (TimeSlot gateTimeSlot : gateTimeSlots) {
                LocalDateTime gateStartTime = gateTimeSlot.getStartTime();
                LocalDateTime gateEndTime = gateTimeSlot.getEndTime();

                for (Map.Entry<Runway, List<TimeSlot>> runwayEntry : runwayAvailabilityMap.entrySet()) {
                    Runway runway = runwayEntry.getKey();
                    List<TimeSlot> runwayTimeSlots = runwayEntry.getValue();

//                    System.out.println("Runway = " + runway.getNumber());
//                    System.out.println(runwayTimeSlots.toString());
//                    System.out.println();

                    for (TimeSlot runwayTimeSlot : runwayTimeSlots) {
                        LocalDateTime runwayStartTime = runwayTimeSlot.getStartTime();
                        LocalDateTime runwayEndTime = runwayTimeSlot.getEndTime();

                        // Calculate the intersecting time range
                        LocalDateTime intersectingStartTime = null, intersectingEndTime = null;
                        if(isDeparture){
                            intersectingStartTime = gateStartTime.plusMinutes(gateDepartureSlotDuration);
                            intersectingEndTime = runwayEndTime.minusMinutes(runwayDepartureSlotDuration);
                        }else{
                            intersectingStartTime = runwayStartTime.plusMinutes(runwayArrivalSlotDuration);
                            intersectingEndTime = gateEndTime.minusMinutes(gateArrivalSlotDuration);
                        }

                        // Check if the intersecting time range overlaps with the originalTime
                        if (intersectingStartTime.isBefore(originalTime) && intersectingEndTime.isAfter(originalTime)) {
                            // Calculate the duration between originalTime and the intersecting start time
                            Duration duration = Duration.between(originalTime, intersectingStartTime);

                            // Check if the duration is positive or zero, meaning intersectingStartTime is after or equal to originalTime
                            if (!duration.isNegative()) {
                                long currentDuration = duration.toMinutes();
                                if (currentDuration < closestDuration) {
                                    closestDuration = currentDuration;
                                    closestIntersectingTime = intersectingStartTime;
                                    closestGate = gate;
                                    closestRunway = runway;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (closestIntersectingTime != null) {
            System.out.println("Closest intersecting time: " + closestIntersectingTime);
            System.out.println("Gate: " + closestGate);
            System.out.println("Runway: " + closestRunway);
            // if the flight is being reassigned - delete any old time slots for the flight
            if(reassigning){
                clearOldTimeSlots(flight);
            }
            // assign the chosen runway and gate to the flight, and if the flight is being reassigned log the changes
            assignFlight(flight, closestIntersectingTime, closestGate, closestRunway, reassigning);
            return true;
        } else {
            System.out.println("No suitable time found.");
            return false;
        }
    }

    private void assignFlight(Flight flight, LocalDateTime flightTime, Gate availableGate, Runway availableRunway, boolean reassigning){
        if(availableGate != null && availableRunway != null){
            boolean isDeparture = flight.isDeparture(airportCode);

            // add a new time slot to the gate schedule
            TimeSlot gateTimeSlot = new TimeSlot(availableGate, flight, getGateSlotStartTime(isDeparture, flightTime), getGateSlotEndTime(isDeparture, flightTime), null);
            availableGate.addTimeSlot(gateTimeSlot);
            // add the new time slot to the database
            gateService.addGateSlot(gateTimeSlot);

            // add a new time slot to the runway schedule
            TimeSlot runwayTimeSlot = new TimeSlot(availableRunway, flight, getRunwaySlotStartTime(isDeparture, flightTime), getRunwaySlotEndTime(isDeparture, flightTime), null);
            availableRunway.addTimeSlot(runwayTimeSlot);
            // add the new time slot to the database
            runwayService.addRunwaySlot(runwayTimeSlot);

            Flight updatedFlight = updateFlightInfo(flight, availableGate, availableRunway, flightTime, isDeparture, reassigning);

            flightRepo.update(updatedFlight);
        }

    }

    // Update a Flight object with a new Gate, Runway, time, and status
    public Flight updateFlightInfo(Flight flight, Gate newGate, Runway newRunway, LocalDateTime flightTime, boolean isDeparture, boolean reassigning){
        flight.setGate(newGate);
        flight.setRunway(newRunway);

        // update the flight arrival/departure time
        LocalDateTime oldFlightTime = null;
        if(isDeparture){
            oldFlightTime = flight.getDepTime();
            flight.setArrTime(flightTime.plusMinutes(flight.getDuration()));
        }else{
            oldFlightTime = flight.getArrTime();
            // update the departure time of a delayed arrival if this is first assignment
            if(!reassigning){
                flight.setDepTime(flightTime.minusMinutes(flight.getDuration()));
            }
        }

        // if this flight is being reassigned - update the flight's status accordingly
        if(reassigning && !oldFlightTime.isBefore(flightTime)){
            flight.setStatus("Delayed");
        }
        return flight;
    }

    public void clearOldTimeSlots(Flight flight){
        UUID flightId = flight.getId();
        // remove runway slot
        runwayService.removeRunwayTimeSlotByFlightId(flightId);
        // remove gate slot
        gateService.removeGateTimeSlotByFlightId(flightId);
    }

    private LocalDateTime getGateSlotStartTime(boolean isDeparture, LocalDateTime flightTime){
        if(isDeparture){
            // if flight is departing - the flight occupies the gate from a number of minutes (gate departure slot length) before departure until departure time
            return flightTime.minusMinutes(gateDepartureSlotDuration);
        }else{
            // if flight is arriving - the flight occupies the gate from arrival time until a number of minutes (gate arrival slot length) after arrival
            return flightTime;
        }
    }

    private LocalDateTime getGateSlotEndTime(boolean isDeparture, LocalDateTime flightTime){
        if(isDeparture){
            // if flight is departing - the flight occupies the gate from a number of minutes (gate departure slot length) before departure until departure time
            return flightTime;
        }else{
            // if flight is arriving - the flight occupies the gate from arrival time until a number of minutes (gate arrival slot length) after arrival
            return flightTime.plusMinutes(gateArrivalSlotDuration);
        }
    }

    private LocalDateTime getRunwaySlotStartTime(boolean isDeparture, LocalDateTime flightTime){
        if(isDeparture){
            // if flight is departing - the flight occupies the runway from departure time until a number of minutes after departure time (runway departure slot duration)
            return flightTime;
        }else{
            // if flight is arriving - the flight occupies the runway from a number of minutes before arrival time (runway arrival slot duration) until the arrival time
            return flightTime.minusMinutes(runwayArrivalSlotDuration);
        }
    }

    private LocalDateTime getRunwaySlotEndTime(boolean isDeparture, LocalDateTime flightTime){
        if(isDeparture){
            // if flight is departing - the flight occupies the runway from departure time until a number of minutes after departure time (runway departure slot duration)
            return flightTime.plusMinutes(runwayDepartureSlotDuration);
        }else{
            // if flight is arriving - the flight occupies the runway from a number of minutes before arrival time (runway arrival slot duration) until the arrival time
            return flightTime;
        }
    }

    private Duration calculateTimeDifference(LocalDateTime start, LocalDateTime end){
        return Duration.between(start, end);
    }

    public void optimiseSchedule(){

    }

    public void handleConflicts(){

    }
}

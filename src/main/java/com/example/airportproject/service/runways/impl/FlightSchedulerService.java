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
        logger.debug("FlightSchedulerService getting all gates");
        List<Gate> gates = gateService.getAll();

        // Get the start and end time of the daily schedule (first and last flight times)
        logger.debug("FlightSchedulerService getting first and last flight times with airport code {}", airportCode);
        LocalDateTime firstFlightTime = flightService.getFirstFlightTime(airportCode).minusMinutes(gateDepartureSlotDuration);
        LocalDateTime lastFlightTime = flightService.getLastFlightTime(airportCode).plusMinutes(gateDepartureSlotDuration);



        for(Flight flight : flights){
            // Get the schedules of available times for all runways and gates
            Map<Runway, List<TimeSlot>> runwaySchedulesOfAvailability = runways.stream().collect(Collectors.toMap(runway -> runway, runway -> runway.getScheduleOfAvailability(firstFlightTime, lastFlightTime)));
            Map<Gate, List<TimeSlot>> gateSchedulesOfAvailability = gates.stream().collect(Collectors.toMap(gate -> gate, gate -> gate.getScheduleOfAvailability(firstFlightTime, lastFlightTime)));
            // find the closest matching gate and runway slots available for the original flight time and assign them to the flight
            // returns the chosen gate and runway objects (updated with their new occupied timeslots)
            List<Schedulable> assignedGateAndRunway = findClosestAvailableSlots(flight, flight.isDeparture(airportCode), runwaySchedulesOfAvailability, gateSchedulesOfAvailability, reassigning);
            // update the runways and gates lists (replace the chosen gate and runway with the updated objects)
            if(assignedGateAndRunway != null){
                gates = updateGatesWithChange(gates, (Gate) assignedGateAndRunway.get(0));
                runways = updateRunwaysWithChange(runways, (Runway) assignedGateAndRunway.get(1));
            }
        }
    }

    // returns a boolean for whether it was successful in assigning a suitable pair of runway and gate time slots for the flight
    private List<Schedulable> findClosestAvailableSlots(Flight flight, boolean isDeparture, Map<Runway, List<TimeSlot>> runwayAvailabilityMap, Map<Gate, List<TimeSlot>> gateAvailabilityMap, boolean reassigning){
        for(Map.Entry<Runway, List<TimeSlot>> runwayEntry : runwayAvailabilityMap.entrySet()){
            System.out.println("runway: " + runwayEntry.getKey().getNumber());
            runwayEntry.getValue().forEach(timeSlot -> System.out.println("Start time: " + timeSlot.getStartTime() + ", End time: " + timeSlot.getEndTime()));
        }

        LocalDateTime originalTime = flight.getFlightTimeForAirport(airportCode);
        LocalDateTime closestIntersectingTime = null;
        Gate closestGate = null;
        Runway closestRunway = null;
        long closestDuration = Long.MAX_VALUE;
        boolean finished = false;

        System.out.println("Original time = " + originalTime);

        for (Map.Entry<Gate, List<TimeSlot>> gateEntry : gateAvailabilityMap.entrySet()) {
            Gate gate = gateEntry.getKey();
            List<TimeSlot> gateTimeSlots = gateEntry.getValue();

//            System.out.println("Gate = T" + gate.getTerminal().getNumber() + " " + gate.getNumber());

            for (TimeSlot gateTimeSlot : gateTimeSlots) {
                LocalDateTime gateStartTime = gateTimeSlot.getStartTime();
                LocalDateTime gateEndTime = gateTimeSlot.getEndTime();

//                System.out.println("start = " + gateStartTime + "     end = " + gateEndTime);
//                System.out.println();

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
                        LocalDateTime gateIntersectionStart = null, gateIntersectionEnd = null, runwayIntersectionStart = null, runwayIntersectionEnd = null;
                        if(isDeparture){
                            gateIntersectionStart = gateStartTime.plusMinutes(gateDepartureSlotDuration);
                            gateIntersectionEnd = gateEndTime;
                            runwayIntersectionStart = runwayStartTime;
                            runwayIntersectionEnd = runwayEndTime.minusMinutes(runwayDepartureSlotDuration);

                        }else{
                            runwayIntersectionStart = runwayStartTime.plusMinutes(runwayArrivalSlotDuration);
                            runwayIntersectionEnd = runwayEndTime;
                            gateIntersectionStart = gateStartTime;
                            gateIntersectionEnd = gateEndTime.minusMinutes(gateArrivalSlotDuration);
                        }

                        LocalDateTime[] overlap = findOverlap(gateIntersectionStart, gateIntersectionEnd, runwayIntersectionStart, runwayIntersectionEnd);
                        if(overlap == null){
                            break;
                        }
                        LocalDateTime intersectionStartTime = overlap[0];
                        LocalDateTime intersectionEndTime = overlap[1];
                        intersectionStartTime = originalTime.isAfter(intersectionStartTime) && originalTime.isBefore(intersectionEndTime) ? originalTime : intersectionStartTime;


                        // Check if the intersecting time range overlaps with the originalTime
                        if ((originalTime.isAfter(intersectionStartTime) || originalTime.isEqual(intersectionStartTime)) && originalTime.isBefore(intersectionEndTime)) {
                            // Calculate the duration between originalTime and the intersecting start time
                            Duration duration = Duration.between(originalTime, intersectionStartTime);

                            // Check if the duration is positive or zero, meaning intersectingStartTime is after or equal to originalTime
                            if (!duration.isNegative()) {
                                long currentDuration = duration.toMinutes();
                                if (currentDuration < closestDuration) {
                                    closestDuration = currentDuration;
                                    closestIntersectingTime = intersectionStartTime;
                                    closestGate = gate;
                                    closestRunway = runway;
                                    if(closestDuration == 0){
                                        finished = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(finished){
                        break;
                    }
                }
                if(finished){
                    break;
                }
            }
            if(finished){
                break;
            }
        }

        if (closestIntersectingTime != null) {
            System.out.println("Closest intersecting time: " + closestIntersectingTime);
            System.out.println("Gate: " + closestGate);
            System.out.println("Runway: " + closestRunway);
            System.out.println();
            // if the flight is being reassigned - delete any old time slots for the flight
            if(reassigning){
                clearOldTimeSlots(flight);
            }
            // assign the chosen runway and gate to the flight, and if the flight is being reassigned log the changes
            // returns a list containing the selected gate and selected runways (updated with their new timeslots)
            return assignFlight(flight, closestIntersectingTime, closestGate, closestRunway, reassigning);
        } else {
            System.out.println("No suitable time found.");
            return null;
        }
    }

    public static LocalDateTime[] findOverlap(LocalDateTime startTime1, LocalDateTime endTime1, LocalDateTime startTime2, LocalDateTime endTime2){
        LocalDateTime startOverlap = startTime1.isAfter(startTime2)? startTime1 : startTime2;
        LocalDateTime endOverlap = endTime1.isBefore(endTime2) ? endTime1 : endTime2;

        if(startOverlap.isBefore(endOverlap)){
            return new LocalDateTime[]{startOverlap, endOverlap};
        }
        // no overlap
        return null;
    }

    private List<Schedulable> assignFlight(Flight flight, LocalDateTime flightTime, Gate availableGate, Runway availableRunway, boolean reassigning){
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

            // return the chosen gate object and runway object updated with the new timeslots
            return new ArrayList<>(List.of(availableGate, availableRunway));
        }
        return null;
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

    // takes a list of gates and a specific Gate object that has had a new timeslot added and needs to be updated in the list
    public List<Gate> updateGatesWithChange(List<Gate> gates, Gate updatedGate){
        // loop through each gate in the list until the matching gate is found
        for(int i = 0; i < gates.size(); i++){
            // if the id of the current gate matches the gate to update - update that gate
            if(gates.get(i).getId().equals(updatedGate.getId())){
                gates.set(i, updatedGate);
                return gates;
            }
        }
        logger.debug("FlightSchedulerService could not find matching gate with id {} in the list to update", updatedGate.getId());
        return null;
    }

    // takes a list of runways and a specific Runway object that has had a new timeslot added and needs to be updated in the list
    public List<Runway> updateRunwaysWithChange(List<Runway> runways, Runway updatedRunway){
        // loop through each runway in the list until the matching runway is found
        for(int i = 0; i < runways.size(); i++){
            // if the id of the current runway matches the runway to update - update that runway
            if(runways.get(i).getId().equals(updatedRunway.getId())){
                runways.set(i, updatedRunway);
                return runways;
            }
        }
        logger.debug("FlightSchedulerService could not find matching runway with id {} in the list to update", updatedRunway.getId());
        return null;
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

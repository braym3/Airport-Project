package com.example.airportproject.service.gates.impl;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.Gate;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.repository.FlightRepo;
import com.example.airportproject.service.gates.GateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GateAssigner {
    private final GateService gateService;
    private final FlightRepo flightRepo;

    @Value("${airportproject.airportcode}")
    String airportCode;
    @Value("${gate.departureSlot}")
    int departureSlotLength;
    @Value("${gate.arrivalSlot}")
    int arrivalSlotLength;
    private final Logger logger = LoggerFactory.getLogger(GateAssigner.class);


    public GateAssigner(GateService gateService, FlightRepo flightRepo) {
        this.gateService = gateService;
        this.flightRepo = flightRepo;
    }

    private boolean isGateAvailable(Gate gate, LocalDateTime startTime, LocalDateTime endTime){
        for(TimeSlot timeSlot : gate.getSchedule()){
            if(timeSlot.overlaps(startTime, endTime)){
                return false;
            }
        }
        return true;
    }

    private Gate findAvailableGate(LocalDateTime startTime, LocalDateTime endTime, List<Gate> gates){
        for(Gate gate : gates){
            // check if the gate is available during the start time and end time
            if(isGateAvailable(gate, startTime, endTime)){
                return gate;
            }
        }
        return null;
    }

    private void assignGate(Flight flight, LocalDateTime startTime, LocalDateTime endTime, Gate availableGate){
        if(availableGate != null){
            // add a new time slot to the gate schedule
            TimeSlot timeSlot = new TimeSlot(availableGate, flight, startTime, endTime, null);
            availableGate.addTimeSlot(timeSlot);
            // add the new time slot to the database
            gateService.addGateSlot(timeSlot);

            // set the Gate ID in the Flight object
            flight.setGate(availableGate);
            flightRepo.update(flight);
        }

    }

    private boolean isDeparture(Flight flight){
        return flight.getDepIata().equals(airportCode);
    }

    private LocalDateTime getStartTime(Flight flight){
        LocalDateTime startTime = null;
        if(isDeparture(flight)){
            // if flight is departing - the flight occupies the gate from a number of minutes (gate departure slot length) before departure until departure time
            startTime = flight.getDepTime().minusMinutes(departureSlotLength);
        }else {
            // if flight is arriving - the flight occupies the gate from arrival time until a number of minutes (gate arrival slot length) after arrival
            startTime = flight.getArrTime();
        }
        return startTime;
    }

    private LocalDateTime getEndTime(Flight flight){
        LocalDateTime endTime = null;
        if(isDeparture(flight)){
            // if flight is departing - the flight occupies the gate from a number of minutes (gate departure slot length) before departure until departure time
            endTime = flight.getDepTime();
        }else {
            // if flight is arriving - the flight occupies the gate from arrival time until a number of minutes (gate arrival slot length) after arrival
            endTime = flight.getArrTime().plusMinutes(arrivalSlotLength);
        }
        return endTime;
    }

    public void assignGatesAndTerminals(){
        // Get the list of sorted flights (both arrivals and departures)
        logger.debug("GateAssigner getting ordered flights with airport code {}", airportCode);
        List<Flight> sortedFlights = flightRepo.getOrderedFlights(airportCode);

        // Get the list of gates at the airport
        logger.debug("GateAssigner getting all gates");
        List<Gate> gates = gateService.getAll();

        for(Flight flight : sortedFlights){
            // set the allotted start and end time of the flight using the gate
            LocalDateTime startTime = getStartTime(flight);
            LocalDateTime endTime = getEndTime(flight);

            // find an available gate for the flight
            Gate availableGate = findAvailableGate(startTime, endTime, gates);
            assignGate(flight, startTime, endTime, availableGate);
        }
    }
}

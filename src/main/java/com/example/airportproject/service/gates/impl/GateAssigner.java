package com.example.airportproject.service.gates.impl;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.Gate;
import com.example.airportproject.repository.FlightRepo;
import com.example.airportproject.repository.GateRepo;
import com.example.airportproject.repository.TerminalRepo;
import com.example.airportproject.service.gates.GateService;
import com.example.airportproject.service.terminals.TerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GateAssigner {
    private final GateService gateService;
    private final TerminalRepo terminalRepo;
    private final TerminalService terminalService;
    private final FlightRepo flightService;

    @Value("${airportproject.airportcode}")
    String airportCode;
    private final Logger logger = LoggerFactory.getLogger(GateAssigner.class);


    public GateAssigner(GateService gateService, TerminalRepo terminalRepo, TerminalService terminalService, FlightRepo flightService) {
        this.gateService = gateService;
        this.terminalRepo = terminalRepo;
        this.terminalService = terminalService;
        this.flightService = flightService;
    }

    private boolean isGateOccupied(UUID gateId, LocalDateTime startTime, LocalDateTime endTime, List<Gate> gates){
        for(Gate gate : gates){
            if(gateId.equals(gate.getId())){
                // check for overlap between gate schedules
//                if (gate.g)
            }
        }
        return true;
    }

    private Gate findAvailableGate(LocalDateTime startTime, LocalDateTime endTime, List<Gate> gates){
        for(Gate gate : gates){
            // check if the gate is available during the start time and end time
            if(isGateOccupied(gate.getId(), startTime, endTime, gates)){
                return gate;
            }
        }
        return null;
    }

    public void assignGatesAndTerminals(){
        // Get the list of sorted flights (both arrivals and departures)
        logger.debug("GateAssigner getting ordered flights with airport code {}", airportCode);
        List<Flight> sortedFlights = flightService.getOrderedFlights(airportCode);

        // Get the list of gates at the airport
        List<Gate> gates = gateService.getAll();

        // iterate over the sorted list of flights
        for(Flight flight : sortedFlights){
            // set the allotted start and end time of using the gate
            LocalDateTime startTime = null, endTime = null;
            // if flight is departing - the start time for using the gate is 45 minutes before departure time
            if(flight.getDepIata().equals(airportCode)){
                startTime = flight.getDepTime().minusMinutes(45);
                endTime = flight.getDepTime();
                // if flight is arriving - the start time for using the gate is arrival time and the gate is allotted for 10 minutes
            }else if(flight.getArrIata().equals(airportCode)){
                startTime = flight.getArrTime();
                endTime = flight.getArrTime().plusMinutes(10);
            }

            // find an available gate for the flight
            Gate gate = findAvailableGate(startTime, endTime, gates);
        }

    }


}

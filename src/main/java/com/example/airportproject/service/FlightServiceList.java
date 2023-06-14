package com.example.airportproject.service;

import com.example.airportproject.model.Flight;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlightServiceList implements FlightService{
    private final Map<UUID, Flight> flights;

    public FlightServiceList() {
        flights = new HashMap<>();
    }

    @Override
    public Flight createFlight(Flight f){
        UUID flightId = f.getId();
        // add Flight to the HashMap, using its UUID as the key
        this.flights.put(flightId, f);
        // return the flight added to the HashMap, find it using its UUID
        return this.flights.get(flightId);
    }

    @Override
    public Flight get(UUID id){
        // return the Flight from the HashMap, using the id as the key
        return this.flights.get(id);
    }

    @Override
    public List<Flight> getAll(){
        // convert map values to list & return flights list
        return new ArrayList<>(flights.values());
    }

    @Override
    public Flight remove(UUID id){
        // remove the flight from the list & return the flight just removed
        return flights.remove(id);
    }

    @Override
    public Flight updateStatus(UUID id, String status){
        // get the flight from the list using the id as index
        Flight f  = flights.get(id);
        // set status attribute
        if(status != null) f.setStatus(status);
        flights.replace(id, f);
        // return the updated flight
        return f;
    }

    @Override
    public List<Flight> getByDepartureAirport(String depIATA) {
        List<Flight> flightsList = new ArrayList<>(flights.values());
        return flightsList.stream().filter(f -> f.getDepIata().equals(depIATA)).toList();
    }

    @Override
    public List<Flight> getByArrivalAirport(String arrIATA) {
        List<Flight> flightsList = new ArrayList<>(flights.values());
        return flightsList.stream().filter(f -> f.getArrIata().equals(arrIATA)).toList();
    }
}

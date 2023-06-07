package com.example.airportproject.service;

import com.example.airportproject.model.Flight;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceList implements FlightService{
    private final List<Flight> flights;

    public FlightServiceList() {
        flights = new ArrayList<>();
    }

    @Override
    public Flight createFlight(Flight f){
        // add flight to list
        this.flights.add(f);
        // return the last flight created (last in the list)
        return this.flights.get(flights.size()-1);
    }

    @Override
    public Flight get(int id){
        // return the flight from the list, using the id as index
        return this.flights.get(id);
    }

    @Override
    public List<Flight> getAll(){
        // return flights list
        return this.flights;
    }

    @Override
    public Flight remove(int id){
        // remove the flight from the list & return the flight just removed
        return this.flights.remove(id);
    }

    @Override
    public Flight updateStatus(int id, String status){
        // get the flight from the list using the id as index
        Flight f  = this.flights.get(id);
        // set status attribute
        if(status != null) f.setStatus(status);
        // return the updated flight
        return f;
    }

    @Override
    public List<Flight> getByDepartureAirport(String depIATA) {
        return this.flights.stream().filter(f -> f.getDepIata().equals(depIATA)).toList();
    }

    @Override
    public List<Flight> getByArrivalAirport(String arrIATA) {
        return this.flights.stream().filter(f -> f.getArrIata().equals(arrIATA)).toList();
    }
}

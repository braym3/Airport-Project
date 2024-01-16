package com.example.airportproject.repository.impl;

import com.example.airportproject.model.Flight;
import com.example.airportproject.repository.FlightRepo;
import com.example.airportproject.repository.impl.mapper.FlightMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class FlightRepoImpl implements FlightRepo {
    private final FlightMapper flightMapper;

    public FlightRepoImpl(FlightMapper flightMapper){
        this.flightMapper = flightMapper;
    }

    @Override
    public Flight create(Flight flight) {
        flightMapper.create(flight);
        return flight;
    }

    @Override
    public List<Flight> getAll() {
//        return populateGates(flightMapper.getAll());
        return flightMapper.getAll();
    }

    @Override
    public Flight get(UUID id) {
        return flightMapper.get(id);
    }

    @Override
    public List<Flight> getDepartures(String depIata) {
        return flightMapper.getDepartures(depIata);
    }

    @Override
    public List<Flight> getArrivals(String arrIata) {
        return flightMapper.getArrivals(arrIata);
    }

    @Override
    public List<Flight> getOrderedFlights(String airportIata) {
        return flightMapper.getOrderedFlights(airportIata);
    }

    @Override
    public Flight update(Flight flight) {
        flightMapper.update(flight);
        return flight;
    }


    @Override
    public Flight remove(UUID id) {
        return flightMapper.remove(id);
    }

    @Override
    public void clear() {
        flightMapper.clear();
    }


    @Override
    public void removeDuplicates() {
        flightMapper.removeDuplicates();
    }

    @Override
    public Flight getFirstFlight(String airportIata) {
        return flightMapper.getFirstFlight(airportIata);
    }

    @Override
    public Flight getLastFlight(String airportIata) {
        return flightMapper.getLastFlight(airportIata);
    }

//    public List<Flight> populateGates(List<Flight> flights){
//        flights.forEach(flight -> flight.setGate(flightMapper.getGateForFlight(flight.getId())));
//        return flights;
//    }
}

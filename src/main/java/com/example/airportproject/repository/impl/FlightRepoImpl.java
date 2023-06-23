package com.example.airportproject.repository.impl;

import com.example.airportproject.model.Flight;
import com.example.airportproject.repository.FlightRepo;
import com.example.airportproject.repository.impl.mapper.FlightMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
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
    public Flight update(Flight flight) {
        return flightMapper.update(flight.getId(), flight.getAirlineIata(), flight.getDepIata(), flight.getDepTerminal(), flight.getDepGate(), flight.getArrIata(), flight.getArrTerminal(), flight.getArrGate(), flight.getStatus(), flight.getAircraftIcao(), flight.getFlightNumber(), flight.getFlightIata(), flight.getDepTime(), flight.getArrTime(), flight.getDuration());
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
}

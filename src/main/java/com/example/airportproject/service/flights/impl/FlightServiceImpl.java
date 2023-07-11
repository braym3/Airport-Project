package com.example.airportproject.service.flights.impl;

import com.example.airportproject.dao.FlightDao;
import com.example.airportproject.model.Flight;
import com.example.airportproject.model.Gate;
import com.example.airportproject.service.flights.FlightService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Primary
@Service
public class FlightServiceImpl implements FlightService {
    private final FlightDao flightDao;

    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    @Transactional
    public Flight createFlight(Flight flight) {
        return flightDao.create(flight);
    }

    @Override
    @Transactional
    public Flight get(UUID id) {
        return flightDao.get(id); //.orElseThrow(FlightNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    @Override
    @Transactional
    public List<Flight> getAll() {
        return flightDao.getAll();
    }

    @Override
    @Transactional
    public Flight remove(UUID id) {
        Flight removed = this.get(id);             // using the get method just made
        flightDao.remove(id);
        return removed;
    }

    @Override
    @Transactional
    public Flight update(UUID id, String airlineIata, String depIata, String arrIata, String status, String aircraftIcao, String flightIata, LocalDateTime depTime, LocalDateTime arrTime, Integer duration, Gate gate) {
        Flight flight = flightDao.get(id); //.orElseThrow(FlightNotFoundException::new);

        // update the flight attributes if the corresponding parameters are provided
        if(airlineIata != null){
            flight.setAirlineIata(airlineIata);
        }
        if(depIata != null){
            flight.setDepIata(depIata);
        }
        if(arrIata != null){
            flight.setArrIata(arrIata);
        }
        if(status != null){
            flight.setStatus(status);
        }
        if(aircraftIcao != null){
            flight.setAircraftIcao(aircraftIcao);
        }
        if(flightIata != null){
            flight.setFlightIata(flightIata);
        }
        if(depTime != null){
            flight.setDepTime(depTime);
        }
        if(arrTime != null){
            flight.setArrTime(arrTime);
        }
        if(duration != null){
            flight.setDuration(duration);
        }
        if(gate != null){
            flight.setGate(gate);
        }
        // save and return the updated flight record
        return flightDao.update(flight);
    }

    @Override
    @Transactional
    public List<Flight> getByDepartureAirport(String depIata) {
        return flightDao.getDepartures(depIata);
    }

    @Override
    @Transactional
    public List<Flight> getByArrivalAirport(String arrIata) {
        return flightDao.getArrivals(arrIata);
    }

    @Override
    @Transactional
    public List<Flight> getOrderedFlights(String airportIata) {
        return flightDao.getOrderedFlights(airportIata);
    }

    @Override
    public LocalDateTime getFirstFlightTime(String airportIata) {
        // get the time of the first flight
        Flight firstFlight = flightDao.getFirstFlight(airportIata);
        if(firstFlight.getDepIata().equals(airportIata)){
            return firstFlight.getDepTime();
        }else{
            return firstFlight.getArrTime();
        }
    }

    @Override
    public LocalDateTime getLastFlightTime(String airportIata) {
        // get the time of the first flight
        Flight lastFlight = flightDao.getLastFlight(airportIata);
        if(lastFlight.getDepIata().equals(airportIata)){
            return lastFlight.getDepTime();
        }else{
            return lastFlight.getArrTime();
        }
    }
}

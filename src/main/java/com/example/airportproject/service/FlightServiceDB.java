package com.example.airportproject.service;

import com.example.airportproject.model.Flight;
import com.example.airportproject.exception.FlightNotFoundException;
import com.example.airportproject.repository.FlightRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Primary
@Service
public class FlightServiceDB implements FlightService{
    private final FlightRepository flightRepo;

    public FlightServiceDB(FlightRepository flightRepo) {
        this.flightRepo = flightRepo;
    }

    @Override
    public Flight createFlight(Flight flight) {
        return flightRepo.save(flight);
    }



    @Override
    public Flight get(UUID id) {
        return flightRepo.findById(id).orElseThrow(FlightNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    @Override
    public List<Flight> getAll() {
        return flightRepo.findAll();
    }

    @Override
    public Flight remove(UUID id) {
        Flight removed = this.get(id);             // using the get method just made
        flightRepo.deleteById(id);
        return removed;
    }

    @Override
    public Flight updateStatus(UUID id, String status) {
        Flight flight = this.get(id);                   // get the Flight by the id

        if(status != null) flight.setStatus(status);       // set status attribute if it's not null

        return flightRepo.save(flight);               // save the updated Flight and return it
    }

    @Override
    public List<Flight> getByDepartureAirport(String depIata) {
        return flightRepo.findFlightsByDepIataOrderByDepTimeAsc(depIata);
    }

    @Override
    public List<Flight> getByArrivalAirport(String arrIata) {
        return flightRepo.findFlightsByArrIataOrderByArrTimeAsc(arrIata);
    }
}

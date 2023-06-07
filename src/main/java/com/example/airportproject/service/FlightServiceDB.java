package com.example.airportproject.service;

import com.example.airportproject.model.Flight;
import com.example.airportproject.exception.FlightNotFoundException;
import com.example.airportproject.repository.FlightRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class FlightServiceDB implements FlightService{
    private final FlightRepository flightRepo;

    public FlightServiceDB(FlightRepository flightRepo) {
        this.flightRepo = flightRepo;
    }

    @Override
    public Flight createFlight(Flight f) {
        return this.flightRepo.save(f);
    }

    @Override
    public Flight get(int id) {
        return this.flightRepo.findById((long)id).orElseThrow(FlightNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    @Override
    public List<Flight> getAll() {
        return this.flightRepo.findAll();
    }

    @Override
    public Flight remove(int id) {
        Flight removed = this.get(id);             // using the get method just made
        this.flightRepo.deleteById((long)id);
        return removed;
    }

    @Override
    public Flight updateStatus(int id, String status) {
        Flight f = this.get(id);                   // get the Flight by the id

        if(status != null) f.setStatus(status);       // set attributes if they're not null

        return this.flightRepo.save(f);               // save the updated Flight and return it
    }

    @Override
    public List<Flight> getByDepartureAirport(String depIata) {
        return this.flightRepo.findFlightsByDepIata(depIata);
    }

    @Override
    public List<Flight> getByArrivalAirport(String arrIata) {
        return this.flightRepo.findFlightsByArrIata(arrIata);
    }
}

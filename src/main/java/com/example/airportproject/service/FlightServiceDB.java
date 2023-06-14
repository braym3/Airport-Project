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

    /**
    * Creates a new record in the database with the data from a Flight object
       * @param f The Flight to persist
       * @return The created Flight
    */
    @Override
    public Flight createFlight(Flight f) {
        return flightRepo.save(f);
    }


    /**
    * Returns the Flight with the given id
       * @param id The id of the flight to find
       * @return Flight object with the matching id
    */
    @Override
    public Flight get(UUID id) {
        return flightRepo.findById(id).orElseThrow(FlightNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    /**
    * Returns a List of all Flights in the database
       * @return List of all Flight objects found
    */
    @Override
    public List<Flight> getAll() {
        return flightRepo.findAll();
    }

    /**
    * Removes the Flight with the corresponding id from the database
       * @param id The id used to identify the Flight to delete
       * @return The deleted Flight object
    */
    @Override
    public Flight remove(UUID id) {
        Flight removed = this.get(id);             // using the get method just made
        flightRepo.deleteById(id);
        return removed;
    }

    /**
    * Updates the status of the flight in the database that corresponds to the provided id
       * @param id The id used to identify the Flight to update
       * @param status The new status of the flight
       * @return The updated Flight object
    */
    @Override
    public Flight updateStatus(UUID id, String status) {
        Flight f = this.get(id);                   // get the Flight by the id

        if(status != null) f.setStatus(status);       // set status attribute if it's not null

        return flightRepo.save(f);               // save the updated Flight and return it
    }

    /**
    * Returns a List of Flights that have the same provided 'depIata' (departure airport code)
       * @param depIata Departure airport code
       * @return List of Flight objects that have the corresponding 'depIata'
    */
    @Override
    public List<Flight> getByDepartureAirport(String depIata) {
        return flightRepo.findFlightsByDepIataOrderByDepTimeAsc(depIata);
    }

    /**
     * Returns a List of Flights that have the same provided 'arrIata' (arrival airport code)
     * @param arrIata Arrival airport code
     * @return List of Flight objects that have the corresponding 'arrIata'
     */
    @Override
    public List<Flight> getByArrivalAirport(String arrIata) {
        return flightRepo.findFlightsByArrIataOrderByArrTimeAsc(arrIata);
    }
}

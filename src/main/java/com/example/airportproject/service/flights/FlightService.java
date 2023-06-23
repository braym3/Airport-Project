package com.example.airportproject.service.flights;

import com.example.airportproject.model.Flight;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Defines the contract for managing flights.
 * It provides operations to fetch flights, add a new flight, remove flights, and update a flight's status.
 */
public interface FlightService {

    /**
     * Creates a new record in the database with the data from a Flight object
     * @param flight The Flight to persist
     * @return The created Flight
     */
    Flight createFlight(Flight flight);

    /**
     * Returns the Flight with the given id
     * @param id The id of the flight to find
     * @return Flight object with the matching id
     */
    Flight get(UUID id);

    /**
     * Returns a List of all Flights in the database
     * @return List of all Flight objects found
     */
    List<Flight> getAll();

    /**
     * Removes the Flight with the corresponding id from the database
     * @param id The id used to identify the Flight to delete
     * @return The deleted Flight object
     */
    Flight remove(UUID id);

    /**
    * Updates the flight record of the corresponding id with the specified attributes
     * @param id
     * @param airlineIata
     * @param depIata
     * @param depTerminal
     * @param depGate
     * @param arrIata
     * @param arrTerminal
     * @param arrGate
     * @param status
     * @param aircraftIcao
     * @param flightNumber
     * @param flightIata
     * @param depTime
     * @param arrTime
     * @param duration
     * @return
    */
    Flight update(UUID id, String airlineIata, String depIata, String depTerminal, String depGate, String arrIata, String arrTerminal, String arrGate, String status, String aircraftIcao, String flightNumber, String flightIata, LocalDateTime depTime, LocalDateTime arrTime, Integer duration);

    /**
     * Returns a List of Flights that have the same provided 'depIata' (departure airport code)
     * @param depIata Departure airport code
     * @return List of Flight objects that have the corresponding 'depIata'
     */
    List<Flight> getByDepartureAirport(String depIata);

    /**
     * Returns a List of Flights that have the same provided 'arrIata' (arrival airport code)
     * @param arrIata Arrival airport code
     * @return List of Flight objects that have the corresponding 'arrIata'
     */
    List<Flight> getByArrivalAirport(String arrIata);
}

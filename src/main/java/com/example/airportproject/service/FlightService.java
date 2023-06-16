package com.example.airportproject.service;

import com.example.airportproject.model.Flight;

import java.util.List;
import java.util.UUID;

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
     * Updates the status of the flight in the database that corresponds to the provided id
     * @param id The id used to identify the Flight to update
     * @param status The new status of the flight
     * @return The updated Flight object
     */
    Flight updateStatus(UUID id, String status);

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

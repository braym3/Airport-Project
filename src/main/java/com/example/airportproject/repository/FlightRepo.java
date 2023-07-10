package com.example.airportproject.repository;

import com.example.airportproject.dto.FlightDTO;
import com.example.airportproject.model.Flight;

import java.util.List;
import java.util.UUID;

public interface FlightRepo {

    Flight create(Flight flight);

    List<Flight> getAll();

    Flight get(UUID id);

    /**
     * Get flights with specific depIATA (departure airport e.g. 'MAN')
     * @param depIata the departure airport code to get the departures for
     * @return List of Flights with the corresponding departure airport code
     */
    List<Flight> getDepartures(String depIata);

    /**
     * Get flights with specific arrIATA (arrival airport e.g. 'MAN')
     * @param arrIata the arrival airport code to get the arrivals for
     * @return List of Flights with the corresponding arrival airport code
     */
    List<Flight> getArrivals(String arrIata);

    List<Flight> getOrderedFlights(String airportIata);

    // flight - updated flight object to save the updated values of
    Flight update(Flight flight);

    Flight remove(UUID id);

    // clear table
    void clear();

    /**
     * Remove duplicate flights - all except the first record of the flight.
     */
    void removeDuplicates();

    Flight getFirstFlight(String airportIata);

    Flight getLastFlight(String airportIata);
}

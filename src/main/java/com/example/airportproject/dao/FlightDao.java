package com.example.airportproject.dao;

import com.example.airportproject.model.Flight;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface FlightDao {
    List<Flight> getAll();

    Flight get(UUID id);

    Flight create(Flight flight);

    Flight update(Flight flight);

    Flight remove(UUID id);

    List<Flight> getDepartures(String depIata);

    List<Flight> getArrivals(String arrIata);

    List<Flight> getOrderedFlights(String airportIata);

    Flight getFirstFlight(String airportIata);

    Flight getLastFlight(String airportIata);

    String fetchData(String endpoint) throws IOException, InterruptedException;
}

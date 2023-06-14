package com.example.airportproject.service;

import com.example.airportproject.model.Flight;

import java.util.List;
import java.util.UUID;

public interface FlightService {
    
    Flight createFlight(Flight f);

    Flight get(UUID id);

    List<Flight> getAll();

    Flight remove(UUID id);

    Flight updateStatus(UUID id, String status);

    List<Flight> getByDepartureAirport(String depIata);

    List<Flight> getByArrivalAirport(String arrIata);
}

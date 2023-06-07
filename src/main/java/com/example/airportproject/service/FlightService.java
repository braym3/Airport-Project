package com.example.airportproject.service;

import com.example.airportproject.model.Flight;

import java.util.List;

public interface FlightService {
    
    Flight createFlight(Flight f);

    Flight get(int id);

    List<Flight> getAll();

    Flight remove(int id);

    Flight updateStatus(int id, String status);

    List<Flight> getByDepartureAirport(String depIata);

    List<Flight> getByArrivalAirport(String arrIata);
}

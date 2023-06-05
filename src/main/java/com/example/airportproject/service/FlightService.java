package com.example.airportproject.service;

import com.example.airportproject.domain.Flight;

import java.util.List;

public interface FlightService {
    
    Flight createFlight(Flight f);

    Flight get(int id);

    List<Flight> getAll();

    Flight remove(int id);

    Flight updateStatus(int id, String status);
}

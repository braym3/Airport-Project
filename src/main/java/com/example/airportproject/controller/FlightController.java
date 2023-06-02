package com.example.airportproject.controller;

import com.example.airportproject.domain.Flight;
import com.example.airportproject.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightController {
    //private FlightService

    private FlightRepository flightRepository;

    @GetMapping("/getAll")
    public List<Flight> getUsers() {
        return flightRepository.findAll();
    }

    // other controller methods
}

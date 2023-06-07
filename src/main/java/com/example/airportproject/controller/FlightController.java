package com.example.airportproject.controller;

import com.example.airportproject.model.Flight;
import com.example.airportproject.exception.FlightNotFoundException;
import com.example.airportproject.repository.FlightRepository;
import com.example.airportproject.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/flights")
public class FlightController {
    private final FlightService flightService;
    private final FlightRepository flightRepository;

    @Autowired
    public FlightController(FlightRepository flightRepository, FlightService flightService) {
        this.flightRepository = flightRepository;
        this.flightService = flightService;

    }

    @GetMapping("/getAll")
    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Flight getFlightById(@PathVariable int id){
        return flightRepository.findById((long) id).orElseThrow(FlightNotFoundException::new);
    }

    @PostMapping("/create")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight f){
        Flight created = this.flightService.createFlight(f);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public Flight updateStatus(@PathVariable int id, @RequestParam(name = "status", required = false) String status){
            return this.flightService.updateStatus(id, status);
    }

    @DeleteMapping("/remove/{id}")
    public Flight remove(@PathVariable int id){
        return this.flightService.remove(id);
    }

    @GetMapping("/get/departures/{depIata}")
    public List<Flight> getByDepartureAirport(@PathVariable String depIata){
        return flightRepository.findFlightsByDepIata(depIata);
    }

    @GetMapping("/get/arrivals/{arrIata}")
    public List<Flight> getByArrivalAirport(@PathVariable String arrIata){
        return flightRepository.findFlightsByArrIata(arrIata);
    }
}

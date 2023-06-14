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

    @GetMapping("/")
    public ResponseEntity<List<Flight>> getFlights() {
        List<Flight> flights = flightRepository.findAll();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable int id){
        Flight found = flightRepository.findById((long) id).orElseThrow(FlightNotFoundException::new);
        return new ResponseEntity<>(found, HttpStatus.FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight f){
        Flight created = flightService.createFlight(f);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Flight> updateStatus(@PathVariable int id, @RequestParam(name = "status", required = false) String status){
            Flight updated = flightService.updateStatus(id, status);
            return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Flight> remove(@PathVariable int id){
        Flight deleted = flightService.remove(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @GetMapping("/departures/{depIata}")
    public ResponseEntity<List<Flight>> getByDepartureAirport(@PathVariable String depIata){
        List<Flight> departures = flightRepository.findFlightsByDepIata(depIata);
        return new ResponseEntity<>(departures, HttpStatus.OK);
    }

    @GetMapping("/arrivals/{arrIata}")
    public ResponseEntity<List<Flight>> getByArrivalAirport(@PathVariable String arrIata){
        List<Flight> arrivals = flightRepository.findFlightsByArrIata(arrIata);
        return new ResponseEntity<>(arrivals, HttpStatus.OK);
    }
}

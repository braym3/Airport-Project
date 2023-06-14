package com.example.airportproject.controller;

import com.example.airportproject.model.Flight;
import com.example.airportproject.exception.FlightNotFoundException;
import com.example.airportproject.repository.FlightRepository;
import com.example.airportproject.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        return ResponseEntity.ok()
                .body(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable int id){
        Flight found = flightRepository.findById((long) id).orElseThrow(FlightNotFoundException::new);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(found);
    }

    @PostMapping("/")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight f){
        Flight created = flightService.createFlight(f);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location)
                .body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Flight> updateStatus(@PathVariable int id, @RequestParam(name = "status", required = false) String status){
            Flight updated = flightService.updateStatus(id, status);
        return ResponseEntity.ok()
                .body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Flight> remove(@PathVariable int id){
        Flight deleted = flightService.remove(id);
        return ResponseEntity.ok()
                .body(deleted);
    }

    @GetMapping("/departures/{depIata}")
    public ResponseEntity<List<Flight>> getByDepartureAirport(@PathVariable String depIata){
        List<Flight> departures = flightRepository.findFlightsByDepIata(depIata);
        return ResponseEntity.ok()
                .body(departures);
    }

    @GetMapping("/arrivals/{arrIata}")
    public ResponseEntity<List<Flight>> getByArrivalAirport(@PathVariable String arrIata){
        List<Flight> arrivals = flightRepository.findFlightsByArrIata(arrIata);
        return ResponseEntity.ok()
                .body(arrivals);
    }
}

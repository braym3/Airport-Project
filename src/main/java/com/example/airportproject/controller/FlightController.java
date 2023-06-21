package com.example.airportproject.controller;

import com.example.airportproject.model.Flight;
import com.example.airportproject.exception.FlightNotFoundException;
import com.example.airportproject.service.FlightService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/flights")
public class FlightController {
    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;

    }

    @GetMapping("/")
    public ResponseEntity<List<Flight>> getFlights() {
        List<Flight> flights = flightService.getAll();
        return ResponseEntity.ok()
                .body(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable UUID id){
        Flight found = flightService.get(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(found);
    }

    @PostMapping("/")
    public ResponseEntity<Flight> createFlight(@Valid @NotNull @RequestBody Flight flight){
        Flight created = flightService.createFlight(flight);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location)
                .body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Flight> updateStatus(@PathVariable UUID id, @RequestParam(name = "status", required = true) String status){
            Flight updated = flightService.updateStatus(id, status);
        return ResponseEntity.ok()
                .body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Flight> remove(@PathVariable UUID id){
        Flight deleted = flightService.remove(id);
        return ResponseEntity.ok()
                .body(deleted);
    }

    @GetMapping("/departures/{depIata}")
    public ResponseEntity<List<Flight>> getByDepartureAirport(@PathVariable String depIata){
        List<Flight> departures = flightService.getByDepartureAirport(depIata);
        return ResponseEntity.ok()
                .body(departures);
    }

    @GetMapping("/arrivals/{arrIata}")
    public ResponseEntity<List<Flight>> getByArrivalAirport(@PathVariable String arrIata){
        List<Flight> arrivals = flightService.getByArrivalAirport(arrIata);
        return ResponseEntity.ok()
                .body(arrivals);
    }
}

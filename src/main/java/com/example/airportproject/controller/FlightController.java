package com.example.airportproject.controller;

import com.example.airportproject.model.Flight;
import com.example.airportproject.service.flights.FlightService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
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
    public ResponseEntity<Flight> update(@PathVariable UUID id,
                                         @RequestParam(name = "airlineIata", required = false) String airlineIata,
                                         @RequestParam(name = "depIata", required = false) String depIata,
                                         @RequestParam(name = "depTerminal", required = false) String depTerminal,
                                         @RequestParam(name = "depGate", required = false) String depGate,
                                         @RequestParam(name = "arrIata", required = false) String arrIata,
                                         @RequestParam(name = "arrTerminal", required = false) String arrTerminal,
                                         @RequestParam(name = "arrGate", required = false) String arrGate,
                                         @RequestParam(name = "status", required = false) String status,
                                         @RequestParam(name = "aircraftIcao", required = false) String aircraftIcao,
                                         @RequestParam(name = "flightNumber", required = false) String flightNumber,
                                         @RequestParam(name = "flightIata", required = false) String flightIata,
                                         @RequestParam(name = "depTime", required = false) LocalDateTime depTime,
                                         @RequestParam(name = "arrTime", required = false) LocalDateTime arrTime,
                                         @RequestParam(name = "duration", required = false) Integer duration){
        Flight updated = flightService.update(id, airlineIata, depIata, depTerminal, depGate, arrIata, arrTerminal, arrGate, status, aircraftIcao, flightNumber, flightIata, depTime, arrTime, duration);
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

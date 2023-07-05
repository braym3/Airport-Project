package com.example.airportproject.controller;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.Gate;
import com.example.airportproject.service.flights.FlightService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Flight>> getFlights() {
        logger.debug("Controller getting all flights");
        List<Flight> flights = flightService.getAll();
        return ResponseEntity.ok()
                .body(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable UUID id){
        logger.debug("Controller getting flight with ID {}", id);
        Flight flight = flightService.get(id);
        return ResponseEntity.ok()
                .body(flight);
    }

    @PostMapping("/")
    public ResponseEntity<Flight> createFlight(@Valid @NotNull @RequestBody Flight flight){
        logger.debug("Controller creating flight");
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
                                         @RequestParam(name = "arrIata", required = false) String arrIata,
                                         @RequestParam(name = "status", required = false) String status,
                                         @RequestParam(name = "aircraftIcao", required = false) String aircraftIcao,
                                         @RequestParam(name = "flightIata", required = false) String flightIata,
                                         @RequestParam(name = "depTime", required = false) LocalDateTime depTime,
                                         @RequestParam(name = "arrTime", required = false) LocalDateTime arrTime,
                                         @RequestParam(name = "duration", required = false) Integer duration,
                                         @RequestParam(name = "gate", required = false) Gate gate){
        logger.debug("Controller updating flight with ID {}", id);
        Flight updated = flightService.update(id, airlineIata, depIata, arrIata, status, aircraftIcao, flightIata, depTime, arrTime, duration, gate);
        return ResponseEntity.ok()
                .body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Flight> remove(@PathVariable UUID id){
        logger.debug("Controller removing flight with ID {}", id);
        Flight deleted = flightService.remove(id);
        return ResponseEntity.ok()
                .body(deleted);
    }

    @GetMapping("/departures/{depIata}")
    public ResponseEntity<List<Flight>> getByDepartureAirport(@PathVariable String depIata){
        logger.debug("Controller getting all departing flights from airport {}", depIata);
        List<Flight> departures = flightService.getByDepartureAirport(depIata);
        return ResponseEntity.ok()
                .body(departures);
    }

    @GetMapping("/arrivals/{arrIata}")
    public ResponseEntity<List<Flight>> getByArrivalAirport(@PathVariable String arrIata){
        logger.debug("Controller getting all arriving flights to airport {}", arrIata);
        List<Flight> arrivals = flightService.getByArrivalAirport(arrIata);
        return ResponseEntity.ok()
                .body(arrivals);
    }

    @GetMapping("/ordered/{airportIata}")
    public ResponseEntity<List<Flight>> getOrderedFlights(@PathVariable String airportIata){
        logger.debug("Controller getting all ordered flights to airport {}", airportIata);
        List<Flight> flights = flightService.getOrderedFlights(airportIata);
        return ResponseEntity.ok()
                .body(flights);
    }

    @GetMapping("/first/{airportIata}")
    public ResponseEntity<LocalDateTime> getFirstFlightTime(@PathVariable String airportIata){
        logger.debug("Controller getting the time of the first flight at airport {}", airportIata);
        LocalDateTime firstFlightTime = flightService.getFirstFlightTime(airportIata);
        return ResponseEntity.ok()
                .body(firstFlightTime);
    }

    @GetMapping("/last/{airportIata}")
    public ResponseEntity<LocalDateTime> getLastFlightTime(@PathVariable String airportIata){
        logger.debug("Controller getting last flight at airport {}", airportIata);
        LocalDateTime lastFlightTime = flightService.getLastFlightTime(airportIata);
        return ResponseEntity.ok()
                .body(lastFlightTime);
    }
}

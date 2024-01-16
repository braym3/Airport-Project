package com.example.airportproject.controller;

import com.example.airportproject.dao.FlightDao;
import com.example.airportproject.model.SimulationTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/time")
public class TimeController {
    private final SimulationTime simulationTime;
    private final Logger logger = LoggerFactory.getLogger(TimeController.class);

    @Autowired
    public TimeController(FlightDao flightDao) {
        this.simulationTime = new SimulationTime(flightDao);
    }

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<LocalDateTime> getCurrentTime() {
        logger.debug("Controller getting current simulation time");
        return ResponseEntity.ok()
                .body(simulationTime.getCurrentTime());
    }

    @CrossOrigin
    @PostMapping("/fast-forward")
    public ResponseEntity<LocalDateTime> fastForwardTime() {
        logger.debug("Controller forwarding simulation time by 1 hour");
        simulationTime.fastForwardOneHour();
        return ResponseEntity.ok()
                .body(simulationTime.getCurrentTime());
    }

}

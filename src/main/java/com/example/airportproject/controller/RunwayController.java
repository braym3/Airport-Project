package com.example.airportproject.controller;

import com.example.airportproject.model.Runway;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.service.runways.RunwayService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/runways")
public class RunwayController {
    private final RunwayService runwayService;
    private final Logger logger = LoggerFactory.getLogger(RunwayController.class);

    @Autowired
    public RunwayController(RunwayService runwayService) {
        this.runwayService = runwayService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/")
    public ResponseEntity<List<Runway>> getRunways() {
        logger.debug("Controller getting all runways");
        List<Runway> runways = runwayService.getAll();
        return ResponseEntity.ok()
                .body(runways);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Runway> getRunwayById(@PathVariable UUID id){
        logger.debug("Controller getting runway with ID {}", id);
        Runway found = runwayService.get(id);
        return ResponseEntity.ok()
                .body(found);
    }

    @CrossOrigin
    @PostMapping("/")
    public ResponseEntity<Runway> createRunway(@Valid @NotNull @RequestBody Runway runway){
        logger.debug("Controller creating runway");
        Runway created = runwayService.createRunway(runway);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location)
                .body(created);
    }

    @CrossOrigin
    @PatchMapping("/{id}")
    public ResponseEntity<Runway> update(@PathVariable UUID id,
                                       @RequestParam(name = "number") int number,
                                       @RequestParam(name = "schedule", required = false) List<TimeSlot> schedule){
        logger.debug("Controller updating runway with ID {}", id);
        Runway updated = runwayService.update(id, number, schedule);
        return ResponseEntity.ok()
                .body(updated);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Runway> remove(@PathVariable UUID id){
        logger.debug("Controller removing runway with ID {}", id);
        Runway deleted = runwayService.remove(id);
        return ResponseEntity.ok()
                .body(deleted);
    }

    @CrossOrigin
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<TimeSlot> getRunwayTimeSlotByFlightId(@PathVariable UUID flightId){
        logger.debug("Controller getting runway time slot for flight ID {}", flightId);
        TimeSlot found = runwayService.getRunwayTimeSlotByFlightId(flightId);
        return ResponseEntity.ok()
                .body(found);
    }

    @CrossOrigin
    @GetMapping("/schedule/{runwayId}")
    public ResponseEntity<List<TimeSlot>> getScheduleForRunway(@PathVariable UUID runwayId){
        logger.debug("Controller getting schedule for runway ID {}", runwayId);
        List<TimeSlot> found = runwayService.getScheduleForRunway(runwayId);
        return ResponseEntity.ok()
                .body(found);
    }

    @CrossOrigin
    @GetMapping("/schedule/")
    public ResponseEntity<List<TimeSlot>> getAllRunwayTimeSlots(){
        logger.debug("Controller getting schedules for all runways");
        List<TimeSlot> found = runwayService.getAllRunwayTimeSlots();
        return ResponseEntity.ok()
                .body(found);
    }
}

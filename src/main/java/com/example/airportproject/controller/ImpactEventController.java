package com.example.airportproject.controller;

import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.service.impactEvents.ImpactEventService;
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
@RequestMapping("api/impactEvents")
public class ImpactEventController {
    private final ImpactEventService impactEventService;
    private final Logger logger = LoggerFactory.getLogger(ImpactEventController.class);

    @Autowired
    public ImpactEventController(ImpactEventService impactEventService) {
        this.impactEventService = impactEventService;
    }

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<List<ImpactEvent>> getImpactEvents() {
        logger.debug("Controller getting all impact events");
        List<ImpactEvent> impactEvents = impactEventService.getAll();
        return ResponseEntity.ok()
                .body(impactEvents);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<ImpactEvent> getImpactEventById(@PathVariable UUID id){
        logger.debug("Controller getting impact event with ID {}", id);
        ImpactEvent impactEvent = impactEventService.get(id);
        return ResponseEntity.ok()
                .body(impactEvent);
    }

    @CrossOrigin
    @PostMapping("/")
    public ResponseEntity<ImpactEvent> createImpactEvent(@Valid @NotNull @RequestBody ImpactEvent impactEvent){
        logger.debug("Controller creating impact event");
        ImpactEvent created = impactEventService.create(impactEvent);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location)
                .body(created);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<ImpactEvent> remove(@PathVariable UUID id){
        logger.debug("Controller removing impact event with ID {}", id);
        ImpactEvent deleted = impactEventService.remove(id);
        return ResponseEntity.ok()
                .body(deleted);
    }

    @CrossOrigin
    @GetMapping("/timeJump")
    public ResponseEntity<List<TimeSlot>> timeJump(){
        logger.debug("Controller randomising impact events");
        // return list of time slots for impact events that happened
        List<TimeSlot> triggeredEvents = impactEventService.triggerImpactEvents();

        return ResponseEntity.ok()
                .body(triggeredEvents);
    }
}

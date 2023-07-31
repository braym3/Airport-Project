package com.example.airportproject.controller;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.Terminal;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.service.gates.GateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/gates")
public class GateController {
    private final GateService gateService;
    private final Logger logger = LoggerFactory.getLogger(GateController.class);

    @Autowired
    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/")
    public ResponseEntity<List<Gate>> getGates() {
        logger.debug("Controller getting all gates");
        List<Gate> gates = gateService.getAll();
        return ResponseEntity.ok()
                .body(gates);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Gate> getGateById(@PathVariable UUID id){
        logger.debug("Controller getting gate with ID {}", id);
        Gate found = gateService.get(id);
        return ResponseEntity.ok()
                .body(found);
    }

    @CrossOrigin
    @PostMapping("/")
    public ResponseEntity<Gate> createGate(@Valid @NotNull @RequestBody Gate gate){
        logger.debug("Controller creating gate");
        Gate created = gateService.createGate(gate);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location)
                .body(created);
    }

    @CrossOrigin
    @PatchMapping("/{id}")
    public ResponseEntity<Gate> update(@PathVariable UUID id,
                                             @RequestParam(name = "number") int number,
                                             @RequestParam(name = "terminal", required = false) Terminal terminal,
                                             @RequestParam(name = "schedule", required = false) List<TimeSlot> schedule){
        logger.debug("Controller updating gate with ID {}", id);
        Gate updated = gateService.update(id, number, terminal, schedule);
        return ResponseEntity.ok()
                .body(updated);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Gate> remove(@PathVariable UUID id){
        logger.debug("Controller removing gate with ID {}", id);
        Gate deleted = gateService.remove(id);
        return ResponseEntity.ok()
                .body(deleted);
    }

    @CrossOrigin
    @GetMapping("/flight/delete/{flightId}")
    public ResponseEntity<TimeSlot> removeGateTimeSlotByFlightId(@PathVariable UUID flightId){
        logger.debug("Controller removing gate time slot for flight ID {}", flightId);
        TimeSlot deleted = gateService.removeGateTimeSlotByFlightId(flightId);
        return ResponseEntity.ok()
                .body(deleted);
    }

    @CrossOrigin
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<TimeSlot> getGateTimeSlotByFlightId(@PathVariable UUID flightId){
        logger.debug("Controller getting gate time slot for flight ID {}", flightId);
        TimeSlot found = gateService.getGateTimeSlotByFlightId(flightId);
        return ResponseEntity.ok()
                .body(found);
    }

    @CrossOrigin
    @GetMapping("/schedule/{gateId}")
    public ResponseEntity<List<TimeSlot>> getScheduleForGate(@PathVariable UUID gateId){
        logger.debug("Controller getting schedule for gate ID {}", gateId);
        List<TimeSlot> found = gateService.getScheduleForGate(gateId);
        return ResponseEntity.ok()
                .body(found);
    }

    @CrossOrigin
    @GetMapping("/schedule/")
    public ResponseEntity<List<TimeSlot>> getAllGatesTimeSlots(){
        logger.debug("Controller getting schedules for all gates");
        List<TimeSlot> found = gateService.getAllGatesTimeSlots();
        return ResponseEntity.ok()
                .body(found);
    }
}
package com.example.airportproject.controller;

import com.example.airportproject.dto.GateDTO;
import com.example.airportproject.dto.GateSlotDTO;
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
    public ResponseEntity<List<GateDTO>> getGates() {
        logger.debug("Controller getting all gates");
        List<Gate> gates = gateService.getAll();
        List<GateDTO> gateDTOs = gateService.convertToDTOList(gates);
        return ResponseEntity.ok()
                .body(gateDTOs);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<GateDTO> getGateById(@PathVariable UUID id){
        logger.debug("Controller getting gate with ID {}", id);
        Gate found = gateService.get(id);
        GateDTO gateDTO = gateService.convertToDTO(found);
        return ResponseEntity.ok()
                .body(gateDTO);
    }

    @CrossOrigin
    @PostMapping("/")
    public ResponseEntity<GateDTO> createGate(@Valid @NotNull @RequestBody Gate gate){
        logger.debug("Controller creating gate");
        Gate created = gateService.createGate(gate);
        GateDTO gateDTO = gateService.convertToDTO(created);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location)
                .body(gateDTO);
    }

    @CrossOrigin
    @PatchMapping("/{id}")
    public ResponseEntity<GateDTO> update(@PathVariable UUID id,
                                             @RequestParam(name = "number") int number,
                                             @RequestParam(name = "terminal", required = false) Terminal terminal,
                                             @RequestParam(name = "schedule", required = false) List<TimeSlot> schedule){
        logger.debug("Controller updating gate with ID {}", id);
        Gate updated = gateService.update(id, number, terminal, schedule);
        GateDTO gateDTO = gateService.convertToDTO(updated);
        return ResponseEntity.ok()
                .body(gateDTO);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<GateDTO> remove(@PathVariable UUID id){
        logger.debug("Controller removing gate with ID {}", id);
        Gate deleted = gateService.remove(id);
        GateDTO gateDTO = gateService.convertToDTO(deleted);
        return ResponseEntity.ok()
                .body(gateDTO);
    }

    @CrossOrigin
    @DeleteMapping("/flight/delete/{flightId}")
    public ResponseEntity<GateSlotDTO> removeGateTimeSlotByFlightId(@PathVariable UUID flightId){
        logger.debug("Controller removing gate time slot for flight ID {}", flightId);
        TimeSlot deleted = gateService.removeGateTimeSlotByFlightId(flightId);
        GateSlotDTO gateSlotDTO = gateService.convertTimeSlotToGateSlotDTO(deleted);
        return ResponseEntity.ok()
                .body(gateSlotDTO);
    }

    @CrossOrigin
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<GateSlotDTO> getGateTimeSlotByFlightId(@PathVariable UUID flightId){
        logger.debug("Controller getting gate time slot for flight ID {}", flightId);
        TimeSlot found = gateService.getGateTimeSlotByFlightId(flightId);
        GateSlotDTO gateSlotDTO = gateService.convertTimeSlotToGateSlotDTO(found);
        return ResponseEntity.ok()
                .body(gateSlotDTO);
    }

    @CrossOrigin
    @GetMapping("/schedule/{gateId}")
    public ResponseEntity<List<GateSlotDTO>> getScheduleForGate(@PathVariable UUID gateId){
        logger.debug("Controller getting schedule for gate ID {}", gateId);
        List<TimeSlot> found = gateService.getScheduleForGate(gateId);
        List<GateSlotDTO> gateSlotDTOs = gateService.convertTimeSlotsToGateSlotDTOList(found);
        return ResponseEntity.ok()
                .body(gateSlotDTOs);
    }

    @CrossOrigin
    @GetMapping("/schedule/")
    public ResponseEntity<List<GateSlotDTO>> getAllGatesTimeSlots(){
        logger.debug("Controller getting schedules for all gates");
        List<TimeSlot> found = gateService.getAllGatesTimeSlots();
        List<GateSlotDTO> gateSlotDTOs = gateService.convertTimeSlotsToGateSlotDTOList(found);
        return ResponseEntity.ok()
                .body(gateSlotDTOs);
    }
}
package com.example.airportproject.controller;

import com.example.airportproject.model.Gate;
import com.example.airportproject.service.gates.GateService;
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
@RequestMapping("api/gates")
public class GateController {
    private final GateService gateService;

    @Autowired
    public GateController(GateService gateService) {
        this.gateService = gateService;

    }

    @GetMapping("/")
    public ResponseEntity<List<Gate>> getGates() {
        List<Gate> gates = gateService.getAll();
        return ResponseEntity.ok()
                .body(gates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gate> getGateById(@PathVariable UUID id){
        Gate found = gateService.get(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(found);
    }

    @PostMapping("/")
    public ResponseEntity<Gate> createGate(@Valid @NotNull @RequestBody Gate gate){
        Gate created = gateService.createGate(gate);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location)
                .body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Gate> updateNumber(@PathVariable UUID id, @RequestParam(name = "number", required = true) int number){
        Gate updated = gateService.updateNumber(id, number);
        return ResponseEntity.ok()
                .body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Gate> remove(@PathVariable UUID id){
        Gate deleted = gateService.remove(id);
        return ResponseEntity.ok()
                .body(deleted);
    }
}
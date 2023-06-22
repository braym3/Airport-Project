package com.example.airportproject.controller;

import com.example.airportproject.model.Terminal;
import com.example.airportproject.service.terminals.TerminalService;
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
@RequestMapping("api/terminals")
public class TerminalController {
    private final TerminalService terminalService;

    @Autowired
    public TerminalController(TerminalService terminalService) {
        this.terminalService = terminalService;

    }

    @GetMapping("/")
    public ResponseEntity<List<Terminal>> getTerminals() {
        List<Terminal> terminals = terminalService.getAll();
        return ResponseEntity.ok()
                .body(terminals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Terminal> getTerminalById(@PathVariable UUID id){
        Terminal found = terminalService.get(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(found);
    }

    @PostMapping("/")
    public ResponseEntity<Terminal> createTerminal(@Valid @NotNull @RequestBody Terminal terminal){
        Terminal created = terminalService.createTerminal(terminal);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location)
                .body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Terminal> updateNumber(@PathVariable UUID id, @RequestParam(name = "number", required = true) int number){
        Terminal updated = terminalService.updateNumber(id, number);
        return ResponseEntity.ok()
                .body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Terminal> remove(@PathVariable UUID id){
        Terminal deleted = terminalService.remove(id);
        return ResponseEntity.ok()
                .body(deleted);
    }
}

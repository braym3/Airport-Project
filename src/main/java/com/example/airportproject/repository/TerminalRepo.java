package com.example.airportproject.repository;

import com.example.airportproject.model.Terminal;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface TerminalRepo{
    Terminal create(Terminal terminal);

    List<Terminal> getAll();

    Terminal get(UUID id);

    // terminal - updated terminal object to save the updated values of
    Terminal update(Terminal terminal);

    Terminal remove(UUID id);

    // clear table
    void clear();
}
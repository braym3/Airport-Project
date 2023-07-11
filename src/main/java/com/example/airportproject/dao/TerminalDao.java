package com.example.airportproject.dao;

import com.example.airportproject.model.Terminal;

import java.util.List;
import java.util.UUID;

public interface TerminalDao {
    List<Terminal> getAll();
    Terminal get(UUID id);
    Terminal create(Terminal terminal, UUID terminalId);
    Terminal update(Terminal terminal);
    Terminal remove(UUID id);
}

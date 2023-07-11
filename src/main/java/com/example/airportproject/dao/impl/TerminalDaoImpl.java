package com.example.airportproject.dao.impl;

import com.example.airportproject.dao.TerminalDao;
import com.example.airportproject.model.Terminal;
import com.example.airportproject.repository.TerminalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TerminalDaoImpl implements TerminalDao {
    private final TerminalRepo terminalRepo;

    @Autowired
    public TerminalDaoImpl(TerminalRepo terminalRepo) {
        this.terminalRepo = terminalRepo;
    }

    @Override
    public List<Terminal> getAll() {
        return terminalRepo.getAll();
    }

    @Override
    public Terminal get(UUID id) {
        return terminalRepo.get(id);
    }

    @Override
    public Terminal create(Terminal terminal, UUID terminalId) {
        return terminalRepo.create(terminal, terminalId);
    }

    @Override
    public Terminal update(Terminal terminal) {
        return terminalRepo.update(terminal);
    }

    @Override
    public Terminal remove(UUID id) {
        return terminalRepo.remove(id);
    }
}

package com.example.airportproject.repository.impl;

import com.example.airportproject.model.Terminal;
import com.example.airportproject.repository.TerminalRepo;
import com.example.airportproject.repository.impl.mapper.TerminalMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class TerminalRepoImpl implements TerminalRepo {

    private final TerminalMapper terminalMapper;

    public TerminalRepoImpl(TerminalMapper terminalMapper){
        this.terminalMapper = terminalMapper;
    }

    @Override
    public Terminal create(Terminal terminal, UUID terminalId) {
        terminal.setId(terminalId);
        terminalMapper.create(terminal);
        return terminal;
    }

    @Override
    public List<Terminal> getAll() {
        return terminalMapper.getAll();
    }

    @Override
    public Terminal get(UUID id) {
        return terminalMapper.get(id);
    }

    @Override
    public Terminal update(Terminal terminal) {
        return terminalMapper.update(terminal.getId(), terminal.getNumber());
    }

    @Override
    public Terminal remove(UUID id) {
        return terminalMapper.remove(id);
    }

    @Override
    public void clear() {
        terminalMapper.clear();
    }
}

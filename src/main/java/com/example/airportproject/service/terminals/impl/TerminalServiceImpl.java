package com.example.airportproject.service.terminals.impl;

import com.example.airportproject.dao.TerminalDao;
import com.example.airportproject.model.Terminal;
import com.example.airportproject.service.terminals.TerminalService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Primary
@Service
public class TerminalServiceImpl implements TerminalService {
    private final TerminalDao terminalDao;

    public TerminalServiceImpl(TerminalDao terminalDao) {
        this.terminalDao = terminalDao;
    }

    @Override
    @Transactional
    public Terminal create(Terminal terminal) {
        UUID terminalId = UUID.randomUUID();
        return terminalDao.create(terminal, terminalId);
    }

    @Override
    @Transactional
    public Terminal get(UUID id) {
        return terminalDao.get(id); //.orElseThrow(TerminalNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    @Override
    @Transactional
    public List<Terminal> getAll() {
        return terminalDao.getAll();
    }

    @Override
    @Transactional
    public Terminal remove(UUID id) {
        Terminal removed = this.get(id);
        terminalDao.remove(id);
        return removed;
    }

    @Override
    @Transactional
    public Terminal updateNumber(UUID id, int number) {
        Terminal terminal = this.get(id);

        terminal.setNumber(number);

        return terminalDao.update(terminal);               // save the updated Gate and return it
    }
}

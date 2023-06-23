package com.example.airportproject.service.terminals.impl;
import com.example.airportproject.exception.TerminalNotFoundException;

import com.example.airportproject.model.Terminal;
import com.example.airportproject.repository.TerminalRepo;
import com.example.airportproject.service.terminals.TerminalService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Primary
@Service
public class TerminalServiceImpl implements TerminalService {
    private final TerminalRepo terminalRepo;

    public TerminalServiceImpl(TerminalRepo terminalRepo) {
        this.terminalRepo = terminalRepo;
    }

    @Override
    public Terminal createTerminal(Terminal terminal) {
        return terminalRepo.create(terminal);
    }



    @Override
    public Terminal get(UUID id) {
        return terminalRepo.get(id); //.orElseThrow(TerminalNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    @Override
    public List<Terminal> getAll() {
        return terminalRepo.getAll();
    }

    @Override
    public Terminal remove(UUID id) {
        Terminal removed = this.get(id);
        terminalRepo.remove(id);
        return removed;
    }

    @Override
    public Terminal updateNumber(UUID id, int number) {
        Terminal terminal = this.get(id);

        terminal.setNumber(number);

        return terminalRepo.update(terminal);               // save the updated Gate and return it
    }
}

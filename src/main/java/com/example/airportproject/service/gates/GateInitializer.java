package com.example.airportproject.service.gates;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.Terminal;
import com.example.airportproject.repository.GateRepo;
import com.example.airportproject.repository.TerminalRepo;
import com.example.airportproject.service.terminals.TerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GateInitializer {
    private final GateRepo gateRepository;
    private final TerminalRepo terminalRepo;
    private final TerminalService terminalService;
    private final Logger logger = LoggerFactory.getLogger(GateInitializer.class);

    @Value("#{${airport.terminals}}")
    Map<Integer, Integer> terminalGates;

    public GateInitializer(GateRepo gateRepository, TerminalRepo terminalRepo, TerminalService terminalService){
        this.gateRepository = gateRepository;
        this.terminalRepo = terminalRepo;
        this.terminalService = terminalService;
    }

    // Create the Gate objects for the provided terminal
    // gateCount - number of gates created for the terminal
    public void createTerminalGates(int gateCount, Terminal terminal){
        for(int i = 1; i <= gateCount; i++){
            logger.debug("GateInitializer creating gate number {} for terminal {}", i, terminal.getNumber());
            Gate gate = new Gate(i, terminal.getId());
            gateRepository.create(gate);
        }
    }

    // Create a terminal with the specified terminal number.
    // Create the gates that are in that terminal based on the provided gateCount of the terminal
    public void createTerminal(int terminalNumber, int gateCount){
        Terminal terminal = new Terminal(terminalNumber);
        logger.debug("GateInitializer creating terminal number {}", terminalNumber);
        terminalService.create(terminal);

        createTerminalGates(gateCount, terminal);
    }


    public void initializeGatesAndTerminals(){
        // clear the gates and terminals tables
        logger.debug("GateInitializer clearing 'gates' and 'terminals' tables");
        gateRepository.clear();
        terminalRepo.clear();

        // iterates through the map of terminal config numbers, creating each terminal and its respective gates
        for(Map.Entry<Integer, Integer> terminal : terminalGates.entrySet()){
            createTerminal(terminal.getKey(), terminal.getValue());
        }
    }
}

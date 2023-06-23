package com.example.airportproject.service.gates;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.Terminal;
import com.example.airportproject.repository.GateRepo;
import com.example.airportproject.repository.TerminalRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GateInitializer {
    private final GateRepo gateRepository;
    private final TerminalRepo terminalRepository;

    @Value("#{${airport.terminals}}")
    Map<Integer, Integer> terminalGates;

    public GateInitializer(GateRepo gateRepository, TerminalRepo terminalRepository){
        this.gateRepository = gateRepository;
        this.terminalRepository = terminalRepository;
    }

    // Create the Gate objects for the provided terminal
    // gateCount - number of gates created for the terminal
    public void createTerminalGates(int gateCount, Terminal terminal){
        for(int i = 1; i <= gateCount; i++){
            Gate gate = new Gate(i, terminal);
            gateRepository.create(gate);
        }
    }

    // Create a terminal with the specified terminal number.
    // Create the gates that are in that terminal based on the provided gateCount of the terminal
    public void createTerminal(int terminalNumber, int gateCount){
        Terminal terminal = new Terminal(terminalNumber);
        terminalRepository.create(terminal);
        createTerminalGates(gateCount, terminal);
    }


    public void initializeGatesAndTerminals(){
        // clear the gates and terminals tables
        gateRepository.clear();
        terminalRepository.clear();

        // iterates through the map of terminal config info, creating each terminal and its respective gates
        for(Map.Entry<Integer, Integer> terminal : terminalGates.entrySet()){
            createTerminal(terminal.getKey(), terminal.getValue());
        }
    }
}

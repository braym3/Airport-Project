package com.example.airportproject.service.runways.impl;

import com.example.airportproject.model.Runway;
import com.example.airportproject.repository.RunwayRepo;
import com.example.airportproject.service.gates.impl.GateInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RunwayInitializer {
    private final RunwayRepo runwayRepo;
    private final Logger logger = LoggerFactory.getLogger(GateInitializer.class);

    @Value("#{${airport.runways}}")
    int runwayAmount;

    public RunwayInitializer(RunwayRepo runwayRepo){
        this.runwayRepo = runwayRepo;
    }

    public void initializeRunways(){
        // clear the runways table
        logger.debug("RunwayInitializer clearing 'runways' table");
        runwayRepo.clear();
        runwayRepo.clearRunwayTimeSlots();

        // create number of runways specified
        for(int i = 1; i <= runwayAmount; i++){
            logger.debug("RunwayInitializer creating runway number {}", i);
            Runway runway = new Runway(i);
            runwayRepo.create(runway);
        }
    }
}

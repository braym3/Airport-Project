package com.example.airportproject.config;

import com.example.airportproject.service.flights.FlightFetchService;
import com.example.airportproject.service.gates.impl.GateAssigner;
import com.example.airportproject.service.gates.impl.GateInitializer;
import com.example.airportproject.service.impactEvents.impl.ImpactEventTimeSlotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

// An application runner that is called 1 time when the application is started -
// fetches the real-time flight data from the external 'AirLabs' api, maps it to Flight objects & persists it
@Component
public class DataInitializerApplicationRunner implements ApplicationRunner {
    private final FlightFetchService flightFetchService;
    private final GateInitializer gateInitializer;
    private final GateAssigner gateAssigner;
    private final ImpactEventTimeSlotService impactEventTimeSlotService;

    // config property for whether to run the application runner (default true)
    @Value("${airportproject.runFetchFlight:false}")
    private boolean runFetchFlight;

    public DataInitializerApplicationRunner(FlightFetchService flightFetchService, GateInitializer gateInitializer, GateAssigner gateAssigner, ImpactEventTimeSlotService impactEventTimeSlotService) {
        this.flightFetchService = flightFetchService;
        this.gateInitializer = gateInitializer;
        this.gateAssigner = gateAssigner;
        this.impactEventTimeSlotService = impactEventTimeSlotService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        if(runFetchFlight){
            flightFetchService.fetchAndPersistFlights();
        }
        gateInitializer.initializeGatesAndTerminals();
        gateAssigner.assignGatesAndTerminals();
        impactEventTimeSlotService.closeRandomGate(UUID.fromString("291a6406-c2ab-473a-b138-225e75ee9c8a"));

    }
}

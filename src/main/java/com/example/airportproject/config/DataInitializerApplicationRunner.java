package com.example.airportproject.config;

import com.example.airportproject.service.flights.FlightFetchService;
//import com.example.airportproject.service.gates.GateInitializer;
import com.example.airportproject.service.gates.GateInitializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

// An application runner that is called 1 time when the application is started -
// fetches the real-time flight data from the external 'AirLabs' api, maps it to Flight objects & persists it
@Component
public class DataInitializerApplicationRunner implements ApplicationRunner {
    private final FlightFetchService flightFetchService;
    private final GateInitializer gateInitializer;

    // config property for whether to run the application runner (default true)
    @Value("${airportproject.runFetchFlight:false}")
    private boolean runFetchFlight;

    public DataInitializerApplicationRunner(FlightFetchService flightFetchService, GateInitializer gateInitializer) {
        this.flightFetchService = flightFetchService;
        this.gateInitializer = gateInitializer;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        if(runFetchFlight){
            flightFetchService.fetchAndPersistFlights();
        }
        gateInitializer.initializeGatesAndTerminals();

    }
}

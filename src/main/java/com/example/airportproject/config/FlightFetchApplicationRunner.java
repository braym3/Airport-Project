package com.example.airportproject.config;

import com.example.airportproject.service.FlightFetchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FlightFetchApplicationRunner implements ApplicationRunner {
    private final FlightFetchService flightFetchService;

    // config property for whether to run the application runner (default true)
    @Value("${airportproject.runFetchFlight:false}")
    private boolean runFetchFlight;

    public FlightFetchApplicationRunner(FlightFetchService flightFetchService) {
        this.flightFetchService = flightFetchService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        if(runFetchFlight){
            flightFetchService.fetchAndPersistFlights();
        }
    }
}

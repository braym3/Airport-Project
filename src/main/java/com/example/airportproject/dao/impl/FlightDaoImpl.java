package com.example.airportproject.dao.impl;

import com.example.airportproject.dao.FlightDao;
import com.example.airportproject.model.Flight;
import com.example.airportproject.repository.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
* Data Access Object for handling flight data retrieval from the external API.
 * Provides methods to fetch flight data for departures and arrivals.
*/
@Repository
public class FlightDaoImpl implements FlightDao {
    private final WebClient webClient;
    private final FlightRepo flightRepo;

    @Value("${airportproject.apikey}")
    String apiKey;
    @Value("${airportproject.airportcode}")
    String airportCode;
    @Value("${airportproject.baseurl}")
    String apiBaseUrl;

    /**
    * Constructs a new FlightDAO.
    */
    @Autowired
    public FlightDaoImpl(FlightRepo flightRepo){
        this.flightRepo = flightRepo;
        this.webClient = WebClient.create();
    }

    @Override
    public List<Flight> getAll() {
        return flightRepo.getAll();
    }

    @Override
    public Flight get(UUID id) {
        return flightRepo.get(id);
    }

    @Override
    public Flight create(Flight flight) {
        return flightRepo.create(flight);
    }

    @Override
    public Flight update(Flight flight) {
        return flightRepo.update(flight);
    }

    @Override
    public Flight remove(UUID id) {
        return flightRepo.remove(id);
    }

    @Override
    public List<Flight> getDepartures(String depIata) {
        return flightRepo.getDepartures(depIata);
    }

    @Override
    public List<Flight> getArrivals(String arrIata) {
        return flightRepo.getArrivals(arrIata);
    }

    @Override
    public List<Flight> getOrderedFlights(String airportIata) {
        return flightRepo.getOrderedFlights(airportIata);
    }

    @Override
    public Flight getFirstFlight(String airportIata) {
        return flightRepo.getFirstFlight(airportIata);
    }

    @Override
    public Flight getLastFlight(String airportIata) {
        return flightRepo.getLastFlight(airportIata);
    }

    /**
     * Builds an asynchronous HTTP GET request object for the specified endpoint parameter
     * @param endpoint the endpoint query parameter for the API request (e.g. "dep_iata" or "arr_iata")
     * @return the constructed WebClient ResponseSpec object
     */
    public WebClient.ResponseSpec buildRequest(String endpoint){
        // uri constructed using stored api details and endpoint parameter
        String uri = apiBaseUrl + apiKey + "&" + endpoint + "=" + airportCode;
        return webClient.get()
                .uri(uri)
                .retrieve();
    }

    /**
    * Fetches flight data from the external API for the specified endpoint parameter.
     * @param endpoint the endpoint query parameter for the API request (e.g. "dep_iata" or "arr_iata")
     * @return a String of the response body from the API
     * @throws IOException if an I/O error occurs while sending the request or receiving the response
     * @throws InterruptedException if the current thread is interrupted while waiting for the response
    */
    @Override
    public String fetchData(String endpoint) throws IOException, InterruptedException {
        WebClient.ResponseSpec responseSpec = buildRequest(endpoint);
        return responseSpec.bodyToMono(String.class).block();
    }
}

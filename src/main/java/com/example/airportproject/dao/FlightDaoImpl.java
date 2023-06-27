package com.example.airportproject.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;


/**
* Data Access Object for handling flight data retrieval from the external API.
 * Provides methods to fetch flight data for departures and arrivals.
*/
@Repository
public class FlightDaoImpl {
    private final WebClient webClient;

    @Value("${airportproject.apikey}")
    String apiKey;
    @Value("${airportproject.airportcode}")
    String airportCode;
    @Value("${airportproject.baseurl}")
    String apiBaseUrl;

    /**
    * Constructs a new FlightDAO.
    */
    public FlightDaoImpl(){
        this.webClient = WebClient.create();
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
    public String fetchData(String endpoint) throws IOException, InterruptedException {
        WebClient.ResponseSpec responseSpec = buildRequest(endpoint);
        return responseSpec.bodyToMono(String.class).block();
    }
}

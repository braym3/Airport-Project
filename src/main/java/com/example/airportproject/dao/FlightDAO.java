package com.example.airportproject.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
* Data Access Object for handling flight data retrieval from the external API.
 * Provides methods to fetch flight data for departures and arrivals.
*/
@Repository
public class FlightDAO {
    private final HttpClient httpClient;

    @Value("${airportproject.apikey}")
    String apiKey;
    @Value("${airportproject.airportcode}")
    String airportCode;
    @Value("${airportproject.baseurl}")
    String apiBaseUrl;

    /**
    * Constructs a new FlightDAO.
    */
    public FlightDAO(){
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
    * Builds an HTTP GET request object for the specified endpoint parameter
     * @param endpoint the endpoint query parameter for the API request (e.g. "dep_iata" or "arr_iata")
     * @return the constructed HttpRequest object
    */
    public HttpRequest buildRequest(String endpoint){
        // uri constructed using stored api details and endpoint parameter
        String url = apiBaseUrl + apiKey + "&" + endpoint + "=" + airportCode;
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
    }

    /**
    * Sends a synchronous HTTP request and returns the response
     * @param request the HttpRequest to send
     * @return the HTTPResponse object containing the response body from the API as a String
     * @throws IOException if an I/O error occurs while receiving the response
     * @throws InterruptedException if the current thread is interrupted while waiting for the response
    */
    public HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()); // synchronous - blocks until response comes
    }

    /**
    * Fetches flight data from the external API for the specified endpoint parameter.
     * @param endpoint the endpoint query parameter for the API request (e.g. "dep_iata" or "arr_iata")
     * @return a String of the response body from the API
     * @throws IOException if an I/O error occurs while sending the request or receiving the response
     * @throws InterruptedException if the current thread is interrupted while waiting for the response
    */
    public String fetchData(String endpoint) throws IOException, InterruptedException {
        HttpResponse<String> response = sendRequest(buildRequest(endpoint));
        return response.body();
    }
}

package com.example.airportproject.service;

import com.example.airportproject.model.Flight;
import com.example.airportproject.repository.FlightRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightFetchService {
    private final FlightRepository flightRepository;
    private ObjectMapper objectMapper;
    @Value("${airportproject.apikey}")
    String apiKey;
    @Value("${airportproject.airportcode}")
    String airportCode;
    @Value("${airportproject.baseurl}")
    String apiBaseUrl;

    public FlightFetchService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
        this.objectMapper = new ObjectMapper();
    }

    /**
        * Returns the String text value of a field in a JSON object, or an empty String if that value is 'null'.
     * Used due to the external API returning the String 'null' for empty values.
     * @param objectNode JSON Object Node
     * @param valueName JSON Object value (field) name
     * @return The String text value from the JSON node or an empty String if the value is 'null'
    */
    public String getTextValue(JsonNode objectNode, String valueName){
        String textValue = objectNode.get(valueName).asText();
        if(textValue.equals("null")) return "";
        return textValue;
    }


    // Extract JSON object nodes (array of flights) from a response String
    public JsonNode extractResponse(String response) throws JsonProcessingException {
        // parse the JSON departures response body
        JsonNode jsonNode = objectMapper.readTree(response);
        // extract the array of flights from the JSON
        return jsonNode.get("response");
    }

    public Flight createFlightFromJsonNode(JsonNode objectNode){
        return new Flight(
                getTextValue(objectNode, "airline_iata"),
                getTextValue(objectNode, "dep_iata"),
                getTextValue(objectNode, "dep_terminal"),
                getTextValue(objectNode, "dep_gate"),
                getTextValue(objectNode, "arr_iata"),
                getTextValue(objectNode, "arr_terminal"),
                getTextValue(objectNode, "arr_gate"),
                getTextValue(objectNode, "status"),
                getTextValue(objectNode, "aircraft_icao"),
                getTextValue(objectNode, "flight_number"),
                getTextValue(objectNode, "flight_iata"),
                OffsetDateTime.parse(objectNode.get("dep_time").asText().replace(" ", "T") + ":00+01"),
                OffsetDateTime.parse(objectNode.get("arr_time").asText().replace(" ", "T") + ":00+01"),
                objectNode.get("duration").asInt()
        );
    }

    // convert JSON flight data from the response into Flight objects in a List
    public List<Flight> parseArray(JsonNode objectsArray){
        List<Flight> flightList = new ArrayList<>();
        // for each flight in the array, deserialize into Flight POJO
        for(JsonNode objectNode : objectsArray){
            // create a new flight object using the values from the current object in the response & add the new flight to the list of flights
            flightList.add(createFlightFromJsonNode(objectNode));
        }
        // return the list of Flights
        return flightList;
    }


    /**
    * Maps the JSON response of flight objects to the Flight model
     * @param departuresResponse A String of the HTTP response from the GET departures request
     * @param arrivalsResponse A String of the HTTP response from the GET arrivals request
     * @return A HashMap containing the corresponding response String and List of Flight objects for each response (departures and arrivals)
     * @throws JsonProcessingException
    */
    public Map<String, List<Flight>> deserializeFlights(String departuresResponse, String arrivalsResponse) throws JsonProcessingException {
        List<Flight> departures = new ArrayList<>();
        List<Flight> arrivals = new ArrayList<>();
        Map<String, List<Flight>> flightData = new HashMap<>();
        // initialise a HashMap with the request response and an empty List of Flights for both departures & arrivals
        flightData.put(departuresResponse, departures);
        flightData.put(arrivalsResponse, arrivals);

        // for each response string in the HashMap, map the JSON objects to Flight objects
        for(String res : flightData.keySet()){
            // extract the JSON array of Flights from the response
            JsonNode objectsArray = extractResponse(res);
            // replace the empty Flights List in the HashMap with a List of the parsed Flight objects
            flightData.replace(res, parseArray(objectsArray));
        }
        return flightData;
    }

    // Builds a GET request with the provided API URL, endpoint, and airport code
    // returns a HttpRequest object
    public HttpRequest buildRequest(String baseUrl, String endpoint, String airportCode){
        // url for the API request constucted using baseUrl, endpoint, and airport code
        String url = baseUrl + "&" + endpoint + "=" + airportCode;
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody()) // specify http method, no body in get request
                .build();
    }

    // Sends a GET request asynchronously to the specified API endpoint with the provided API URL, and airport code
    // returns the HTTPResponse<String> object, containing the response body as a String
    public HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString()); // synchronous - blocks until response comes
    }

    public void persistFlights(Map<String, List<Flight>> flightData){
        // persisting the flight data to the db - adding both departures & arrivals to the flights table
        for (Map.Entry<String, List<Flight>> flightSet : flightData.entrySet()) {
            List<Flight> flightList = flightSet.getValue();
            flightRepository.saveAll(flightList);
        }
    }

    /**
    * Fetches flight data from the external 'AirLabs' API, models the response to Flight objects, and persists the data to a Postgres database.
     * Is called by the application runner (runs once when the application is started)
     * @throws IOException
     * @throws InterruptedException
    */
    public void fetchAndPersistFlights() throws IOException, InterruptedException {
        String apiUrl = apiBaseUrl + apiKey; // provide api key

        // clear the flights table
        flightRepository.deleteAll();

        // build get request for departures and asynchronously send it
        HttpResponse<String> departuresResponse = sendRequest(buildRequest(apiUrl, "dep_iata", airportCode));
        // build get request for arrivals and asynchronously send it
        HttpResponse<String> arrivalsResponse = sendRequest(buildRequest(apiUrl, "arr_iata", airportCode));

        // Deserialization into the `Flight` class
        Map<String, List<Flight>> flightData = deserializeFlights(String.valueOf(departuresResponse.body()), String.valueOf(arrivalsResponse.body()));

        // persisting the flight data to the db - adding both departures & arrivals to the flights table
        persistFlights(flightData);

        // remove duplicate flights - all except the first record of the flight
        flightRepository.removeDuplicates();
    }
}



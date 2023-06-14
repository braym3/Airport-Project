package com.example.airportproject.service;

import com.example.airportproject.model.Flight;
import com.example.airportproject.repository.FlightRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Service
public class FlightFetchService {
    private final FlightRepository flightRepository;

    public FlightFetchService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
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


    /**
    * Maps the JSON response of flight objects to the Flight model
     * @param departuresResponse A String of the HTTP response from the GET departures request
     * @param arrivalsResponse A String of the HTTP response from the GET arrivals request
     * @return A HashMap containing the corresponding response String and List of Flight objects for each response (departures and arrivals)
     * @throws JsonProcessingException
    */
    public HashMap<String, List<Flight>> mapToFlights(String departuresResponse, String arrivalsResponse) throws JsonProcessingException {
        List<Flight> departures = new ArrayList<>();
        List<Flight> arrivals = new ArrayList<>();
        HashMap<String, List<Flight>> flightData = new HashMap<>();
        // initialise a hashmap with the request response and an empty list of flights for both departures & arrivals
        flightData.put(departuresResponse, departures);
        flightData.put(arrivalsResponse, arrivals);

        // for each response string in the hashmap, map the json objects to Flight objects
        for(String res : flightData.keySet()){
            ObjectMapper objectMapper = new ObjectMapper();
            // parse the JSON departures response body
            JsonNode jsonNode = objectMapper.readTree(res);
            // extract the array of flights from the JSON
            JsonNode objectsArray = jsonNode.get("response");
            // iterate over each flight in the array
            for(JsonNode objectNode : objectsArray){
                // get the current flight list (departures or arrivals)
                List<Flight> fList = flightData.get(res);
                // extract the flight details needed
                // create a flight object for each flight in the response
                // if list not empty - check that the previous flight is not duplicate
                if(!fList.isEmpty()){
                    Flight prev = fList.get(fList.size() - 1);
                    // check prev flight does not have same dep airport, dep gate & dep time - duplicate of flight
                    if(((prev.getDepIata().equals(objectNode.get("dep_iata").asText())) &&
                            (prev.getDepGate().equals(objectNode.get("dep_gate").asText())) &&
                            (prev.getDepTime().equals(OffsetDateTime.parse(objectNode.get("dep_time").asText().replace(" ", "T") + ":00+01")))
                    )){
                        continue;
                    }
                }
                fList.add(new Flight(
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
                ));
            }
        }
        return flightData;
    }

    /**
    * Fetches flight data from the external 'AirLabs' API, models the response to Flight objects, and persists the data to a Postgres database.
     * Is called by the application runner (runs once when the application is started)
     * @throws IOException
     * @throws InterruptedException
    */
    public void fetchAndPersistFlights() throws IOException, InterruptedException {
        String apiKey = "c5de155c-c17e-47fa-9eb1-500a6d74ffae"; // need to be stored securely
        String airportCode = "MAN";
        String apiUrl = "https://airlabs.co/api/v9/schedules?api_key=" + apiKey; // provide api key

        // clear the flights table
        flightRepository.deleteAll();

        // build the departures get request
        HttpRequest departuresRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "&dep_iata=" + airportCode)) // provide the url - give the API endpoint
                .method("GET", HttpRequest.BodyPublishers.noBody()) // specify http method, no body in get request
                .build();

        // build the arrivals get request
        HttpRequest arrivalsRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "&arr_iata=" + airportCode)) // provide the url - give the API endpoint
                .method("GET", HttpRequest.BodyPublishers.noBody()) // specify http method, no body in get request
                .build();

        // send the request & save the response - departures
        HttpResponse<String> departuresResponse = HttpClient.newHttpClient()
                .send(departuresRequest, HttpResponse.BodyHandlers.ofString()); // synchronous - blocks until response comes

        // send the request & save the response - arrivals
        HttpResponse<String> arrivalsResponse = HttpClient.newHttpClient()
                .send(arrivalsRequest, HttpResponse.BodyHandlers.ofString());

        // Deserialization into the `Flight` class
        HashMap<String, List<Flight>> flightData = mapToFlights(String.valueOf(departuresResponse.body()), String.valueOf(arrivalsResponse.body()));

        // persisting the flight data to the db - adding both departures & arrivals to the flights table
        for (HashMap.Entry<String, List<Flight>> flightSet : flightData.entrySet()) {
            List<Flight> flightList = flightSet.getValue();
            flightRepository.saveAll(flightList);
        }

        // remove duplicate flights - all except the first record of the flight
        flightRepository.removeDuplicates();
    }
}



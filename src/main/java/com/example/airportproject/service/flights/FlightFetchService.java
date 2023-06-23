package com.example.airportproject.service.flights;

import com.example.airportproject.dao.FlightDaoImpl;
import com.example.airportproject.model.Flight;
import com.example.airportproject.repository.FlightRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Service class for fetching flight data from an external API, mapping it to Flight objects,
 * and persisting the data to a database
*/
@Service
public class FlightFetchService {
    private final FlightRepo flightRepository;
    private final FlightDaoImpl flightDAO;
    private final ObjectMapper objectMapper;
    private final DateTimeFormatter timeFormatter;
    private static final Logger logger = LoggerFactory.getLogger(FlightFetchService.class);



    /**
    * Constructs a new FlightFetchService with the specified FlightRepository and FlightDAO
     * @param flightRepository the FlightRepository to use for persisting the flight data
     * @param flightDAO the FlightDAO to use for fetching flight data from the external API
    */
    public FlightFetchService(FlightRepo flightRepository, FlightDaoImpl flightDAO) {
        this.flightRepository = flightRepository;
        this.flightDAO = flightDAO;
        this.objectMapper = new ObjectMapper();
        this.timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
    * Creates a Flight object from a JSON object node
     * @param objectNode the JSON object node representing a Flight
     * @return the created Flight object
    */
    public Flight createFlightFromJson(JsonNode objectNode){
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
                LocalDateTime.parse(objectNode.get("dep_time_utc").asText(), timeFormatter),
                LocalDateTime.parse(objectNode.get("arr_time_utc").asText(), timeFormatter),
                objectNode.get("duration").asInt()
        );
    }

    /**
    * Parses the JSON flight data response and creates a List of Flight objects.
     * @param jsonResponse the JSON response string
     * @return a List of Flight objects parsed from the JSON response
     * @throws JsonProcessingException if an error occurs while processing the JSON responses
    */
    public List<Flight> parseFlights(String jsonResponse) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode objectsArray = jsonNode.get("response");

        List<Flight> flights = new ArrayList<>();
        if(objectsArray != null){
            // for each flight in the object array, deserialize into Flight POJOs
            for(JsonNode objectNode : objectsArray){
                // create a new Flight object using the current object's values and add it to the List of Flights
                flights.add(createFlightFromJson(objectNode));
            }
        }

        return flights;
    }


    /**
    * Maps the JSON response of flight objects to the Flight model.
     * This method parses the JSON responses for departures and arrivals, creates Flight objects
     * from the parsed data, and returns a Map containing the response Strings and Lists of Flight objects
     * @param departuresResponse a String of the HTTP response body from the GET departures request
     * @param arrivalsResponse a String of the HTTP response body from the GET arrivals request
     * @return a Map containing the corresponding response String and List of Flight objects for each response (departures and arrivals)
     * @throws JsonProcessingException if an error occurs while processing the JSON responses
    */
    public Map<String, List<Flight>> deserializeFlights(String departuresResponse, String arrivalsResponse) throws JsonProcessingException {
        List<Flight> departures = parseFlights(departuresResponse);
        List<Flight> arrivals = parseFlights(arrivalsResponse);
        Map<String, List<Flight>> flightData = new HashMap<>();

        flightData.put(departuresResponse, departures);
        flightData.put(arrivalsResponse, arrivals);

        return flightData;
    }

    public void persistFlights(Map<String, List<Flight>> flightData){
        // persisting the flight data to the db - adding both departures & arrivals to the flights table
        for (Map.Entry<String, List<Flight>> flightSet : flightData.entrySet()) {
            List<Flight> flightList = flightSet.getValue();
            for(Flight flight : flightList){
                flightRepository.create(flight);
            }
        }
    }

    /**
    * Fetches flight data from the external API, models the response to Flight objects,
     * and persists the data to a database.
     * This method is called by the application runner (runs once when the application is started).
     * @throws IOException if an I/O error occurs while sending the request or receiving the response
     * @throws InterruptedException if the current thread is interrupted while waiting for the response
    */
    public void fetchAndPersistFlights() throws IOException, InterruptedException {
        // clear the flights table
        flightRepository.clear();

        try{
            // build get request for departures and asynchronously send it
            String departuresResponse = flightDAO.fetchData("dep_iata");
            // build get request for arrivals and asynchronously send it
            String arrivalsResponse = flightDAO.fetchData("arr_iata");

            // Deserialization into the `Flight` class
            Map<String, List<Flight>> flightData = deserializeFlights(departuresResponse, arrivalsResponse);

            // persisting the flight data to the db - adding both departures & arrivals to the flights table
            persistFlights(flightData);
            // remove duplicate flights - all except the first record of the flight
            flightRepository.removeDuplicates();
        }catch (IOException e){
            logger.error("An IOException occurred whilst fetching flight data", e);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            logger.error("An IOException occurred whilst fetching flight data", e);
        }
    }
}
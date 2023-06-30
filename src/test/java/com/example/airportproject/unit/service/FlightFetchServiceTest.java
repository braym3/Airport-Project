package com.example.airportproject.unit.service;

import com.example.airportproject.dao.impl.FlightDaoImpl;
import com.example.airportproject.repository.FlightRepo;
import com.example.airportproject.service.flights.FlightFetchService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FlightFetchServiceTest {

    @Mock
    private FlightRepo flightRepository;
    @Mock
    private FlightDaoImpl flightDAO;

    private FlightFetchService flightFetchService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        flightFetchService = new FlightFetchService(flightRepository, flightDAO);
    }

//    @Test
//    void fetchAndPersistFlights_SuccessfulRequest_FlightDataSavedToRepository() throws IOException, InterruptedException {
//        // Mock response from external API
//        String departuresResponse = "{\"response\": [{\"airline_iata\": \"BA\",\"airline_icao\": \"BAW\",\"flight_iata\": \"BA7804\",\"flight_icao\": \"BAW7804\",\"flight_number\": \"7804\",\"dep_iata\": \"MAN\",\"dep_icao\": \"EGCC\",\"dep_terminal\": \"3\",\"dep_gate\": \"36\",\"dep_time\": \"2023-06-16 12:35\",\"dep_time_utc\": \"2023-06-16 11:35\",\"dep_estimated\": \"2023-06-16 12:31\",\"dep_estimated_utc\": \"2023-06-16 11:31\",\"dep_actual\": \"2023-06-16 12:31\",\"dep_actual_utc\": \"2023-06-16 11:31\",\"arr_iata\": \"IOM\",\"arr_icao\": \"EGNS\",\"arr_terminal\": null,\"arr_gate\": null,\"arr_baggage\": null,\"arr_time\": \"2023-06-16 13:25\",\"arr_time_utc\": \"2023-06-16 12:25\",\"cs_airline_iata\": \"LM\",\"cs_flight_number\": \"694\",\"cs_flight_iata\": \"LM694\",\"status\": \"landed\",\"duration\": 50,\"delayed\": null,\"dep_delayed\": null,\"arr_delayed\": null,\"aircraft_icao\": null,\"arr_time_ts\": 1686918300,\"dep_time_ts\": 1686915300,\"dep_estimated_ts\": 1686915060,\"dep_actual_ts\": 1686915060}]}";
//        String arrivalsResponse = "{\"response\": [{\"airline_iata\": \"EZ\",\"airline_icao\": \"EZY\",\"flight_iata\": \"EZ7804\",\"flight_icao\": \"EZY7804\",\"flight_number\": \"7804\",\"dep_iata\": \"DLM\",\"dep_icao\": \"EGCC\",\"dep_terminal\": \"3\",\"dep_gate\": \"36\",\"dep_time\": \"2023-06-16 12:35\",\"dep_time_utc\": \"2023-06-16 11:35\",\"dep_estimated\": \"2023-06-16 12:31\",\"dep_estimated_utc\": \"2023-06-16 11:31\",\"dep_actual\": \"2023-06-16 12:31\",\"dep_actual_utc\": \"2023-06-16 11:31\",\"arr_iata\": \"MAN\",\"arr_icao\": \"EGNS\",\"arr_terminal\": null,\"arr_gate\": null,\"arr_baggage\": null,\"arr_time\": \"2023-06-16 13:25\",\"arr_time_utc\": \"2023-06-16 12:25\",\"cs_airline_iata\": \"LM\",\"cs_flight_number\": \"283\",\"cs_flight_iata\": \"SJ283\",\"status\": \"landed\",\"duration\": 50,\"delayed\": null,\"dep_delayed\": null,\"arr_delayed\": null,\"aircraft_icao\": null,\"arr_time_ts\": 1686918300,\"dep_time_ts\": 1686915300,\"dep_estimated_ts\": 1686915060,\"dep_actual_ts\": 1686915060}]}";
//
//        // Mock DAO methods
//        when(flightDAO.fetchData("dep_iata")).thenReturn(departuresResponse);
//        when(flightDAO.fetchData("arr_iata")).thenReturn(arrivalsResponse);
//
//        // Mock repository methods
//        doNothing().when(flightRepository).clear();
//
//        doAnswer(invocation -> {
//            List<Flight> flights = invocation.getArgument(0);
//            for(Flight flight : flights){
//                flightRepository.create(flight);
//            }
//            return null;
//        }).when(flightRepository).saveAll(anyList());
//
//        doNothing().when(flightRepository).removeDuplicates();
//
//        // Call fetch and persist flights method
//        flightFetchService.fetchAndPersistFlights();
//
//        // Verify the flight data is saved to repository
//        verify(flightRepository, times(1)).clear();
//        verify(flightRepository, times(2)).saveAll(anyList());
//        verify(flightRepository, times(1)).removeDuplicates();
//    }
//
//    @Test
//    void fetchAndPersistFlights_ExceptionThrown_ExceptionHandled() throws IOException, InterruptedException {
//        // Mock IOException when fetching flight data
//        when(flightDAO.fetchData(anyString())).thenThrow(new IOException("Failed to fetch data"));
//
//        // Call fetch and persist flights method - assert to not throw exception and fail test
//        assertDoesNotThrow(() -> flightFetchService.fetchAndPersistFlights());
//
//        // verify the exception is caught and handled
//        verify(flightRepository, times(1)).deleteAll();
//        verify(flightRepository, never()).saveAll(anyList());
//        verify(flightRepository, never()).removeDuplicates();
//    }
}
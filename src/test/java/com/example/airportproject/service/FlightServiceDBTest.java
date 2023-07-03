package com.example.airportproject.service;

import com.example.airportproject.repository.FlightRepo;
import com.example.airportproject.service.flights.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // will run the application normally to test it properly - makes sure port is not being used
class FlightServiceDBTest {

    @Autowired // injects a bean into a bean
    private FlightService service;

    @MockBean // make a mock repo
    private FlightRepo repo;

//    @Test
//    void createFlight() {
//
//        Flight toCreate = new Flight("U2", "MAN", "2", "K31", "SPU", "1", "J12", "scheduled", "AT76", "2142", "U22142", OffsetDateTime.parse("2023-06-06T08:00:00+01"), OffsetDateTime.parse("2023-06-06T11:55:00+01"), 235);
//        Flight created = new Flight(1L, "U2", "MAN", "2", "K31", "SPU", "1", "J12", "scheduled", "AT76", "2142", "U22142", OffsetDateTime.parse("2023-06-06T08:00:00+01"), OffsetDateTime.parse("2023-06-06T11:55:00+01"), 235);
//
//        Mockito.when(this.repo.save(toCreate)).thenReturn(created);
//
//        assertEquals(created, this.service.createFlight(toCreate));
//    }
//
//    @Test
//    void get() {
//        int id = 1;
//        Flight f = new Flight((long) id, "E6", "RIX", "2", "I12", "MAN", "1", "F11", "scheduled", "AT76", "2938", "E62938", OffsetDateTime.parse("2023-06-06T10:45:00+01"), OffsetDateTime.parse("2023-06-06T15:25:00+01"), 160);
//        Mockito.when(this.repo.findById((long) id)).thenReturn(Optional.of(f));
//
//        assertEquals(f, this.service.get(id));
//    }
//
//    @Test
//    void getAll() {
//        List<Flight> flights = List.of(new Flight(1L, "U2", "MAN", "2", "K31", "SPU", "1", "J12", "scheduled", "AT76", "2142", "U22142", OffsetDateTime.parse("2023-06-06T08:00:00+01"), OffsetDateTime.parse("2023-06-06T11:55:00+01"), 235));
//        Mockito.when(this.repo.findAll()).thenReturn(flights);
//
//        assertEquals(flights, this.service.getAll());
//    }
//
//    @Test
//    void remove() {
//        int id = 1;
//        Flight f = new Flight((long) id, "U2", "MAN", "2", "K31", "SPU", "1", "J12", "scheduled", "AT76", "2142", "U22142", OffsetDateTime.parse("2023-06-06T08:00:00+01"), OffsetDateTime.parse("2023-06-06T11:55:00+01"), 235);
//        Mockito.when(this.repo.findById((long) id)).thenReturn(Optional.of(f));
//
//        assertEquals(f, this.service.remove(id));
//    }
//
//    @Test
//    void updateStatus() {
//        int id = 1;
//        Flight existing = new Flight((long) id, "U2", "MAN", "2", "K31", "SPU", "1", "J12", "scheduled", "AT76", "2142", "U22142", OffsetDateTime.parse("2023-06-06T08:00:00+01"), OffsetDateTime.parse("2023-06-06T11:55:00+01"), 235);
//        Flight updated = new Flight((long) id, "U2", "MAN", "2", "K31", "SPU", "1", "J12", "cancelled", "AT76", "2142", "U22142", OffsetDateTime.parse("2023-06-06T08:00:00+01"), OffsetDateTime.parse("2023-06-06T11:55:00+01"), 235);
//
//        Mockito.when(this.repo.findById((long) id)).thenReturn(Optional.of(existing)); // Make an optional flight (if not equal to null)
//        Mockito.when(this.repo.save(Mockito.any(Flight.class))).thenReturn(updated); // Mock the save method of the repo
//
//        assertEquals(updated, this.service.updateStatus(id, updated.getStatus()));
//    }
//
//    @Test
//    void getByDepartureAirport() {
//        String dep_airport = "MAN";
//        List<Flight> departures = List.of(new Flight(1L, "U2", dep_airport, "2", "K31", "SPU", "1", "J12", "scheduled", "AT76", "2142", "U22142", OffsetDateTime.parse("2023-06-06T08:00:00+01"), OffsetDateTime.parse("2023-06-06T11:55:00+01"), 235));
//        Mockito.when(this.repo.findFlightsByDepIata(dep_airport)).thenReturn(departures);
//
//        assertEquals(departures, this.service.getByDepartureAirport(dep_airport));
//      }
//
//    @Test
//    void getByArrivalAirport() {
//        String arr_airport = "MAN";
//        List<Flight> arrivals = List.of(new Flight(1L, "U2", "SPU", "2", "K31", arr_airport, "1", "J12", "scheduled", "AT76", "2142", "U22142", OffsetDateTime.parse("2023-06-06T08:00:00+01"), OffsetDateTime.parse("2023-06-06T11:55:00+01"), 235));
//        Mockito.when(this.repo.findFlightsByArrIata(arr_airport)).thenReturn(arrivals);
//
//        assertEquals(arrivals, this.service.getByArrivalAirport(arr_airport));
//    }
}
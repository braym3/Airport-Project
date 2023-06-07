package com.example.airportproject.repository;

import com.example.airportproject.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findFlightsByDepIata(String depIata); // get departures with specific depIATA (departure airport e.g. 'MAN')

    List<Flight> findFlightsByArrIata(String arrIata); // get arrivals with specific arrIATA (arrival airport e.g. 'MAN')
}

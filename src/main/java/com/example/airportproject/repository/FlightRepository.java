package com.example.airportproject.repository;

import com.example.airportproject.model.Flight;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /**
     * Get flights with specific depIATA (departure airport e.g. 'MAN')
     * @param depIata
     * @return List of flights with that corresponding 'depIata'
    */
    List<Flight> findFlightsByDepIata(String depIata); // get departures with specific depIATA (departure airport e.g. 'MAN')

    /**
     * Get flights with specific arrIATA (arrival airport e.g. 'MAN')
     * @param arrIata
     * @return List of flights with that corresponding 'arrIata'
     * */
    List<Flight> findFlightsByArrIata(String arrIata); // get arrivals with specific arrIATA (arrival airport e.g. 'MAN')


    /**
    * Remove duplicate flights - all except the first record of the flight.
    */
    @Modifying
    @Query(value = "DELETE FROM flights WHERE id NOT IN (SELECT min_id FROM ( SELECT MIN(id) AS min_id FROM flights GROUP BY dep_time, dep_iata, dep_gate, arr_iata ) AS grouped_data )",
    nativeQuery = true)
    void removeDuplicates(); // remove duplicate flights - all except the first record of the flight
}

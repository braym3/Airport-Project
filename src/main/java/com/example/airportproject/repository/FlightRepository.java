package com.example.airportproject.repository;

import com.example.airportproject.model.Flight;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface FlightRepository extends JpaRepository<Flight, UUID> {


    /**
     * Get flights with specific depIATA (departure airport e.g. 'MAN')
     * @param depIata
     * @return List of flights with that corresponding 'depIata'
    */
    List<Flight> findFlightsByDepIataOrderByDepTimeAsc(String depIata); // get departures with specific depIATA (departure airport e.g. 'MAN')

    /**
     * Get flights with specific arrIATA (arrival airport e.g. 'MAN')
     * @param arrIata
     * @return List of flights with that corresponding 'arrIata'
     * */
    List<Flight> findFlightsByArrIataOrderByArrTimeAsc(String arrIata); // get arrivals with specific arrIATA (arrival airport e.g. 'MAN')


    /**
    * Remove duplicate flights - all except the first record of the flight.
    */
    @Modifying
    @Query(value = "DELETE FROM FLIGHTS WHERE id IN ( SELECT id FROM ( SELECT *, ROW_NUMBER() OVER ( PARTITION BY dep_iata, dep_gate, dep_time, arr_time ) AS row_num FROM flights ) AS sub WHERE row_num > 1 )",
    nativeQuery = true)
    void removeDuplicates(); // remove duplicate flights - all except the first record of the flight
}

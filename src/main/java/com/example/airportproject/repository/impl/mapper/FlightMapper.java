package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Flight;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface FlightMapper {

    @Insert(
            "INSERT INTO flights (airline_iata, dep_iata, arr_iata, status, aircraft_icao, flight_iata, dep_time, arr_time, duration, gate_id) VALUES (#{airlineIata}, #{depIata}, #{arrIata}, #{status}, #{aircraftIcao}, #{flightIata}, #{depTime}, #{arrTime}, #{duration}, #{gate.id})")
    void create(Flight flight);

    @Select("SELECT id, airline_iata, dep_iata, arr_iata, status, aircraft_icao, flight_iata, dep_time, arr_time, duration, gate_id FROM flights")
    @Results(id="flightResults", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "airlineIata", column = "airline_iata"),
            @Result(property = "depIata", column = "dep_iata"),
            @Result(property = "arrIata", column = "arr_iata"),
            @Result(property = "status", column = "status"),
            @Result(property = "aircraftIcao", column = "aircraft_icao"),
            @Result(property = "flightIata", column = "flight_iata"),
            @Result(property = "depTime", column = "dep_time"),
            @Result(property = "arrTime", column = "arr_time"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "gate", column = "gate_id", one = @One(select = "com.example.airportproject.repository.impl.mapper.GateMapper.get"))
    })
    List<Flight> getAll();

//    @Select("SELECT gates.id, gates.number FROM gates LEFT JOIN flights ON gates.id = flights.gate_id WHERE flights.id = #{flightId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
//    @Results(value = {
//            @Result(property = "id", column = "id", javaType = UUID.class),
//            @Result(property = "number", column = "number"),
//            //@Result(property = "terminalId", column = "terminal_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class)
//    })
//    Gate selectGateForFlight(@Param("flightId") UUID flightId);

    @ResultMap("flightResults")
    @Select("SELECT id, airline_iata, dep_iata, arr_iata, status, aircraft_icao, flight_iata, dep_time, arr_time, duration, gate_id FROM flights WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Flight get(@Param("id") UUID id);

    @ResultMap("flightResults")
    @Select("SELECT id, airline_iata, dep_iata, arr_iata, status, aircraft_icao, flight_iata, dep_time, arr_time, duration, gate_id FROM flights WHERE dep_iata = #{depIata} ORDER BY dep_time ASC")
    List<Flight> getDepartures(@Param("depIata") String depIata);

    @ResultMap("flightResults")
    @Select("SELECT id, airline_iata, dep_iata, arr_iata, status, aircraft_icao, flight_iata, dep_time, arr_time, duration, gate_id FROM flights WHERE arr_iata = #{arrIata} ORDER BY arr_time ASC")
    List<Flight> getArrivals(@Param("arrIata") String arrIata);

    @ResultMap("flightResults")
    @Select("SELECT id, airline_iata, dep_iata, arr_iata, status, aircraft_icao, flight_iata, dep_time, arr_time, duration, gate_id FROM flights ORDER BY CASE WHEN dep_iata = #{airportIata} THEN dep_time WHEN arr_iata = #{airportIata} THEN arr_time END")
    List<Flight> getOrderedFlights(@Param("airportIata") String airportIata);

    @ResultMap("flightResults")
    @Select("SELECT id, airline_iata, dep_iata, arr_iata, status, aircraft_icao, flight_iata, dep_time, arr_time, duration, gate_id FROM flights ORDER BY CASE WHEN dep_iata = #{airportIata} THEN dep_time WHEN arr_iata = #{airportIata} THEN arr_time END LIMIT 1")
    Flight getFirstFlight(String airportIata);

    @ResultMap("flightResults")
    @Select("SELECT id, airline_iata, dep_iata, arr_iata, status, aircraft_icao, flight_iata, dep_time, arr_time, duration, gate_id FROM flights ORDER BY CASE WHEN dep_iata = #{airportIata} THEN dep_time WHEN arr_iata = #{airportIata} THEN arr_time END DESC LIMIT 1")
    Flight getLastFlight(String airportIata);

    @Update("UPDATE flights SET airline_iata = #{airlineIata}, dep_iata = #{depIata}, arr_iata = #{arrIata}, status = #{status}, aircraft_icao = #{aircraftIcao}, flight_iata = #{flightIata}, dep_time = #{depTime}, arr_time = #{arrTime}, duration = #{duration}, gate_id = #{gate.id} WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    void update(Flight flight);

    @Delete("DELETE FROM flights WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Flight remove(@Param("id") UUID id);

    @Delete("DELETE FROM flights")
    void clear();

    @Delete("DELETE FROM flights WHERE id IN ( SELECT id FROM ( SELECT *, ROW_NUMBER() OVER ( PARTITION BY dep_iata, dep_time, arr_time ) AS row_num FROM flights ) AS sub WHERE row_num > 1)")
    void removeDuplicates();
}

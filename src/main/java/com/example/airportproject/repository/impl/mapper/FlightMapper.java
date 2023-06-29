package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.Gate;
import com.example.airportproject.typehandler.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
public interface FlightMapper {

    @Insert(
            "INSERT INTO flights (airline_iata, dep_iata, arr_iata, status, aircraft_icao, flight_iata, dep_time, arr_time, duration, gate_id) VALUES (#{airlineIata}, #{depIata}, #{arrIata}, #{status}, #{aircraftIcao}, #{flightIata}, #{depTime}, #{arrTime}, #{duration}, #{gateId})")
    void create(Flight flight);

    @Results(id = "flightResults")
    @ConstructorArgs({
            @Arg(column = "id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class, id = true),
            @Arg(column = "airline_iata", javaType = String.class),
            @Arg(column = "dep_iata", javaType = String.class),
            @Arg(column = "arr_iata", javaType = String.class),
            @Arg(column = "status", javaType = String.class),
            @Arg(column = "aircraft_icao", javaType = String.class),
            @Arg(column = "flight_iata", javaType = String.class),
            @Arg(column = "dep_time", javaType = LocalDateTime.class),
            @Arg(column = "arr_time", javaType = LocalDateTime.class),
            @Arg(column = "duration", javaType = Integer.class),
            @Arg(column = "gate_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class)
    })
    @Select("SELECT f.id, f.airline_iata, f.dep_iata, f.arr_iata, f.status, f.aircraft_icao, f.flight_iata, f.dep_time, f.arr_time, f.duration, f.gate_id FROM flights f")
    List<Flight> getAll();

    @Results(id = "gateResults", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "number", column = "number"),
            @Result(property = "terminalId", column = "terminal_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class)
    })
    @Select("SELECT gates.id, gates.number, gates.terminal_id FROM gates LEFT JOIN flights ON gates.id = flights.gate_id WHERE flights.id = #{flightId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Gate getGateForFlight(UUID flightId);

    @ResultMap("flightResults")
    @Select("SELECT * FROM flights WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Flight get(@Param("id") UUID id);

    @ResultMap("flightResults")
    @Select("SELECT * FROM flights WHERE dep_iata = #{depIata} ORDER BY dep_time ASC")
    List<Flight> getDepartures(@Param("depIata") String depIata);

    @ResultMap("flightResults")
    @Select("SELECT * FROM flights WHERE arr_iata = #{arrIata} ORDER BY arr_time ASC")
    List<Flight> getArrivals(@Param("arrIata") String arrIata);

    @ResultMap("flightResults")
    @Select("SELECT * FROM flights ORDER BY CASE WHEN dep_iata = #{airportIata} THEN dep_time WHEN arr_iata = #{airportIata} THEN arr_time END")
    List<Flight> getOrderedFlights(@Param("airportIata") String airportIata);

    @Update("UPDATE flights SET airline_iata = #{airlineIata}, dep_iata = #{depIata}, arr_iata = #{arrIata}, status = #{status}, aircraft_icao = #{aircraftIcao}, flight_iata = #{flightIata}, dep_time = #{depTime}, arr_time = #{arrTime}, duration = #{duration}, gate_id = #{gateId} WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    void update(Flight flight);

    @Delete("DELETE FROM flights WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Flight remove(@Param("id") UUID id);

    @Delete("DELETE FROM flights")
    void clear();

    @Delete("DELETE FROM flights WHERE id IN ( SELECT id FROM ( SELECT *, ROW_NUMBER() OVER ( PARTITION BY dep_iata, dep_time, arr_time ) AS row_num FROM flights ) AS sub WHERE row_num > 1)")
    void removeDuplicates();
}

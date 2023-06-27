package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Flight;
import com.example.airportproject.typehandler.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
public interface FlightMapper {

    @Insert(
            "INSERT INTO flights (airline_iata, dep_iata, dep_terminal, dep_gate, arr_iata, arr_terminal, arr_gate, status, aircraft_icao, flight_number, flight_iata, dep_time, arr_time, duration) VALUES (#{airlineIata}, #{depIata}, #{depTerminal}, #{depGate}, #{arrIata}, #{arrTerminal}, #{arrGate}, #{status}, #{aircraftIcao}, #{flightNumber}, #{flightIata}, #{depTime}, #{arrTime}, #{duration})")
    void create(Flight flight);

    @Results(id = "flightResults")
    @ConstructorArgs({
            @Arg(column = "id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class, id = true),
            @Arg(column = "airline_iata", javaType = String.class),
            @Arg(column = "dep_iata", javaType = String.class),
            @Arg(column = "dep_terminal", javaType = String.class),
            @Arg(column = "dep_gate", javaType = String.class),
            @Arg(column = "arr_iata", javaType = String.class),
            @Arg(column = "arr_terminal", javaType = String.class),
            @Arg(column = "arr_gate", javaType = String.class),
            @Arg(column = "status", javaType = String.class),
            @Arg(column = "aircraft_icao", javaType = String.class),
            @Arg(column = "flight_number", javaType = String.class),
            @Arg(column = "flight_iata", javaType = String.class),
            @Arg(column = "dep_time", javaType = LocalDateTime.class),
            @Arg(column = "arr_time", javaType = LocalDateTime.class),
            @Arg(column = "duration", javaType = Integer.class)
    })
    @Select("SELECT * FROM flights")
    List<Flight> getAll();

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

    @Update("UPDATE flights SET airline_iata = #{airlineIata}, dep_iata = #{depIata}, dep_terminal = #{depTerminal}, dep_gate = #{depGate}, arr_iata = #{arrIata}, arr_terminal = #{arrTerminal}, arr_gate = #{arrGate}, status = #{status}, aircraft_icao = #{aircraftIcao}, flight_number = #{flightNumber}, flight_iata = #{flightIata}, dep_time = #{depTime}, arr_time = #{arrTime}, duration = #{duration} WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Flight update(@Param("id") UUID id, @Param("airlineIata") String airlineIata, @Param("depIata") String depIata, @Param("depTerminal") String depTerminal, @Param("depGate") String depGate, @Param("arrIata") String arrIata, @Param("arrTerminal") String arrTerminal, @Param("arrGate") String arrGate, @Param("status") String status, @Param("aircraftIcao") String aircraftIcao, @Param("flightNumber") String flightNumber, @Param("flightIata") String flightIata, @Param("depTime") LocalDateTime depTime, @Param("arrTime") LocalDateTime arrTime, @Param("duration") Integer duration);

    @Delete("DELETE FROM flights WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Flight remove(@Param("id") UUID id);

    @Delete("DELETE FROM flights")
    void clear();

    @Delete("DELETE FROM flights WHERE id IN ( SELECT id FROM ( SELECT *, ROW_NUMBER() OVER ( PARTITION BY dep_iata, dep_gate, dep_time, arr_time ) AS row_num FROM flights ) AS sub WHERE row_num > 1)")
    void removeDuplicates();
}

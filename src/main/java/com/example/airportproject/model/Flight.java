package com.example.airportproject.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

/**
* Represents a flight arriving or departing from an airport
*/
public class Flight {
    private UUID id;
    @NotNull
    @Length(max = 10)
    private String flightIata, depIata, arrIata, status;
    @Length(max = 10)
    private String airlineIata, aircraftIcao;
    @NotNull
    @Min(0)
    private Integer duration;
    @NotNull
    private LocalDateTime depTime, arrTime;

    private Gate gate;

    /**
    *
     * @param airlineIata Airline IATA code
     * @param depIata Departure airport IATA code
     * @param arrIata Arrival airport IATA code
     * @param status Status of the flight
     * @param aircraftIcao Aircraft ICAO code
     * @param flightIata Flight IATA code
     * @param depTime Departure time
     * @param arrTime Arrival time
     * @param duration Flight duration in minutes
    */
    public Flight(String airlineIata, @NotNull String depIata, @NotNull String arrIata, @NotNull String status, String aircraftIcao, @NotNull String flightIata, @NotNull LocalDateTime depTime, @NotNull LocalDateTime arrTime, @NotNull Integer duration) {

        this.airlineIata = airlineIata; // airline code
        this.depIata = depIata; // departure airport code
        this.arrIata = arrIata; // arrival airport code
        this.status = status; // flight status
        this.aircraftIcao = aircraftIcao; // aircraft code
        this.flightIata = flightIata; // flight code
        this.depTime = depTime; // actual departure date/time
        this.arrTime = arrTime; // actual arrival date/time
        this.duration = duration; // flight duration (minutes)
        // initialise gate as null until gate has been assigned
        this.gate = null;
    }


    /**
    * Constructor with ID
     * @param id Unique ID used in the database
     * @param airlineIata Airline IATA code
     * @param depIata Departure airport IATA code
     * @param arrIata Arrival airport IATA code
     * @param status Status of the flight
     * @param aircraftIcao Aircraft ICAO code
     * @param flightIata Flight IATA code
     * @param depTime Departure time
     * @param arrTime Arrival time
     * @param duration Flight duration in minutes
    */
    public Flight(UUID id, String airlineIata, @NotNull String depIata, @NotNull String arrIata, @NotNull String status, String aircraftIcao, @NotNull String flightIata, @NotNull LocalDateTime depTime, @NotNull LocalDateTime arrTime, @NotNull Integer duration) {
        this.id = id;
        this.airlineIata = airlineIata;
        this.depIata = depIata;
        this.arrIata = arrIata;
        this.status = status;
        this.aircraftIcao = aircraftIcao;
        this.flightIata = flightIata;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.duration = duration;
        // initialise gate as null until gate has been assigned
        this.gate = null;
    }

    public Flight(UUID id, String airlineIata, @NotNull String depIata, @NotNull String arrIata, @NotNull String status, String aircraftIcao, @NotNull String flightIata, @NotNull LocalDateTime depTime, @NotNull LocalDateTime arrTime, @NotNull Integer duration, Gate gate){
        this.id = id;
        this.airlineIata = airlineIata;
        this.depIata = depIata;
        this.arrIata = arrIata;
        this.status = status;
        this.aircraftIcao = aircraftIcao;
        this.flightIata = flightIata;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.duration = duration;
        this.gate = gate;
    }

    /**
    * Default constructor
    */
    // no arg constructor
    public Flight() {

    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAirlineIata() {
        return airlineIata;
    }

    public void setAirlineIata(String airlineIata) {
        this.airlineIata = airlineIata;
    }

    public @NotNull String getDepIata() {
        return depIata;
    }

    public void setDepIata(@NotNull String depIata) {
        this.depIata = depIata;
    }

    public @NotNull String getArrIata() {
        return arrIata;
    }

    public void setArrIata(@NotNull String arrIata) {
        this.arrIata = arrIata;
    }

    public @NotNull String getStatus() {
        return status;
    }

    public void setStatus(@NotNull String status) {
        this.status = status;
    }

    public String getAircraftIcao() {
        return aircraftIcao;
    }

    public void setAircraftIcao(String aircraftIcao) {
        this.aircraftIcao = aircraftIcao;
    }

    public @NotNull String getFlightIata() {
        return flightIata;
    }

    public void setFlightIata(@NotNull String flightIata) {
        this.flightIata = flightIata;
    }

    public @NotNull LocalDateTime getDepTime() {
        return depTime;
    }

    public void setDepTime(@NotNull LocalDateTime depTime) {
        this.depTime = depTime;
    }

    public @NotNull LocalDateTime getArrTime() {
        return arrTime;
    }

    public void setArrTime(@NotNull LocalDateTime arrTime) {
        this.arrTime = arrTime;
    }

    public @NotNull Integer getDuration() {
        return duration;
    }

    public void setDuration(@NotNull @Min(0) Integer duration) {
        this.duration = duration;
    }

//    public UUID getGateId() {
//        return gateId;
//    }
//
//    public void setGateId(UUID gateId) {
//        this.gateId = gateId;
//    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }
}

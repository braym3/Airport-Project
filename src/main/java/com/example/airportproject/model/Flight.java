package com.example.airportproject.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Objects;
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
    @Min(0)
    private int duration;
    @NotNull
    private LocalDateTime depTime, arrTime;
    private Gate gate;
    private Runway runway;

    /**
    * Constructor with no ID
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
    public Flight(String airlineIata, @NotNull String depIata, @NotNull String arrIata, @NotNull String status, String aircraftIcao, @NotNull String flightIata, @NotNull LocalDateTime depTime, @NotNull LocalDateTime arrTime, int duration) {

        this.airlineIata = airlineIata; // airline code
        this.depIata = depIata; // departure airport code
        this.arrIata = arrIata; // arrival airport code
        this.status = status; // flight status
        this.aircraftIcao = aircraftIcao; // aircraft code
        this.flightIata = flightIata; // flight code
        this.depTime = depTime; // actual departure date/time
        this.arrTime = arrTime; // actual arrival date/time
        this.duration = duration; // flight duration (minutes)
        // initialise gate and runway as null until they have been assigned
        this.gate = null;
        this.runway = null;
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
    public Flight(UUID id, String airlineIata, @NotNull String depIata, @NotNull String arrIata, @NotNull String status, String aircraftIcao, @NotNull String flightIata, @NotNull LocalDateTime depTime, @NotNull LocalDateTime arrTime, int duration) {
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
        // initialise gate and runway as null until they have been assigned
        this.gate = null;
        this.runway = null;
    }

    /**
     * Constructor with ID and assigned Gate and Runway
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
     * @param gate The assigned Gate object
     */
    public Flight(UUID id, String airlineIata, @NotNull String depIata, @NotNull String arrIata, @NotNull String status, String aircraftIcao, @NotNull String flightIata, @NotNull LocalDateTime depTime, @NotNull LocalDateTime arrTime, int duration, Gate gate, Runway runway){
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
        this.runway = runway;
    }

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(@Min(0) int duration) {
        this.duration = duration;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public Runway getRunway() {
        return runway;
    }

    public void setRunway(Runway runway) {
        this.runway = runway;
    }

    // returns true if the flight is departing from the specified airport
    public boolean isDeparture(String airportCode){
        return this.getDepIata().equals(airportCode);
    }

    // returns true if the flight is arriving to the specified airport
    public boolean isArrival(String airportCode){
        return this.getArrIata().equals(airportCode);
    }

    // returns the flight time for the specified airport (departure time for departures or arrival time for arrivals)
    public LocalDateTime getFlightTimeForAirport(String airportCode){
        if(isDeparture(airportCode)){
            return this.getDepTime();
        }else if(isArrival(airportCode)){
            return this.getArrTime();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return getDuration() == flight.getDuration() && Objects.equals(getId(), flight.getId()) && Objects.equals(getFlightIata(), flight.getFlightIata()) && Objects.equals(getDepIata(), flight.getDepIata()) && Objects.equals(getArrIata(), flight.getArrIata()) && Objects.equals(getStatus(), flight.getStatus()) && Objects.equals(getAirlineIata(), flight.getAirlineIata()) && Objects.equals(getAircraftIcao(), flight.getAircraftIcao()) && Objects.equals(getDepTime(), flight.getDepTime()) && Objects.equals(getArrTime(), flight.getArrTime()) && Objects.equals(getGate(), flight.getGate()) && Objects.equals(getRunway(), flight.getRunway());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFlightIata(), getDepIata(), getArrIata(), getStatus(), getAirlineIata(), getAircraftIcao(), getDuration(), getDepTime(), getArrTime(), getGate(), getRunway());
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightIata='" + flightIata + '\'' +
                ", depIata='" + depIata + '\'' +
                ", arrIata='" + arrIata + '\'' +
                ", status='" + status + '\'' +
                ", airlineIata='" + airlineIata + '\'' +
                ", aircraftIcao='" + aircraftIcao + '\'' +
                ", duration=" + duration +
                ", depTime=" + depTime +
                ", arrTime=" + arrTime +
                ", gate=" + gate +
                ", runway=" + runway +
                '}';
    }
}

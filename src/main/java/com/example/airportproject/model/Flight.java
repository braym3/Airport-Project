package com.example.airportproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity // tells spring it's a table
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)    // makes it auto-increment
    private UUID id;
    @NotNull
    @Length(max = 10)
    private String flightIata, depIata, arrIata, status, flightNumber;
    private String airlineIata, depTerminal, depGate, arrTerminal, arrGate, aircraftIcao;
    @NotNull
    private int duration;
    @NotNull
    private LocalDateTime depTime, arrTime;

    /**
    *
     * @param airlineIata Airline IATA code
     * @param depIata Departure airport IATA code
     * @param depTerminal Departure terminal
     * @param depGate Departure gate
     * @param arrIata Arrival airport IATA code
     * @param arrTerminal Arrival terminal
     * @param arrGate Arrival gate
     * @param status Status of the flight
     * @param aircraftIcao Aircraft ICAO code
     * @param flightNumber Flight number
     * @param flightIata Flight IATA code
     * @param depTime Departure time
     * @param arrTime Arrival time
     * @param duration Flight duration in minutes
    */
    public Flight(String airlineIata, String depIata, String depTerminal, String depGate, String arrIata, String arrTerminal, String arrGate, String status, String aircraftIcao, String flightNumber, String flightIata, LocalDateTime depTime, LocalDateTime arrTime, int duration) {

        this.airlineIata = airlineIata; // airline code
        this.depIata = depIata; // departure airport code
        this.depTerminal = depTerminal; // departure terminal
        this.depGate = depGate; // departure gate
        this.arrIata = arrIata; // arrival airport code
        this.arrTerminal = arrTerminal; // arrival terminal
        this.arrGate = arrGate; // arrival gate
        this.status = status; // flight status
        this.aircraftIcao = aircraftIcao; // aircraft code
        this.flightNumber = flightNumber; // base flight number
        this.flightIata = flightIata; // flight code
        this.depTime = depTime; // actual departure date/time
        this.arrTime = arrTime; // actual arrival date/time
        this.duration = duration; // flight duration (minutes)
    }


    /**
    * Constructor with ID
     * @param id Unique ID used in the database
     * @param airlineIata Airline IATA code
     * @param depIata Departure airport IATA code
     * @param depTerminal Departure terminal
     * @param depGate Departure gate
     * @param arrIata Arrival airport IATA code
     * @param arrTerminal Arrival terminal
     * @param arrGate Arrival gate
     * @param status Status of the flight
     * @param aircraftIcao Aircraft ICAO code
     * @param flightNumber Flight number
     * @param flightIata Flight IATA code
     * @param depTime Departure time
     * @param arrTime Arrival time
     * @param duration Flight duration in minutes
    */
    public Flight(UUID id, String airlineIata, String depIata, String depTerminal, String depGate, String arrIata, String arrTerminal, String arrGate, String status, String aircraftIcao, String flightNumber, String flightIata, LocalDateTime depTime, LocalDateTime arrTime, int duration) {
        this.id = id;
        this.airlineIata = airlineIata;
        this.depIata = depIata;
        this.depTerminal = depTerminal;
        this.depGate = depGate;
        this.arrIata = arrIata;
        this.arrTerminal = arrTerminal;
        this.arrGate = arrGate;
        this.status = status;
        this.aircraftIcao = aircraftIcao;
        this.flightNumber = flightNumber;
        this.flightIata = flightIata;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.duration = duration;
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

    public String getDepIata() {
        return depIata;
    }

    public void setDepIata(String depIata) {
        this.depIata = depIata;
    }

    public String getDepTerminal() {
        return depTerminal;
    }

    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal;
    }

    public String getDepGate() {
        return depGate;
    }

    public void setDepGate(String depGate) {
        this.depGate = depGate;
    }

    public String getArrIata() {
        return arrIata;
    }

    public void setArrIata(String arrIata) {
        this.arrIata = arrIata;
    }

    public String getArrTerminal() {
        return arrTerminal;
    }

    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal;
    }

    public String getArrGate() {
        return arrGate;
    }

    public void setArrGate(String arrGate) {
        this.arrGate = arrGate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAircraftIcao() {
        return aircraftIcao;
    }

    public void setAircraftIcao(String aircraftIcao) {
        this.aircraftIcao = aircraftIcao;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightIata() {
        return flightIata;
    }

    public void setFlightIata(String flightIata) {
        this.flightIata = flightIata;
    }

    public LocalDateTime getDepTime() {
        return depTime;
    }

    public void setDepTime(LocalDateTime depTime) {
        this.depTime = depTime;
    }

    public LocalDateTime getArrTime() {
        return arrTime;
    }

    public void setArrTime(LocalDateTime arrTime) {
        this.arrTime = arrTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", airlineIata='" + airlineIata + '\'' +
                ", depIata='" + depIata + '\'' +
                ", depTerminal='" + depTerminal + '\'' +
                ", depGate='" + depGate + '\'' +
                ", arrIata='" + arrIata + '\'' +
                ", arrTerminal='" + arrTerminal + '\'' +
                ", arrGate='" + arrGate + '\'' +
                ", status='" + status + '\'' +
                ", aircraftIcao='" + aircraftIcao + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", flightIata='" + flightIata + '\'' +
                ", depTime='" + depTime + '\'' +
                ", arrTime='" + arrTime + '\'' +
                ", duration=" + duration +
                '}';
    }
}

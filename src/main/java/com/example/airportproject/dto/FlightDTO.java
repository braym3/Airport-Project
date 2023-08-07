package com.example.airportproject.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class FlightDTO {
    private UUID id;
    private String flightIata, depIata, arrIata, status, airlineIata, aircraftIcao;
    private Integer duration;
    private LocalDateTime depTime, arrTime;
    private Integer gateNumber;
    private Integer terminalNumber;
    private Integer runwayNumber;

    public FlightDTO(){

    }

    public FlightDTO(UUID id, String flightIata, String depIata, String arrIata, String status, String airlineIata, String aircraftIcao, Integer duration, LocalDateTime depTime, LocalDateTime arrTime, Integer gateNumber, Integer terminalNumber, Integer runwayNumber) {
        this.id = id;
        this.flightIata = flightIata;
        this.depIata = depIata;
        this.arrIata = arrIata;
        this.status = status;
        this.airlineIata = airlineIata;
        this.aircraftIcao = aircraftIcao;
        this.duration = duration;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.gateNumber = gateNumber;
        this.terminalNumber = terminalNumber;
        this.runwayNumber = runwayNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFlightIata() {
        return flightIata;
    }

    public void setFlightIata(String flightIata) {
        this.flightIata = flightIata;
    }

    public String getDepIata() {
        return depIata;
    }

    public void setDepIata(String depIata) {
        this.depIata = depIata;
    }

    public String getArrIata() {
        return arrIata;
    }

    public void setArrIata(String arrIata) {
        this.arrIata = arrIata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAirlineIata() {
        return airlineIata;
    }

    public void setAirlineIata(String airlineIata) {
        this.airlineIata = airlineIata;
    }

    public String getAircraftIcao() {
        return aircraftIcao;
    }

    public void setAircraftIcao(String aircraftIcao) {
        this.aircraftIcao = aircraftIcao;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public Integer getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(Integer gateNumber) {
        this.gateNumber = gateNumber;
    }

    public Integer getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(Integer terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public Integer getRunwayNumber() {
        return runwayNumber;
    }

    public void setRunwayNumber(Integer runwayNumber) {
        this.runwayNumber = runwayNumber;
    }
}
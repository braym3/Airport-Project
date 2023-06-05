package com.example.airportproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity // tells spring it's a table
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // makes it auto-increment
    private Long id;
    private String airline_iata, dep_iata, dep_terminal, dep_gate, arr_iata, arr_terminal, arr_gate, status, aircraft_icao, flight_number, flight_iata;
    private double duration;

    private LocalDateTime dep_time, arr_time;

    // constructor without id
    public Flight(String airline_iata, String dep_iata, String dep_terminal, String dep_gate, String arr_iata, String arr_terminal, String arr_gate, String status, String aircraft_icao, String flight_number, String flight_iata, LocalDateTime dep_time, LocalDateTime arr_time, double duration) {
        this.airline_iata = airline_iata;
        this.dep_iata = dep_iata;
        this.dep_terminal = dep_terminal;
        this.dep_gate = dep_gate;
        this.arr_iata = arr_iata;
        this.arr_terminal = arr_terminal;
        this.arr_gate = arr_gate;
        this.status = status;
        this.aircraft_icao = aircraft_icao;
        this.flight_number = flight_number;
        this.flight_iata = flight_iata;
        this.dep_time = dep_time;
        this.arr_time = arr_time;
        this.duration = duration;
    }

    // constructor with id
    public Flight(Long id, String airline_iata, String dep_iata, String dep_terminal, String dep_gate, String arr_iata, String arr_terminal, String arr_gate, String status, String aircraft_icao, String flight_number, String flight_iata, LocalDateTime dep_time, LocalDateTime arr_time, double duration) {
        this.id = id;
        this.airline_iata = airline_iata;
        this.dep_iata = dep_iata;
        this.dep_terminal = dep_terminal;
        this.dep_gate = dep_gate;
        this.arr_iata = arr_iata;
        this.arr_terminal = arr_terminal;
        this.arr_gate = arr_gate;
        this.status = status;
        this.aircraft_icao = aircraft_icao;
        this.flight_number = flight_number;
        this.flight_iata = flight_iata;
        this.dep_time = dep_time;
        this.arr_time = arr_time;
        this.duration = duration;
    }

    // no arg constructor
    public Flight() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirline_iata() {
        return airline_iata;
    }

    public void setAirline_iata(String airline_iata) {
        this.airline_iata = airline_iata;
    }

    public String getDep_iata() {
        return dep_iata;
    }

    public void setDep_iata(String dep_iata) {
        this.dep_iata = dep_iata;
    }

    public String getDep_terminal() {
        return dep_terminal;
    }

    public void setDep_terminal(String dep_terminal) {
        this.dep_terminal = dep_terminal;
    }

    public String getDep_gate() {
        return dep_gate;
    }

    public void setDep_gate(String dep_gate) {
        this.dep_gate = dep_gate;
    }

    public String getArr_iata() {
        return arr_iata;
    }

    public void setArr_iata(String arr_iata) {
        this.arr_iata = arr_iata;
    }

    public String getArr_terminal() {
        return arr_terminal;
    }

    public void setArr_terminal(String arr_terminal) {
        this.arr_terminal = arr_terminal;
    }

    public String getArr_gate() {
        return arr_gate;
    }

    public void setArr_gate(String arr_gate) {
        this.arr_gate = arr_gate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAircraft_icao() {
        return aircraft_icao;
    }

    public void setAircraft_icao(String aircraft_icao) {
        this.aircraft_icao = aircraft_icao;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getFlight_iata() {
        return flight_iata;
    }

    public void setFlight_iata(String flight_iata) {
        this.flight_iata = flight_iata;
    }

    public LocalDateTime getDep_time() {
        return dep_time;
    }

    public void setDep_time(LocalDateTime dep_time) {
        this.dep_time = dep_time;
    }

    public LocalDateTime getArr_time() {
        return arr_time;
    }

    public void setArr_time(LocalDateTime arr_time) {
        this.arr_time = arr_time;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", airline_iata='" + airline_iata + '\'' +
                ", dep_iata='" + dep_iata + '\'' +
                ", dep_terminal='" + dep_terminal + '\'' +
                ", dep_gate='" + dep_gate + '\'' +
                ", arr_iata='" + arr_iata + '\'' +
                ", arr_terminal='" + arr_terminal + '\'' +
                ", arr_gate='" + arr_gate + '\'' +
                ", status='" + status + '\'' +
                ", aircraft_icao='" + aircraft_icao + '\'' +
                ", flight_number='" + flight_number + '\'' +
                ", flight_iata='" + flight_iata + '\'' +
                ", dep_time='" + dep_time + '\'' +
                ", arr_time='" + arr_time + '\'' +
                ", duration=" + duration +
                '}';
    }
}

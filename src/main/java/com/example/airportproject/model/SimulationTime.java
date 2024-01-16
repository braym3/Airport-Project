package com.example.airportproject.model;

import com.example.airportproject.dao.FlightDao;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public class SimulationTime {
    private LocalDateTime currentTime;

    @Value("#{${airportproject.airportcode}}")
    String airportCode;

    public SimulationTime(FlightDao flightDao) {
        // get date of simulation by looking up the first flight arrival time
        LocalDateTime currentDate = flightDao.getFirstFlight(airportCode).getArrTime();
        // set the time to 12am of that date
        this.currentTime = currentDate.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public void fastForwardOneHour(){
        this.currentTime = this.currentTime.plusHours(1);
    }
}

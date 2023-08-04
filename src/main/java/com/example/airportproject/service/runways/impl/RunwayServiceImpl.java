package com.example.airportproject.service.runways.impl;

import com.example.airportproject.dao.RunwayDao;
import com.example.airportproject.model.Runway;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.service.runways.RunwayService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Primary
@Service
public class RunwayServiceImpl implements RunwayService {
    private final RunwayDao runwayDao;

    public RunwayServiceImpl(RunwayDao runwayDao) {
        this.runwayDao = runwayDao;
    }

    @Override
    @Transactional
    public Runway createRunway(Runway runway) {
        return runwayDao.create(runway);
    }



    @Override
    @Transactional
    public Runway get(UUID id) {
        return runwayDao.get(id);
    }

    @Override
    @Transactional
    public List<Runway> getAll() {
        return runwayDao.getAll();
    }

    @Override
    @Transactional
    public Runway remove(UUID id) {
        Runway removed = this.get(id);
        runwayDao.remove(id);
        return removed;
    }

    @Override
    @Transactional
    public Runway update(UUID id, int number, List<TimeSlot> schedule) {
        Runway runway = this.get(id);

        // update the runway attributes if the corresponding parameters are provided
        if(number != runway.getNumber()){
            runway.setNumber(number);
        }
        if(schedule != null){
            runway.setSchedule(schedule);
        }
        // save and return the updated runway record
        return runwayDao.update(runway);
    }

    @Override
    @Transactional
    public void addRunwaySlot(TimeSlot timeSlot){
        runwayDao.addRunwaySlot(timeSlot);
    }

    @Override
    public TimeSlot getRunwayTimeSlotByFlightId(UUID flightId) {
        return runwayDao.getRunwayTimeSlotByFlightId(flightId);
    }

    @Override
    public List<TimeSlot> getScheduleForRunway(UUID runwayId) {
        return runwayDao.getScheduleForRunway(runwayId);
    }

    @Override
    public List<TimeSlot> getAllRunwayTimeSlots() {
        return runwayDao.getAllRunwayTimeSlots();
    }

    @Override
    public void removeTimeSlotForRunway(UUID timeSlotId){
        runwayDao.removeTimeSlotForRunway(timeSlotId);
    }

    @Override
    public TimeSlot removeRunwayTimeSlotByFlightId(UUID flightId) {
        TimeSlot removed = getRunwayTimeSlotByFlightId(flightId);
        runwayDao.removeRunwayTimeSlotByFlightId(flightId);
        return removed;
    }
}

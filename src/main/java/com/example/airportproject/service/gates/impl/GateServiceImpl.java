package com.example.airportproject.service.gates.impl;

import com.example.airportproject.dao.GateDao;
import com.example.airportproject.dto.GateDTO;
import com.example.airportproject.dto.GateSlotDTO;
import com.example.airportproject.dto.TimeSlotDTO;
import com.example.airportproject.model.Gate;
import com.example.airportproject.model.Terminal;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.service.flights.FlightService;
import com.example.airportproject.service.gates.GateService;
import com.example.airportproject.service.impactEvents.ImpactEventService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Primary
@Service
public class GateServiceImpl implements GateService {
    private final GateDao gateDao;
    private final ImpactEventService impactEventService;
    private final FlightService flightService;

    public GateServiceImpl(GateDao gateDao, ImpactEventService impactEventService, FlightService flightService) {
        this.gateDao = gateDao;
        this.impactEventService = impactEventService;
        this.flightService = flightService;
    }

    @Override
    @Transactional
    public Gate createGate(Gate gate) {
        return gateDao.create(gate);
    }

    @Override
    @Transactional
    public Gate get(UUID id) {
        return gateDao.get(id); //.orElseThrow(GateNotFoundException::new);  // orElseThrows takes a supplier (functional interface) - so use lambda
    }

    @Override
    @Transactional
    public List<Gate> getAll() {
        return gateDao.getAll();
    }

    @Override
    @Transactional
    public Gate remove(UUID id) {
        Gate removed = this.get(id);
        gateDao.remove(id);
        return removed;
    }

    @Override
    @Transactional
    public Gate update(UUID id, int number, Terminal terminal, List<TimeSlot> schedule) {
        Gate gate = this.get(id);

        // update the gate attributes if the corresponding parameters are provided
        if(number != gate.getNumber()){
            gate.setNumber(number);
        }
        if(terminal != null){
            gate.setTerminal(terminal);
        }
        if(schedule != null){
            gate.setSchedule(schedule);
        }
        // save and return the updated gate record
        return gateDao.update(gate);               // save the updated Gate and return it
    }

    @Override
    @Transactional
    public void addGateSlot(TimeSlot timeSlot){
        gateDao.addGateSlot(timeSlot);
    }

    @Override
    public TimeSlot getGateTimeSlotByFlightId(UUID flightId) {
        return gateDao.getGateTimeSlotByFlightId(flightId);
    }

    @Override
    public List<TimeSlot> getScheduleForGate(UUID gateId) {
        return gateDao.getScheduleForGate(gateId);
    }

    @Override
    public List<TimeSlot> getAllGatesTimeSlots() {
        return gateDao.getAllGatesTimeSlots();
    }

    @Override
    public void removeTimeSlotForGate(UUID timeSlotId){
        gateDao.removeTimeSlotForGate(timeSlotId);
    }

    @Override
    public TimeSlot removeGateTimeSlotByFlightId(UUID flightId){
        TimeSlot removed = getGateTimeSlotByFlightId(flightId);
        gateDao.removeGateTimeSlotByFlightId(flightId);
        return removed;
    }

    // convert a Gate object to a GateDTO
    @Override
    public GateDTO convertToDTO(Gate gate){
        GateDTO gateDTO = new GateDTO();
        gateDTO.setId(gate.getId());
        gateDTO.setNumber(gate.getNumber());
        gateDTO.setTerminalNumber(gate.getTerminal().getNumber());
        gateDTO.setSchedule(gate.getSchedule().stream().map(this::convertTimeSlotToGateSlotDTO).collect(Collectors.toList()));
        return gateDTO;
    }

    @Override
    public List<GateDTO> convertToDTOList(List<Gate> gates){
        return gates.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public GateSlotDTO convertTimeSlotToGateSlotDTO(TimeSlot timeSlot){
        GateSlotDTO gateSlotDTO = new GateSlotDTO();
        gateSlotDTO.setId(timeSlot.getId());
        gateSlotDTO.setStartTime(timeSlot.getStartTime());
        gateSlotDTO.setEndTime(timeSlot.getEndTime());
        if(timeSlot.getRunway() != null){
            gateSlotDTO.setRunwayNumber(timeSlot.getRunway().getNumber());
        }
        if(timeSlot.getImpactEvent() != null){
            gateSlotDTO.setImpactEvent(impactEventService.convertToDTO(timeSlot.getImpactEvent()));
        }
        if(timeSlot.getFlight() != null){
            gateSlotDTO.setFlight(flightService.convertToDTO(timeSlot.getFlight()));

        }
        return gateSlotDTO;
    }

    @Override
    public TimeSlotDTO convertTimeSlotToTimeSlotDTO(TimeSlot timeSlot){
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setId(timeSlot.getId());
        timeSlotDTO.setStartTime(timeSlot.getStartTime());
        timeSlotDTO.setEndTime(timeSlot.getEndTime());
        if(timeSlot.getGate() != null){
            timeSlotDTO.setGateId(timeSlot.getGate().getId());
            timeSlotDTO.setGateNumber(timeSlot.getGate().getNumber());
        }
        if(timeSlot.getRunway() != null){
            timeSlotDTO.setRunwayId(timeSlot.getRunway().getId());
            timeSlotDTO.setRunwayNumber(timeSlot.getRunway().getNumber());
        }
        if(timeSlot.getImpactEvent() != null){
            timeSlotDTO.setImpactEvent(impactEventService.convertToDTO(timeSlot.getImpactEvent()));
        }
        if(timeSlot.getFlight() != null){
            timeSlotDTO.setFlight(flightService.convertToDTO(timeSlot.getFlight()));

        }
        return timeSlotDTO;
    }

    @Override
    public List<GateSlotDTO> convertTimeSlotsToGateSlotDTOList(List<TimeSlot> timeSlots){
        return timeSlots.stream().map(this::convertTimeSlotToGateSlotDTO).collect(Collectors.toList());
    }

    @Override
    public List<TimeSlotDTO> convertTimeSlotsToTimeSlotDTOList(List<TimeSlot> timeSlots){
        return timeSlots.stream().map(this::convertTimeSlotToTimeSlotDTO).collect(Collectors.toList());
    }
}

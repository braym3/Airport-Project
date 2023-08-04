package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.*;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
public interface TimeSlotMapper {
    @Insert(
            "INSERT INTO timeslots (gate_id, flight_id, start_time, end_time, impact_event_id, runway_id) VALUES (#{gate.id}, #{flight.id}, #{startTime}, #{endTime}, #{impactEvent.id}, #{runway.id})")
    void createTimeSlot(TimeSlot timeSlot);

    @Select("SELECT id, gate_id, flight_id, start_time, end_time, impact_event_id, runway_id FROM timeslots ORDER BY start_time ASC")
    @Results(id = "timeSlotResults", value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "gate", column = "gate_id", javaType = Gate.class, one = @One(select = "com.example.airportproject.repository.impl.mapper.GateMapper.getGateWithoutSchedule")),
            @Result(property = "flight", column = "flight_id", javaType = Flight.class, one = @One(select = "com.example.airportproject.repository.impl.mapper.FlightMapper.get")),
            @Result(property = "startTime", column = "start_time", javaType = LocalDateTime.class),
            @Result(property = "endTime", column = "end_time", javaType = LocalDateTime.class),
            @Result(property = "impactEvent", column = "impact_event_id", javaType = ImpactEvent.class, one = @One(select = "com.example.airportproject.repository.impl.mapper.ImpactEventMapper.get")),
            @Result(property = "runway", column = "runway_id", javaType = Runway.class, one = @One(select = "com.example.airportproject.repository.impl.mapper.RunwayMapper.getRunwayWithoutSchedule")),
    })
    List<TimeSlot> getAllTimeSlots();

    @ResultMap("timeSlotResults")
    @Select("SELECT id, gate_id, flight_id, start_time, end_time, impact_event_id, runway_id FROM timeslots WHERE gate_id = #{gateId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler} ORDER BY start_time ASC")
    List<TimeSlot> selectTimeSlotsForGate(@Param("gateId") UUID gateId);

    @ResultMap("timeSlotResults")
    @Select("SELECT id, gate_id, flight_id, start_time, end_time, impact_event_id, runway_id FROM timeslots WHERE runway_id = #{runwayId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler} ORDER BY start_time ASC")
    List<TimeSlot> selectTimeSlotsForRunway(@Param("runwayId") UUID runwayId);

    @ResultMap("timeSlotResults")
    @Select("SELECT id, gate_id, flight_id, start_time, end_time, impact_event_id, runway_id FROM timeslots WHERE gate_id IS NOT NULL ORDER BY gate_id")
    List<TimeSlot> selectAllGateTimeSlots();

    @ResultMap("timeSlotResults")
    @Select("SELECT id, gate_id, flight_id, start_time, end_time, impact_event_id, runway_id FROM timeslots WHERE runway_id IS NOT NULL ORDER BY runway_id")
    List<TimeSlot> selectAllRunwayTimeSlots();

    @ResultMap("timeSlotResults")
    @Select("SELECT id, gate_id, flight_id, start_time, end_time, impact_event_id, runway_id FROM timeslots WHERE impact_event_id IS NOT NULL ORDER BY impact_event_id")
    List<TimeSlot> selectAllImpactEventTimeSlots();

    @ResultMap("timeSlotResults")
    @Select("SELECT id, gate_id, flight_id, start_time, end_time, impact_event_id, runway_id FROM timeslots WHERE flight_id = #{flightId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler} AND gate_id IS NOT NULL")
    TimeSlot getGateTimeSlotByFlightId(UUID flightId);

    @ResultMap("timeSlotResults")
    @Select("SELECT id, gate_id, flight_id, start_time, end_time, impact_event_id, runway_id FROM timeslots WHERE flight_id = #{flightId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler} AND runway_id IS NOT NULL")
    TimeSlot getRunwayTimeSlotByFlightId(UUID flightId);

    @Delete("DELETE FROM timeslots WHERE id = #{timeSlotId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    void removeTimeSlot(@Param("timeSlotId") UUID timeSlotId);

    @Delete("DELETE FROM timeslots WHERE gate_id = #{gateId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    void removeAllTimeSlotsForGateId(UUID gateId);

    @Delete("DELETE FROM timeslots WHERE runway_id = #{runwayId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    void removeAllTimeSlotsForRunwayId(UUID runwayId);

    @Delete("DELETE FROM timeslots WHERE flight_id = #{flightId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler} AND gate_id IS NOT NULL")
    void removeGateTimeSlotByFlightId(UUID flightId);

    @Delete("DELETE FROM timeslots WHERE flight_id = #{flightId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler} AND runway_id IS NOT NULL")
    void removeRunwayTimeSlotByFlightId(UUID flightId);
}

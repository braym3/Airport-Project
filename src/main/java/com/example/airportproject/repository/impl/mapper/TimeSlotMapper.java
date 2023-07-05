package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.typehandler.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
public interface TimeSlotMapper {
    @Insert(
            "INSERT INTO gate_slots (gate_id, flight_id, start_time, end_time, impact_event_id) VALUES (#{gateId}, #{timeSlot.flightId}, #{timeSlot.startTime}, #{timeSlot.endTime}, #{timeSlot.impactEventId})")
    void createTimeSlotForGate(TimeSlot timeSlot, UUID gateId);

    @Select("SELECT gate_slots.id, gate_slots.flight_id, gate_slots.start_time, gate_slots.end_time, gate_slots.impact_event_id FROM gate_slots LEFT JOIN gates ON gate_slots.gate_id = gates.id WHERE gates.id = #{gateId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    @Results(id = "gateSlotResults", value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "flightId", column = "flight_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "startTime", column = "start_time", javaType = LocalDateTime.class),
            @Result(property = "endTime", column = "end_time", javaType = LocalDateTime.class),
            @Result(property = "impactEventId", column = "impact_event_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class)
    })
    List<TimeSlot> selectTimeSlotsForGate(@Param("gateId") UUID gateId);

    @ResultMap("gateSlotResults")
    @Select("SELECT id, flight_id, start_time, end_time, impact_event_id FROM gate_slots WHERE flight_id = #{flightId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    TimeSlot getGateTimeSlotByFlightId(UUID flightId);

    @Delete("DELETE FROM gate_slots WHERE id = #{timeSlotId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    void removeTimeSlotForGate(@Param("timeSlotId") UUID timeSlotId);

    @Delete("DELETE FROM gate_slots WHERE gate_id = #{gateId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    void removeAllTimeSlotsForGate(UUID gateId);
}

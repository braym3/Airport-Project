package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.model.enums.Entity;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mapper
public interface ImpactEventMapper {
    @Insert(
            "INSERT INTO impact_events (type, description, probability, min_duration, max_duration, impacts) VALUES (#{type}, #{description}, #{probability}, #{minDuration}, #{maxDuration}, #{impacts})")
    void create(ImpactEvent impactEvent);

    @Select("SELECT id, type, description, probability, min_duration, max_duration, impacts FROM impact_events")
    @Results(id = "impactEventResults", value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "type", column = "type", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "probability", column = "probability", javaType = BigDecimal.class),
            @Result(property = "minDuration", column = "min_duration", javaType = int.class),
            @Result(property = "maxDuration", column = "max_duration", javaType = int.class),
            @Result(property = "impacts", column = "impacts", javaType = Entity.class)
    })
    List<ImpactEvent> getAll();

    @ResultMap("impactEventResults")
    @Select("SELECT id, type, description, probability, min_duration, max_duration, impacts FROM impact_events WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    ImpactEvent get(@Param("id") UUID id);

    @Update("UPDATE impact_events SET type = #{type}, description = #{description}, probability = #{probability}, min_duration = #{minDuration}, max_duration = #{maxDuration}, impacts = #{impacts} WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    ImpactEvent update(ImpactEvent impactEvent);

    @Delete("DELETE FROM impact_events WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    ImpactEvent remove(@Param("id") UUID id);

    @Delete("DELETE FROM impact_events")
    void clear();

    // insert new record into flight slots history table
    @Insert(
            "INSERT INTO flight_slots_history (flight_id, old_gate_id, new_gate_id, old_dep_time, old_arr_time, new_dep_time, new_arr_time, impact_time_slot_id, old_runway_id, new_runway_id) VALUES (#{flight.id}, #{oldGateId}, #{newGateId}, #{oldDepTime}, #{oldArrTime}, #{newDepTime}, #{newArrTime}, #{impactTimeSlotId}, #{oldRunwayId}, #{newRunwayId})")
    void createHistorySlot(Flight flight, UUID oldGateId, UUID newGateId, LocalDateTime oldDepTime, LocalDateTime newDepTime, LocalDateTime oldArrTime, LocalDateTime newArrTime, UUID impactTimeSlotId, UUID oldRunwayId, UUID newRunwayId);

    @Select("SELECT id, timestamp, flight_id, old_gate_id, new_gate_id, old_dep_time, old_arr_time, new_dep_time, new_arr_time, impact_time_slot_id, old_runway_id, new_runway_id FROM flight_slots_history")
    @Results(id = "historySlotResults", value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "timestamp", column = "timestamp", javaType = LocalDateTime.class),
            @Result(property = "flight", column = "flight_id", javaType = UUID.class),
            @Result(property = "oldGate", column = "old_gate_id", javaType = UUID.class),
            @Result(property = "newGate", column = "new_gate_id", javaType = UUID.class),
            @Result(property = "oldDepTime", column = "old_dep_time", javaType = LocalDateTime.class),
            @Result(property = "oldArrTime", column = "old_arr_time", javaType = LocalDateTime.class),
            @Result(property = "newDepTime", column = "new_dep_time", javaType = LocalDateTime.class),
            @Result(property = "newArrTime", column = "new_arr_time", javaType = LocalDateTime.class),
            @Result(property = "impactEventTimeSlot", column = "impact_time_slot_id", javaType = UUID.class),
            @Result(property = "oldRunway", column = "old_runway_id", javaType = UUID.class),
            @Result(property = "newRunway", column = "new_runway_id", javaType = UUID.class)
    })
    List<Objects> getAllHistory();

    @Select("SELECT id, timestamp, flight_id, old_gate_id, new_gate_id, old_dep_time, old_arr_time, new_dep_time, new_arr_time, impact_time_slot_id, old_runway_id, new_runway_id FROM flight_slots_history WHERE impact_time_slot_id = #{impactTimeSlotId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    @ResultMap("historySlotResults")
    List<Objects> getHistoryForImpactEventTimeSlotId(UUID impactTimeSlotId);
}

package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.Terminal;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.typehandler.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Mapper
public interface GateMapper {
    @Insert(
            "INSERT INTO gates (number, terminal_id) VALUES (#{number}, #{terminal.id})")
    void create(Gate gate);

    @Select("SELECT id, number FROM gates")
    @Results(id="gateResults", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "number", column = "number"),
            @Result(property = "terminal", column = "id", many = @Many(select = "selectTerminalForGate")),
            @Result(property = "schedule", column = "id", many = @Many(select = "selectTimeSlotsForGate"))
    })
    List<Gate> getAll();

    @Select("SELECT terminals.id, terminals.number FROM terminals LEFT JOIN gates ON terminals.id = gates.terminal_id WHERE gates.id = #{gateId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    @Results(value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "number", column = "number")
    })
    Terminal selectTerminalForGate(@Param("gateId") UUID gateId);

    @Select("SELECT gate_slots.id, gate_slots.gate_id, gate_slots.flight_id, gate_slots.start_time, gate_slots.end_time FROM gate_slots LEFT JOIN gates ON gate_slots.gate_id = gates.id WHERE gates.id = #{gateId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    @Results(value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "gateId", column = "gate_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "flightId", column = "flight_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "startTime", column = "start_time", javaType = LocalDateTime.class),
            @Result(property = "endTime", column = "end_time", javaType = LocalDateTime.class)
    })
    List<TimeSlot> selectTimeSlotsForGate(@Param("gateId") UUID gateId);

    @ResultMap("gateResults")
    @Select("SELECT id, number FROM gates WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Gate get(@Param("id") UUID id);

    @Update("UPDATE gates SET number = #{number}, terminal_id = #{terminal.id} WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Gate update(Gate gate);

    @Delete("DELETE FROM gates WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Gate remove(@Param("id") UUID id);

    @Delete("DELETE FROM gates")
    void clear();
}

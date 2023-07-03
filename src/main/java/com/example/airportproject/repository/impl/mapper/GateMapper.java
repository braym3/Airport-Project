package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.Terminal;
import org.apache.ibatis.annotations.*;

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
            @Result(property = "terminal", column = "id", javaType = Terminal.class, one = @One(select = "com.example.airportproject.repository.impl.mapper.TerminalMapper.selectTerminalForGate")),
            @Result(property = "schedule", column = "id", many = @Many(select = "com.example.airportproject.repository.impl.mapper.TimeSlotMapper.selectTimeSlotsForGate"))
    })
    List<Gate> getAll();

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

package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Runway;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface RunwayMapper {
    @Insert(
            "INSERT INTO runways (number) VALUES (#{number})")
    void create(Runway runway);

    @Select("SELECT id, number FROM runways")
    @Results(id="runwayResults", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "number", column = "number"),
            @Result(property = "schedule", column = "id", javaType = List.class, many = @Many(select = "com.example.airportproject.repository.impl.mapper.TimeSlotMapper.selectTimeSlotsForRunway"))
    })
    List<Runway> getAll();

    @ResultMap("runwayResults")
    @Select("SELECT id, number FROM runways WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Runway get(@Param("id") UUID id);

    @Select("SELECT id, number FROM runways WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "number", column = "number"),
    })
    Runway getRunwayWithoutSchedule(@Param("id") UUID id);

    @Update("UPDATE runways SET number = #{number} WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Runway update(Runway gate);

    @Delete("DELETE FROM runways WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    void remove(@Param("id") UUID id);

    @Delete("DELETE FROM runways")
    void clear();
}

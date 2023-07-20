package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.model.enums.Entity;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;
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
}

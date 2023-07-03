package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Flight;
import com.example.airportproject.model.ImpactEvent;
import com.example.airportproject.model.TimeSlot;
import com.example.airportproject.typehandler.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
public interface ImpactEventMapper {
    @Insert(
            "INSERT INTO impact_events (type, description, probability) VALUES (#{type}, #{description}, #{probability})")
    void create(ImpactEvent impactEvent);

    @Select("SELECT id, type, description, probability FROM impact_events")
    @Results(id = "impactEventResults", value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "type", column = "type", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "probability", column = "probability", javaType = BigDecimal.class)
    })
    List<ImpactEvent> getAll();

    @ResultMap("impactEventResults")
    @Select("SELECT id, type, description, probability FROM impact_events WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    ImpactEvent get(@Param("id") UUID id);

    @Delete("DELETE FROM impact_events WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    ImpactEvent remove(@Param("id") UUID id);

    @Delete("DELETE FROM impact_events")
    void clear();
}

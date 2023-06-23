package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Gate;
import com.example.airportproject.typehandler.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface GateMapper {
    @Insert(
            "INSERT INTO gates (number, terminal_id) VALUES (#{number}, #{terminal_id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler})")
    void create(Gate gate);

    @Results(id = "gateResults")
    @ConstructorArgs({
            @Arg(column = "id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class, id = true),
            @Arg(column = "number", javaType = Integer.class),
            @Arg(column = "terminal_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class, id = true)
    })
    @Select("SELECT * FROM gates")
    List<Gate> getAll();

    @ResultMap("gateResults")
    @Select("SELECT * FROM gates WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Gate get(@Param("id") UUID id);

    @Update("UPDATE gates SET number = #{number}, terminal_id = #{terminalId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler} WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Gate update(@Param("id") UUID id, @Param("number") Integer number, @Param("terminalId") UUID terminalId);

    @Delete("DELETE FROM gates WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Gate remove(@Param("id") UUID id);

    @Delete("DELETE FROM gates")
    void clear();
}

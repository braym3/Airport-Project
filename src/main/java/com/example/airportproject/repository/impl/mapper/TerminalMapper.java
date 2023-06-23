package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Terminal;
import com.example.airportproject.typehandler.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface TerminalMapper {
    @Insert(
            "INSERT INTO terminals (number) VALUES (#{number})")
    void create(Terminal terminal);

    @Results(id = "terminalResults")
    @ConstructorArgs({
            @Arg(column = "id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class, id = true),
            @Arg(column = "number", javaType = Integer.class),
    })
    @Select("SELECT * FROM terminals")
    List<Terminal> getAll();

    @ResultMap("terminalResults")
    @Select("SELECT * FROM terminals WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Terminal get(@Param("id") UUID id);

    @Update("UPDATE terminals SET number = #{number} WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Terminal update(@Param("id") UUID id, @Param("number") Integer number);

    @Delete("DELETE FROM terminals WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Terminal remove(@Param("id") UUID id);

    @Delete("DELETE FROM terminals")
    void clear();
}

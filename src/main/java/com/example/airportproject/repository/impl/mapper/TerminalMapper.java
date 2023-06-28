package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.Terminal;
import com.example.airportproject.typehandler.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.UUID;

@Mapper
public interface TerminalMapper {
    @Insert(
            "INSERT INTO terminals (id, number) VALUES (#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}, #{number})")
    void create(Terminal terminal);

//    @Results(id = "terminalResults", value = {
//            @Result(property = "id", column = "id", javaType = UUID.class, jdbcType = JdbcType.OTHER),
//            @Result(property = "number", column = "number", javaType = Integer.class),
//            @Result(property = "gates", column = "id", many = @Many(select = "selectGatesForTerminal"))
//    })
//    @Select("SELECT id, number FROM terminals")
//    List<Terminal> getAll();
//
//    @Results(id = "selectGatesForTerminal", value = {
//            @Result(column = "id", javaType = UUID.class, jdbcType = JdbcType.OTHER),
//            @Result(column = "number", javaType = Integer.class)
//    })
//    @Select("SELECT id, number FROM gates WHERE terminal_id = #{terminalId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
//    List<Gate> selectGatesForTerminal(@Param("terminalId") UUID terminalId);

    @Results(id = "terminalResults", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "number", column = "number")
    })
    @Select("SELECT id, number FROM terminals")
    List<Terminal> getAll();

    @Results(id = "gateResults", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "number", column = "number")
    })
    @Select("SELECT id, number FROM gates WHERE terminal_id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    List<Gate> getGates(UUID id);

//    @Results(id = "terminalResults", value = {
//            @Result(property = "id", column = "id"),
//            @Result(property = "number", column = "number"),
//            @Result(property = "gates", column = "id", javaType = List.class, many = @Many(select = "selectGatesForTerminal"))
//    })
//    @Select("SELECT id, number FROM terminals")
//    List<Terminal> getAll();
//    @Results(value = {
//            @Result(column = "id", property = "id"),
//            @Result(column = "number", property = "number")
//    })
//    @Select("SELECT id, number FROM gates WHERE terminal_id = #{terminalId}")
//    List<Gate> selectGatesForTerminal(@Param("terminalId") UUID terminalId);

    @ResultMap("terminalResults")
    @Select("SELECT id, number FROM terminals WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Terminal get(@Param("id") UUID id);

    @Update("UPDATE terminals SET number = #{number} WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Terminal update(@Param("id") UUID id, @Param("number") Integer number);

    @Delete("DELETE FROM terminals WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Terminal remove(@Param("id") UUID id);

    @Delete("DELETE FROM terminals")
    void clear();
}

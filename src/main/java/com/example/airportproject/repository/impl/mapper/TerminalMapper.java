package com.example.airportproject.repository.impl.mapper;

import com.example.airportproject.model.Gate;
import com.example.airportproject.model.Terminal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.ResultMap;

import java.util.List;
import java.util.UUID;

@Mapper
public interface TerminalMapper {
    @Insert(
            "INSERT INTO terminals (id, number) VALUES (#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}, #{number})")
    void create(Terminal terminal);

    @Select("SELECT id, number FROM terminals")
    @Results(id="terminalResults", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "number", column = "number"),
            @Result(property = "gates", column = "id", javaType = List.class, many = @Many(select = "selectGatesForTerminal"))
    })
    List<Terminal> getAll();

    @Select("SELECT id, number FROM gates WHERE gates.terminal_id = #{terminalId, javaType=java.util.UUID, jdbcType=OTHER}")
    @Results(value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "number", column = "number"),
    })
    List<Gate> selectGatesForTerminal(@Param("terminalId") UUID terminalId);

    @Select("SELECT terminals.id, terminals.number FROM terminals JOIN gates ON terminals.id = gates.terminal_id WHERE gates.id = #{gateId, javaType=java.util.UUID, jdbcType=OTHER}")
    Terminal selectTerminalForGate(@Param("gateId") UUID gateId);

    @ResultMap("terminalResults")
    @Select("SELECT id, number FROM terminals WHERE id = #{id, jdbcType=OTHER}")
    Terminal get(@Param("id") UUID id);

    @Update("UPDATE terminals SET number = #{number} WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    Terminal update(@Param("id") UUID id, @Param("number") Integer number);

    @Delete("DELETE FROM terminals WHERE id = #{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UUIDTypeHandler}")
    void remove(@Param("id") UUID id);

    @Delete("DELETE FROM terminals")
    void clear();
}

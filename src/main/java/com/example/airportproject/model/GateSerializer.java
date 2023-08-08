package com.example.airportproject.model;

import com.example.airportproject.model.Gate;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GateSerializer extends JsonSerializer<Gate> {

    @Override
    public void serialize(Gate gate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", gate.getId().toString());
        jsonGenerator.writeNumberField("number", gate.getNumber());

        // serialize terminal if it's not null
        if (gate.getTerminal() != null){
            jsonGenerator.writeObjectFieldStart("terminal");
            jsonGenerator.writeStringField("id", gate.getTerminal().getId().toString());
            jsonGenerator.writeNumberField("number", gate.getTerminal().getNumber());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndObject();
    }
}
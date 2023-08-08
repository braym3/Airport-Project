package com.example.airportproject.model.enums;

public enum Entity {
    FLIGHT("Flight"),
    GATE("Gate"),
    TERMINAL("Terminal"),
    RUNWAY("Runway");

    private String name;

    Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

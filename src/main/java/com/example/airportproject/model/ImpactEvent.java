package com.example.airportproject.model;

import java.math.BigDecimal;
import java.util.UUID;

public class ImpactEvent {

    private UUID id;
    private String type;
    private String description;
    private BigDecimal probability;

    public ImpactEvent(String type, String description, BigDecimal probability) {
        this.type = type;
        this.description = description;
        this.probability = probability;
    }

    public ImpactEvent(UUID id, String type, String description, BigDecimal probability) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.probability = probability;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }
}

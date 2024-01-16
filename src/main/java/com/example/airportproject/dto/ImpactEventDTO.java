package com.example.airportproject.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ImpactEventDTO {
    private UUID id;
    private String type;
    private String description;
    private BigDecimal probability;
    private int minDuration, maxDuration;
    private String impacts;

    public ImpactEventDTO() {
    }

    public ImpactEventDTO(UUID id, String type, String description, BigDecimal probability, Integer minDuration, Integer maxDuration, String impacts) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.probability = probability;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.impacts = impacts;
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

    public int getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    public Integer getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
    }

    public String getImpacts() {
        return impacts;
    }

    public void setImpacts(String impacts) {
        this.impacts = impacts;
    }
}

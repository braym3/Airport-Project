package com.example.airportproject.model;

import com.example.airportproject.model.enums.Entity;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ImpactEvent {

    private UUID id;
    @NotNull
    private String type;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal probability;
    private int minDuration, maxDuration;
    @NotNull
    private Entity impacts;

    public ImpactEvent(@NotNull String type, @NotNull String description, @NotNull BigDecimal probability, int minDuration, int maxDuration, @NotNull Entity impacts) {
        this.type = type;
        this.description = description;
        this.probability = probability;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.impacts = impacts;
    }

    public ImpactEvent(UUID id, @NotNull String type, @NotNull String description, @NotNull BigDecimal probability, int minDuration, int maxDuration, @NotNull Entity impacts) {
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

    public @NotNull String getType() {
        return type;
    }

    public void setType(@NotNull String type) {
        this.type = type;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public @NotNull BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(@NotNull BigDecimal probability) {
        this.probability = probability;
    }

    public int getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public @NotNull Entity getImpacts() {
        return impacts;
    }

    public void setImpacts(@NotNull Entity impacts) {
        this.impacts = impacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImpactEvent that = (ImpactEvent) o;
        return getMinDuration() == that.getMinDuration() && getMaxDuration() == that.getMaxDuration() && Objects.equals(getId(), that.getId()) && Objects.equals(getType(), that.getType()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getProbability(), that.getProbability()) && getImpacts() == that.getImpacts();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getDescription(), getProbability(), getMinDuration(), getMaxDuration(), getImpacts());
    }

    @Override
    public String toString() {
        return "ImpactEvent{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", probability=" + probability +
                ", minDuration=" + minDuration +
                ", maxDuration=" + maxDuration +
                ", impacts=" + impacts +
                '}';
    }
}

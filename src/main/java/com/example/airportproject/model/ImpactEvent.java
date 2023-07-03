package com.example.airportproject.model;

import java.time.LocalDateTime;

public class ImpactEvent {
    private String type;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ImpactEvent(String type, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.type = type;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}

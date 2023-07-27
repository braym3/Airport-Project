package com.example.airportproject.model;

import java.time.LocalDateTime;
import java.util.List;

public interface Schedulable {
    List<TimeSlot> getSchedule();
    void setSchedule(List<TimeSlot> schedule);
    boolean isAvailableAtTimeSlot(LocalDateTime startTime, LocalDateTime endTime);
    void addTimeSlot(TimeSlot timeSlot);
    TimeSlot findClosestAvailableSlot(LocalDateTime startTime, LocalDateTime endTime);
    List<TimeSlot> getScheduleOfAvailability(LocalDateTime scheduleStartTime, LocalDateTime scheduleEndTime);
}

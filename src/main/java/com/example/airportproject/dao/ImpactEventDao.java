package com.example.airportproject.dao;

import com.example.airportproject.model.ImpactEvent;

import java.util.List;
import java.util.UUID;

public interface ImpactEventDao {
    List<ImpactEvent> getAll();
    ImpactEvent get(UUID id);
    ImpactEvent create(ImpactEvent impactEvent);
    ImpactEvent update(ImpactEvent impactEvent);
    ImpactEvent remove(UUID id);
}

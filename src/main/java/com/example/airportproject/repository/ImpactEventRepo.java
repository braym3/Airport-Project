package com.example.airportproject.repository;

import com.example.airportproject.model.ImpactEvent;

import java.util.List;
import java.util.UUID;

public interface ImpactEventRepo {
    ImpactEvent create(ImpactEvent impactEvent);

    List<ImpactEvent> getAll();

    ImpactEvent get(UUID id);

    ImpactEvent remove(UUID id);

    void clear();
}

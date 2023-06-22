package com.example.airportproject.repository;

import com.example.airportproject.model.Gate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Transactional
public interface GateRepository extends JpaRepository<Gate, UUID> {}

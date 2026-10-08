package com.cyberportal.cyberincidentportal.repository;

import com.cyberportal.cyberincidentportal.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
}

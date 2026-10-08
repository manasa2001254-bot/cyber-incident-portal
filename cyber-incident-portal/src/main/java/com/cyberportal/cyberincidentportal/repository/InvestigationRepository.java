package com.cyberportal.cyberincidentportal.repository;

import com.cyberportal.cyberincidentportal.model.Investigation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestigationRepository extends JpaRepository<Investigation, Long> {

    List<Investigation> findByIncidentId(Long incidentId);
}

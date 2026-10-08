package com.cyberportal.cyberincidentportal.service;

import com.cyberportal.cyberincidentportal.model.Incident;
import com.cyberportal.cyberincidentportal.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident createIncident(Incident incident) {
        return incidentRepository.save(incident);
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Optional<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id);
    }

    public void deleteIncident(Long id) {
        incidentRepository.deleteById(id);
    }
}

package com.cyberportal.cyberincidentportal.service;

import com.cyberportal.cyberincidentportal.model.Investigation;
import com.cyberportal.cyberincidentportal.repository.InvestigationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestigationService {

    private final InvestigationRepository investigationRepository;

    public InvestigationService(InvestigationRepository investigationRepository) {
        this.investigationRepository = investigationRepository;
    }

    public Investigation createInvestigation(Investigation investigation) {
        return investigationRepository.save(investigation);
    }

    public List<Investigation> getAllInvestigations() {
        return investigationRepository.findAll();
    }

    public List<Investigation> getInvestigationsByIncidentId(Long incidentId) {
        return investigationRepository.findByIncidentId(incidentId);
    }

    public void deleteInvestigation(Long id) {
        investigationRepository.deleteById(id);
    }
}

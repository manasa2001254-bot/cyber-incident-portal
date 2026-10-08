package com.cyberportal.cyberincidentportal.controller;

import com.cyberportal.cyberincidentportal.model.Investigation;
import com.cyberportal.cyberincidentportal.service.InvestigationService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class InvestigationController {

    private final InvestigationService investigationService;

    public InvestigationController(InvestigationService investigationService) {
        this.investigationService = investigationService;
    }

    @GetMapping("/incidents/{incidentId}/investigation")
    public String investigationForm(@PathVariable Long incidentId, Model model) {

        Investigation investigation = new Investigation();
        investigation.setIncidentId(incidentId);

        model.addAttribute("investigation", investigation);

        return "investigation";
    }

    @PostMapping("/incidents/{incidentId}/investigation/save")
    public String saveInvestigation(
            @PathVariable Long incidentId,
            @ModelAttribute Investigation investigation) {

        investigation.setIncidentId(incidentId);

        investigationService.createInvestigation(investigation);

        return "redirect:/incidents/" + incidentId;
    }
}

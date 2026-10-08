package com.cyberportal.cyberincidentportal.controller;

import com.cyberportal.cyberincidentportal.model.Incident;
import com.cyberportal.cyberincidentportal.service.IncidentService;
import com.cyberportal.cyberincidentportal.service.InvestigationService;
import com.cyberportal.cyberincidentportal.service.S3Service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class IncidentController {

    private static final String BUCKET_NAME =
            "cyber-incident-portal-evidence-2026";

    private final IncidentService incidentService;
    private final InvestigationService investigationService;
    private final S3Service s3Service;

    public IncidentController(
            IncidentService incidentService,
            InvestigationService investigationService,
            S3Service s3Service) {

        this.incidentService = incidentService;
        this.investigationService = investigationService;
        this.s3Service = s3Service;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/incidents";
    }

    @GetMapping("/incidents")
    public String incidents(Model model) {
        model.addAttribute(
                "incidents",
                incidentService.getAllIncidents()
        );
        return "incidents";
    }

    @GetMapping("/incidents/new")
    public String newIncident(Model model) {
        model.addAttribute("incident", new Incident());
        return "report-incident";
    }

    @PostMapping("/incidents/save")
    public String saveIncident(
            @ModelAttribute Incident incident,
            @RequestParam(
                    value = "evidenceFile",
                    required = false
            ) MultipartFile evidenceFile,
            RedirectAttributes redirectAttributes) {

        Incident savedIncident =
                incidentService.createIncident(incident);

        if (evidenceFile != null && !evidenceFile.isEmpty()) {
            try {
                String originalName =
                        evidenceFile.getOriginalFilename();

                if (originalName == null || originalName.isBlank()) {
                    originalName = "evidence-file.pdf";
                }

                String safeName = originalName.replaceAll(
                        "[^a-zA-Z0-9._-]", "_"
                );

                String objectKey =
                        "incident-evidence/incident-"
                        + savedIncident.getId()
                        + "-"
                        + UUID.randomUUID()
                        + "-"
                        + safeName;

                String contentType = evidenceFile.getContentType();

                if (contentType == null || contentType.isBlank()) {
                    contentType = "application/octet-stream";
                }

                s3Service.uploadFile(
                        BUCKET_NAME,
                        objectKey,
                        evidenceFile.getBytes(),
                        contentType
                );

                savedIncident.setEvidenceKey(objectKey);

                incidentService.createIncident(savedIncident);

                redirectAttributes.addFlashAttribute(
                        "successMessage",
                        "Incident and evidence saved successfully."
                );

            } catch (Exception e) {
                System.err.println(
                        "Evidence upload failed: " + e.getMessage()
                );

                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "Incident saved, but evidence upload failed. "
                        + "Check the application terminal."
                );
            }
        } else {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Incident saved. No evidence file was selected."
            );
        }

        return "redirect:/incidents";
    }

    @GetMapping("/incidents/{id}")
    public String viewIncident(
            @PathVariable Long id,
            Model model) {

        Incident incident = incidentService
                .getIncidentById(id)
                .orElse(null);

        if (incident == null) {
            return "redirect:/incidents";
        }

        model.addAttribute("incident", incident);

        model.addAttribute(
                "investigations",
                investigationService.getInvestigationsByIncidentId(id)
        );

        return "incident-details";
    }

    @PostMapping("/incidents/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Incident incident = incidentService
                .getIncidentById(id)
                .orElse(null);

        if (incident != null) {
            incident.setStatus(status);
            incidentService.createIncident(incident);
        }

        return "redirect:/incidents/" + id;
    }
}

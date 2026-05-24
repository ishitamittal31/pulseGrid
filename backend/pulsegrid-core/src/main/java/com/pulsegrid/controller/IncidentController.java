package com.pulsegrid.controller;

import com.pulsegrid.domain.Acknowledgement;
import com.pulsegrid.domain.Incident;
import com.pulsegrid.dto.IncidentRequest;
import com.pulsegrid.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incidents")
public class IncidentController {

    @Autowired
    IncidentService incidentService;

    @PostMapping
    public Incident createIncident(@RequestBody IncidentRequest incidentRequest) {
        return incidentService.createIncident(incidentRequest);
    }


}

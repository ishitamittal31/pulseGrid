package com.pulsegrid.controller;

import com.pulsegrid.service.AcknowledgementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incidents")
public class AcknowledgementController {

    @Autowired
    AcknowledgementService acknowledgementService;

    @PostMapping("/{incidentId}/ack/{responderId}")
    public String acknowledge(@PathVariable Long incidentId, @PathVariable Long responderId) {
        return acknowledgementService.acknowledge(incidentId, responderId);
    }


}

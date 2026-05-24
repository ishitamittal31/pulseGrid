package com.pulsegrid.service;

import com.pulsegrid.dispatch.event.IncidentCreatedEvent;
import com.pulsegrid.domain.Incident;
import com.pulsegrid.dto.IncidentRequest;
import com.pulsegrid.enums.IncidentStatus;
import com.pulsegrid.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IncidentService {

    @Autowired
    IncidentRepository incidentRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public Incident createIncident(IncidentRequest incidentRequest) {
        Incident incident = new Incident();
        incident.setLocation(incidentRequest.getLocation());
        incident.setSeverity(incident.getSeverity());
        incident.setType(incidentRequest.getType());
        incident.setStatus(IncidentStatus.CREATED);
        incident.setCreatedAt(LocalDateTime.now());

        Incident saved = incidentRepository.save(incident);

        IncidentCreatedEvent event =
            new IncidentCreatedEvent(
                    saved.getId(),
                    saved.getType(),
                    saved.getSeverity(),
                    saved.getLocation()
            );
        kafkaTemplate.send("incident-events", event);
        return saved;
    }

}

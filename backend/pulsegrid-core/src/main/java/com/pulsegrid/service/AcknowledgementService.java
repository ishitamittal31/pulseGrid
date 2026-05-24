package com.pulsegrid.service;

import com.pulsegrid.dispatch.event.IncidentAssignedEvent;
import com.pulsegrid.domain.Incident;
import com.pulsegrid.domain.IncidentAssignment;
import com.pulsegrid.domain.Responder;
import com.pulsegrid.enums.IncidentStatus;
import com.pulsegrid.repository.IncidentAssignmentRepository;
import com.pulsegrid.repository.IncidentRepository;
import com.pulsegrid.repository.ResponderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AcknowledgementService {

    @Autowired
    IncidentAssignmentRepository incidentAssignmentRepository;

    @Autowired
    ResponderRepository responderRepository;

    @Autowired
    private IncidentRepository incidentRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public String acknowledge(Long incidentId, Long responderId) {

        Incident incident = incidentRepository.findByIdForUpdate(incidentId)
                .orElseThrow(() -> new RuntimeException("Incident not found"));


        boolean alreadyAssigned = incidentAssignmentRepository.existsByIncidentId(incidentId);
        if(alreadyAssigned || incident.getStatus() == IncidentStatus.ASSIGNED) {
            return "Incident already assigned";
        }

        Responder responder =
                responderRepository.findById(responderId)
                        .orElseThrow(() ->
                                new RuntimeException("Responder not found"));
        if (!responder.getIsAvailable()) {
            return "Responder is not available";
        }
        responder.setIsAvailable(false);
        incident.setStatus(IncidentStatus.ASSIGNED);
        responderRepository.save(responder);
        IncidentAssignment incidentAssignment = new IncidentAssignment();
        incidentAssignment.setAssignedAt(LocalDateTime.now());
        incidentAssignment.setIncidentId(incidentId);
        incidentAssignment.setResponderId(responderId);
        incidentAssignment.setStatus(IncidentStatus.ASSIGNED);
        incidentAssignmentRepository.save(incidentAssignment);

        kafkaTemplate.send("incident-assigned", new IncidentAssignedEvent(incidentId, responderId, LocalDateTime.now()));

        return "You are successfully assigned this incident" + responder.getName().toString();

    }

}

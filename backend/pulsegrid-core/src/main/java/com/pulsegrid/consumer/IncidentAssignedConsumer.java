package com.pulsegrid.consumer;

import com.pulsegrid.dispatch.event.IncidentAssignedEvent;
import com.pulsegrid.domain.Responder;
import com.pulsegrid.repository.ResponderRepository;
import com.pulsegrid.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class IncidentAssignedConsumer {

    @Autowired
    AuditLogService auditLogService;
    @Autowired
    private ResponderRepository responderRepository;

    @KafkaListener(topics = "incident-assigned", groupId = "incident-group")
    public void consume(IncidentAssignedEvent event) {
//        Responder responder = responderRepository.getById(event.getResponderId());
        System.out.println("Incident was assigned successfully");
        auditLogService.logIncidentAssignment(event);
    }

}

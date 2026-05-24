package com.pulsegrid.consumer;

import com.pulsegrid.dispatch.event.IncidentAssignedEvent;
import com.pulsegrid.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class IncidentAssignedConsumer {

    @Autowired
    AuditLogService auditLogService;

    @KafkaListener(topics = "incident-assigned", groupId = "incident-group")
    public void consume(IncidentAssignedEvent event) {
        System.out.println("Incident was assigned successfully");
        auditLogService.logIncidentAssignment(event);
    }

}

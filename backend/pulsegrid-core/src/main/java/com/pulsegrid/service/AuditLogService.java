package com.pulsegrid.service;

import com.pulsegrid.dispatch.event.IncidentAssignedEvent;
import com.pulsegrid.domain.AuditLog;
import com.pulsegrid.domain.IncidentAssignment;
import com.pulsegrid.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    @Autowired
    AuditLogRepository auditLogRepository;

    public void logIncidentAssignment(IncidentAssignedEvent event) {
        AuditLog auditLog = new AuditLog();
        auditLog.setCreatedAt(event.getAssignedAt());
        auditLog.setMessage("Incident assigned to responder" + event.getResponderId());
        auditLog.setIncidentId(event.getIncidentId());
        auditLog.setReponderId(event.getResponderId());
        auditLogRepository.save(auditLog);
    }

}


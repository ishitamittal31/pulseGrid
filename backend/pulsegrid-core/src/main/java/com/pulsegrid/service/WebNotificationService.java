package com.pulsegrid.service;

import com.pulsegrid.domain.Incident;
import com.pulsegrid.dto.RealTimeNotificationDto;
import com.pulsegrid.repository.IncidentRepository;
import com.pulsegrid.repository.ProcessedEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class WebNotificationService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    ProcessedEventRepository processedEventRepository;

    public void notifyResponder(
            Long responderId,
            Long incidentId
    ) {
//        if(processedEventRepository.existsById(eve))
        RealTimeNotificationDto dto = new RealTimeNotificationDto();
        dto.setIncidentId(incidentId);
//        Incident incident = incidentRepository.findById(incidentId);
//        dto.setLocation(incident.getLocation());
//        dto.setMessage("New incident awaiting to be assigned");
        System.out.println("sending notification" + responderId);
        simpMessagingTemplate.convertAndSend("topic/responder/" + responderId, dto);

    }

}

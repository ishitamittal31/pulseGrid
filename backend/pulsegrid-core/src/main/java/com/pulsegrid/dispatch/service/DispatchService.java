package com.pulsegrid.dispatch.service;

import com.pulsegrid.dispatch.event.IncidentCreatedEvent;
import com.pulsegrid.dispatch.event.ResponderNotificationEvent;
import com.pulsegrid.domain.Responder;
import com.pulsegrid.repository.ResponderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class DispatchService {

    @Autowired
    ResponderRepository responderRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void handleIncident(IncidentCreatedEvent event) {

        List<Responder> availableResponders = responderRepository.findByIsAvailableTrue();

        if(availableResponders.isEmpty()) {
//            System.out.println("No responders available");
//            return;
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "No responders available"
            );
        }

        for(Responder responder : availableResponders) {
            ResponderNotificationEvent notificationEvent = new ResponderNotificationEvent(
                    UUID.randomUUID().toString(),
                    event.getIncidentId(),
                    responder.getResponderId(),
                    responder.getName()
            );
            kafkaTemplate.send("responder-notifications", responder.getResponderId().toString(), notificationEvent);
            System.out.println("Dispatching responders for: " + responder.getName());

        }


        // next steps will go here:
        // 1. find available responders
        // 2. assign incident
        // 3. send notifications
    }

}

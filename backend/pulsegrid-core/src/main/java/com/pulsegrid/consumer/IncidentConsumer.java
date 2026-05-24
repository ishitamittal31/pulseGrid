package com.pulsegrid.consumer;

import com.pulsegrid.dispatch.event.IncidentCreatedEvent;
import com.pulsegrid.dispatch.service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class IncidentConsumer {

    @Autowired
    private DispatchService dispatchService;

    @KafkaListener(topics = "incident-events", groupId = "dispatch-group")
    public void consume(IncidentCreatedEvent event) {

        dispatchService.handleIncident(event);
    }
}

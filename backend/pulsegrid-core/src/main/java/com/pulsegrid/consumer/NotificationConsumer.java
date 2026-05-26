package com.pulsegrid.consumer;

import com.pulsegrid.dispatch.event.ResponderNotificationEvent;
import com.pulsegrid.domain.Incident;
import com.pulsegrid.domain.ProcessedEvent;
import com.pulsegrid.domain.Responder;
import com.pulsegrid.repository.IncidentRepository;
import com.pulsegrid.repository.ProcessedEventRepository;
import com.pulsegrid.repository.ResponderRepository;
import com.pulsegrid.service.EmailNotificationService;
import com.pulsegrid.service.WebNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
//import org.springframework.kafka.annotation.RetryableTopic;
//import org.springframework.kafka.annotation.DltHandler;
//import org.springframework.retry.annotation.Backoff;

@Component
public class NotificationConsumer {

    @Autowired
    private WebNotificationService webNotificationService;

    @Autowired
    private ProcessedEventRepository processedEventRepository;
    @Autowired
    private EmailNotificationService emailNotificationService;
    @Autowired
    private ResponderRepository responderRepository;
    @Autowired
    private IncidentRepository incidentRepository;


    @Transactional
    @RetryableTopic(attempts = "4", backOff = @BackOff(delay = 3000), dltTopicSuffix = "-dlt")
    @KafkaListener(topics = "responder-notifications", groupId = "notification-group")
    public void consume(ResponderNotificationEvent event) {

        if(processedEventRepository.existsById(event.getEventId())) {
            System.out.println("Duplicate event ignored");
            return;
        }

        System.out.println(
                "Sending notification to responder: "
                        + event.getResponderName()
        );//should add logger later

        webNotificationService.notifyResponder(event.getResponderId(), event.getIncidentId());
        Responder responder = responderRepository.findById(event.getResponderId())
                .orElseThrow(() -> new RuntimeException("Responder not found"));
        Incident incident = incidentRepository.findById(event.getIncidentId())
                .orElseThrow(() -> new RuntimeException("Incident not found"));
        emailNotificationService.sendEmail(
                responder.getEmailAddress(),
                "Emergency Alert",
                "Incident"+incident.getType()+" "+incident.getLocation()+" = "+incident.getStatus()
        );
        ProcessedEvent processedEvent = new ProcessedEvent();
        processedEvent.setEventId(event.getEventId());
        processedEvent.setProcessedAt(LocalDateTime.now());
        processedEventRepository.save(processedEvent); //race condition possible here

    }

    @DltHandler
    public void dlt(ResponderNotificationEvent event) {
        System.out.println("message moved to dlq" + event.getIncidentId());
    }


}

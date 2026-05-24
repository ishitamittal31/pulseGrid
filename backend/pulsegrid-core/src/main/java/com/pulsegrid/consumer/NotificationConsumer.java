package com.pulsegrid.consumer;

import com.pulsegrid.dispatch.event.ResponderNotificationEvent;
import com.pulsegrid.service.WebNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;
//import org.springframework.kafka.annotation.RetryableTopic;
//import org.springframework.kafka.annotation.DltHandler;
//import org.springframework.retry.annotation.Backoff;

@Component
public class NotificationConsumer {

    @Autowired
    private WebNotificationService webNotificationService;


    @RetryableTopic(attempts = "4", backOff = @BackOff(delay = 3000), dltTopicSuffix = "-dlt")
    @KafkaListener(topics = "responder-notifications", groupId = "notification-group")
    public void consume(ResponderNotificationEvent event) {
        System.out.println(
                "Sending notification to responder: "
                        + event.getResponderName()
        );

        webNotificationService.notifyResponder(event.getIncidentId(), event.getResponderId());
    }

    @DltHandler
    public void dlt(ResponderNotificationEvent event) {
        System.out.println("message moved to dlq" + event.getIncidentId());
    }


}

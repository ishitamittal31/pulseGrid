package com.pulsegrid.dispatch.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponderNotificationEvent {

    private String eventId;
    private Long incidentId;
    private Long responderId;
    private String responderName;
}

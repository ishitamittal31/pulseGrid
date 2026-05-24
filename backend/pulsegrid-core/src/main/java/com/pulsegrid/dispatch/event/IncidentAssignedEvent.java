package com.pulsegrid.dispatch.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentAssignedEvent {

    private Long incidentId;
    private Long responderId;
    private LocalDateTime assignedAt;

}

package com.pulsegrid.dispatch.event;

import com.pulsegrid.enums.IncidentSeverity;
import com.pulsegrid.enums.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//to not send entity directly
public class IncidentCreatedEvent {

    private Long incidentId;
    private String type;
    private IncidentSeverity severity;
    private Location location;
}

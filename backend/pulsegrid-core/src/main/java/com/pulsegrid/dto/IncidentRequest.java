package com.pulsegrid.dto;

import com.pulsegrid.enums.Location;
import lombok.Data;

@Data
public class IncidentRequest {
    private String type;
    private String severity;
    private Location location;
}

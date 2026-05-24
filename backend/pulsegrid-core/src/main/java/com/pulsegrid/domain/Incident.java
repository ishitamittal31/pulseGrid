package com.pulsegrid.domain;

import com.pulsegrid.enums.IncidentSeverity;
import com.pulsegrid.enums.IncidentStatus;
import com.pulsegrid.enums.Location;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private IncidentSeverity severity;
    private String type;
    private Location location;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status;

    private LocalDateTime createdAt;
}

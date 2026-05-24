package com.pulsegrid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealTimeNotificationDto {

    private Long incidentId;

    private String incidentType;

    private String severity;

    private String location;

    private String message;

    private LocalDateTime createdAt;

    private Integer responseWindowSeconds;

}

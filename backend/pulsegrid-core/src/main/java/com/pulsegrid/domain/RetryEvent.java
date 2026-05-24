package com.pulsegrid.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RetryEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long retryId;

    private Long notificationId;
    private String reason;
    private LocalDateTime nextRetryAt;
    private int retryCount;

}

package org.kdt.mooluck.domain.alert.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AlertDTO {
    private int alertId;
    private int elderId;
    private Timestamp alertTime;
    private String alertType;
    private boolean resolved;

    // 생성자
    public AlertDTO(int alertId, int elderId, Timestamp alertTime, String alertType, boolean resolved) {
        this.alertId = alertId;
        this.elderId = elderId;
        this.alertTime = alertTime;
        this.alertType = alertType;
        this.resolved = resolved;
    }
} 
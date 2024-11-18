package org.kdt.mooluck.domain.alert;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class AlertSetting {
    private int userId;          // 사용자 ID
    private LocalTime alertTime;  // 알림 시간 (HH:mm 형식)

    // 기본 생성자
    public AlertSetting() {
    }

    // 모든 필드를 포함한 생성자
    public AlertSetting(int userId, LocalTime alertTime) {
        this.userId = userId;
        this.alertTime = alertTime;
    }
}

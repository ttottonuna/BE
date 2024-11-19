package org.kdt.mooluck.domain.alert.service;

import org.kdt.mooluck.domain.alert.dto.AlertDTO;

import java.util.List;

public interface AlertService {
    void createAlert(int elderId, String alertType);
    void resolveAlert(int alertId);
    List<AlertDTO> getAlertsByElderId(int elderId);

    // 버튼 클릭 시 기록 및 알림 해결 메서드
    void recordClick(int elderId);

}
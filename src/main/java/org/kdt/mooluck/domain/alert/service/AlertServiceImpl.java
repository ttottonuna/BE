package org.kdt.mooluck.domain.alert.service;

import org.kdt.mooluck.domain.alert.dto.AlertDTO;
import org.kdt.mooluck.domain.alert.AlertSetting;
import org.kdt.mooluck.domain.alert.mapper.AlertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    private final AlertMapper alertMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public AlertServiceImpl(AlertMapper alertMapper, SimpMessagingTemplate messagingTemplate) {
        this.alertMapper = alertMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void createAlert(int elderId, String alertType) {
        AlertDTO alert = new AlertDTO(0, elderId, new Timestamp(System.currentTimeMillis()), alertType, false);
        alertMapper.insertAlert(alert);

        // WebSocket을 통해 관리자에게 실시간 알림 전송
        messagingTemplate.convertAndSend("/topic/alerts", alert);
    }

    @Override
    public void resolveAlert(int alertId) {
        alertMapper.updateAlertStatus(alertId, true);

        // 알림이 해결되었음을 WebSocket을 통해 전송
        AlertDTO alert = alertMapper.getAlertById(alertId);
        if (alert != null) {
            alert.setResolved(true);
            messagingTemplate.convertAndSend("/topic/alerts", alert);
        }
    }

    @Override
    public List<AlertDTO> getAlertsByElderId(int elderId) {
        return alertMapper.getAlertsByElderId(elderId);
    }

    // 매일 1분 간격으로 설정된 알림 시간과 현재 시간 비교
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void checkAlertTimes() {
        List<AlertSetting> settings = alertMapper.getAlertSettings(); // 사용자 알림 시간 목록을 가져옴
        LocalTime now = LocalTime.now();

        for (AlertSetting setting : settings) {
            if (now.truncatedTo(ChronoUnit.MINUTES).equals(setting.getAlertTime())) {
                sendUserAlert(setting.getUserId(), "물 주세요", "밥 주세요");
            }
        }
    }

    private void sendUserAlert(int userId, String... messages) {
        for (String message : messages) {
            messagingTemplate.convertAndSend("/topic/user-alerts/" + userId, message);
        }
    }

    @Override
    public void recordClick(int elderId) {
        // 버튼 클릭 시간 기록
        AlertDTO alert = new AlertDTO(0, elderId, new Timestamp(System.currentTimeMillis()), "버튼 클릭 기록", true);
        alertMapper.insertAlert(alert);

        // 만약 elderId와 연결된 활성화된 알림이 있다면 해제
        AlertDTO activeAlert = alertMapper.getActiveAlertByElderId(elderId);
        if (activeAlert != null && !activeAlert.isResolved()) {
            resolveAlert(activeAlert.getAlertId());
        }

        // 실시간으로 관리자에게 버튼 클릭이 기록되었음을 WebSocket으로 전송
        messagingTemplate.convertAndSend("/topic/admin-alerts", alert);
    }
}

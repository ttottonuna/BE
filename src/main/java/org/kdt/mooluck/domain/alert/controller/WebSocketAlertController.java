package org.kdt.mooluck.domain.alert.controller;

import org.kdt.mooluck.domain.alert.dto.AlertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketAlertController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketAlertController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 사용자로부터 /app/send-alert 경로로 수신된 메시지를 /topic/admin-alerts로 전송
    @MessageMapping("/send-alert")
    public void handleAlertMessage(AlertDTO alertDTO) {
        // 메시지를 관리자 구독 경로로 전송
        messagingTemplate.convertAndSend("/topic/admin-alerts", alertDTO);
    }
}

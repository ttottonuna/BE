package org.kdt.mooluck.domain.alert.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kdt.mooluck.domain.alert.AlertSetting;
import org.kdt.mooluck.domain.alert.dto.AlertDTO;

import java.util.List;

@Mapper
public interface AlertMapper {

    void insertAlert(AlertDTO alert);
    void updateAlertStatus(int alertId, boolean resolved);
    AlertDTO getAlertById(int alertId);
    List<AlertDTO> getAlertsByElderId(int elderId);

    // 특정 elderId에 대한 활성화된 알람 가져오기
    AlertDTO getActiveAlertByElderId(int elderId);

    List<AlertSetting> getAlertSettings();
}

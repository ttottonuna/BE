package org.kdt.mooluck.domain.watermanagement.service;

import org.kdt.mooluck.domain.watermanagement.dto.WaterManagementDTO;

public interface WaterManagementService {
    // interaction_id로 water_management 데이터 조회
    WaterManagementDTO getWaterDataByinteraction_id(int interaction_id);

    // water_count 증가
    void incrementWaterCount(int interaction_id);
}

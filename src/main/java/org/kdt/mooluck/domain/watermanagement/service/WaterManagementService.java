package org.kdt.mooluck.domain.watermanagement.service;

import org.kdt.mooluck.domain.watermanagement.dto.WaterManagementDTO;

public interface WaterManagementService {
    WaterManagementDTO getWaterDataByinteraction_id(int interaction_id);
    void incrementWaterCount(int interaction_id);
}

package org.kdt.mooluck.domain.watermanagement.service;


import org.kdt.mooluck.domain.watermanagement.dto.WaterManagementDTO;
import org.kdt.mooluck.domain.watermanagement.mapper.WaterManagementMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WaterManagementServiceImpl implements WaterManagementService {

    private final WaterManagementMapper waterManagementMapper;

    public WaterManagementServiceImpl(WaterManagementMapper waterManagementMapper) {
        this.waterManagementMapper = waterManagementMapper;
    }

    @Override
    @Transactional
    public void incrementWaterCount(int interaction_id) {
        System.out.println("Updating interaction table for interaction_id: " + interaction_id);
        waterManagementMapper.incrementWaterCount(interaction_id);
        System.out.println("Updating water_management table for interaction_id: " + interaction_id);
        waterManagementMapper.incrementWaterTableCountAndcreated_at(interaction_id);
    }

    @Override
    public WaterManagementDTO getWaterDataByinteraction_id(int interaction_id) {
        return waterManagementMapper.getWaterDataByinteraction_id(interaction_id);
    }
}
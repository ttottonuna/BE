package org.kdt.mooluck.domain.watermanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kdt.mooluck.domain.watermanagement.dto.WaterManagementDTO;

@Mapper
public interface WaterManagementMapper {
    void incrementWaterCount(@Param("interaction_id") int interaction_id);
    void incrementWaterTableCountAndcreated_at(@Param("interaction_id") int interaction_id);
    WaterManagementDTO getWaterDataByinteraction_id(@Param("interaction_id") int interaction_id);
}

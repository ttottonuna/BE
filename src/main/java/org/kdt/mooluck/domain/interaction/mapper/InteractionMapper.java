package org.kdt.mooluck.domain.interaction.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InteractionMapper {
    void insertIfNotExists(@Param("elderId") int elderId);
    void incrementPetCount(@Param("elderId") int elderId);
    void incrementWaterCount(@Param("elderId") int elderId);
    void PreviousDayData();
    void resetCounts();
}

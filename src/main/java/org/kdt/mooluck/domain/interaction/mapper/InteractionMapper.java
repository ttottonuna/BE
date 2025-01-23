package org.kdt.mooluck.domain.interaction.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InteractionMapper {
    // 00시에 resetCounts로 row 추가되지 않았을 경우 행 추가
    void insertIfNotExists(@Param("elderId") int elderId);

    // 특정 elder의 pet_count 증가
    void incrementPetCount(@Param("elderId") int elderId);

    // 특정 elder의 water_count 증가
    void incrementWaterCount(@Param("elderId") int elderId);

    void PreviousDayData();
    void resetCounts();
}

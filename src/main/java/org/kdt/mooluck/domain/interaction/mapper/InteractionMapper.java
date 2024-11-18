package org.kdt.mooluck.domain.interaction.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InteractionMapper {
    // 특정 elder의 pet_count 증가
    void incrementPetCount(@Param("elderId") int elderId);
}

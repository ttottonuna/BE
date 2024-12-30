package org.kdt.mooluck.domain.petmanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kdt.mooluck.domain.petmanagement.dto.PetManagementDTO;

@Mapper
public interface PetManagementMapper {
    // pet_management 데이터 조회
    PetManagementDTO getPetDataByInteractionId(@Param("interaction_id") int interactionId);

    // interaction 테이블의 pet_count 증가
    void incrementInteractionPetCount(@Param("interaction_id") int interactionId);

    // pet_management 테이블의 pet_count와 created_at 업데이트
    void incrementPetTableCountAndCreatedAt(@Param("interaction_id") int interactionId);
}

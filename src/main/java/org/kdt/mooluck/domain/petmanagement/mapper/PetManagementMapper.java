package org.kdt.mooluck.domain.petmanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kdt.mooluck.domain.petmanagement.dto.PetManagementDTO;

@Mapper
public interface PetManagementMapper {
    PetManagementDTO getPetDataByInteractionId(@Param("interaction_id") int interactionId);
    void incrementInteractionPetCount(@Param("interaction_id") int interactionId);
    void incrementPetTableCountAndCreatedAt(@Param("interaction_id") int interactionId);
}

package org.kdt.mooluck.domain.petmanagement.service;

import org.kdt.mooluck.domain.petmanagement.dto.PetManagementDTO;

public interface PetManagementService {
    // interaction_id로 pet_management 데이터 조회
    PetManagementDTO getPetDataByInteractionId(int interactionId);

    // pet_count 증가
    void incrementPetCount(int interactionId);
}

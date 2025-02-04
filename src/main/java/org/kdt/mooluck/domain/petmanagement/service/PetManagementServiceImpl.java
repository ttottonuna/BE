package org.kdt.mooluck.domain.petmanagement.service;

import org.kdt.mooluck.domain.petmanagement.dto.PetManagementDTO;
import org.kdt.mooluck.domain.petmanagement.mapper.PetManagementMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetManagementServiceImpl implements PetManagementService {

    private final PetManagementMapper petManagementMapper;

    public PetManagementServiceImpl(PetManagementMapper petManagementMapper) {
        this.petManagementMapper = petManagementMapper;
    }

    @Override
    @Transactional
    public void incrementPetCount(int interactionId) {
        // interaction 테이블 업데이트
        System.out.println("Updating interaction table for interaction_id: " + interactionId);
        petManagementMapper.incrementInteractionPetCount(interactionId);

        // pet_management 테이블 업데이트
        System.out.println("Updating pet_management table for interaction_id: " + interactionId);
        petManagementMapper.incrementPetTableCountAndCreatedAt(interactionId);
    }

    @Override
    public PetManagementDTO getPetDataByInteractionId(int interactionId) {
        return petManagementMapper.getPetDataByInteractionId(interactionId);
    }
}

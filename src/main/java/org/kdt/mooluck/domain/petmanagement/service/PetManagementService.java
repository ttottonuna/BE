package org.kdt.mooluck.domain.petmanagement.service;

import org.kdt.mooluck.domain.petmanagement.dto.PetManagementDTO;

public interface PetManagementService {
    PetManagementDTO getPetDataByInteractionId(int interactionId);
    void incrementPetCount(int interactionId);
}

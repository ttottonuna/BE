package org.kdt.mooluck.domain.interaction.service;

import org.kdt.mooluck.domain.interaction.mapper.InteractionMapper;
import org.kdt.mooluck.domain.petmanagement.service.PetManagementService;
import org.kdt.mooluck.domain.petmanagement.service.PetManagementServiceImpl;
import org.kdt.mooluck.domain.watermanagement.service.WaterManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InteractionServiceImpl implements InteractionService {

    private final InteractionMapper interactionMapper;
    private final WaterManagementService waterManagementService;
    private final PetManagementService petManagementService;


    public InteractionServiceImpl(InteractionMapper interactionMapper, WaterManagementService waterManagementService, PetManagementService petManagementService) {
        this.interactionMapper = interactionMapper;
        this.waterManagementService = waterManagementService;
        this.petManagementService = petManagementService;

    }

    @Override
    @Transactional
    public void incrementPetCount(int elderId) {
        interactionMapper.incrementPetCount(elderId);
        petManagementService.incrementPetCount(elderId);

    }

    @Override
    @Transactional
    public void incrementWaterCount(int elderId)
    {
        interactionMapper.incrementWaterCount(elderId);
        waterManagementService.incrementWaterCount(elderId);
    }
}

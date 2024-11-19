package org.kdt.mooluck.domain.interaction.service;

import org.kdt.mooluck.domain.interaction.mapper.InteractionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InteractionServiceImpl implements InteractionService {

    private final InteractionMapper interactionMapper;

    public InteractionServiceImpl(InteractionMapper interactionMapper) {
        this.interactionMapper = interactionMapper;
    }

    @Override
    @Transactional
    public void incrementPetCount(int elderId) {
        interactionMapper.incrementPetCount(elderId);
    }

    @Override
    @Transactional
    public void incrementWaterCount(int elderId) { // 새로운 메서드 구현
        interactionMapper.incrementWaterCount(elderId);
    }
}

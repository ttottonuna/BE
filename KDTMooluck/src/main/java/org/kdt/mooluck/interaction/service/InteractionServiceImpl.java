package org.kdt.mooluck.interaction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kdt.mooluck.interaction.dto.InteractionDTO;
import org.springframework.stereotype.Service;
import org.kdt.mooluck.interaction.mapper.InteractionMapper;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class InteractionServiceImpl {

    private final InteractionMapper interactionMapper;

    @Override
    public List<InteractionDTO> getInteractionList(Integer elderId){
        return interactionMapper.getInteractionList(elderId);
    }
}

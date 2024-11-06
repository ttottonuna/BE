package org.kdt.mooluck.interaction.service;

import org.kdt.mooluck.interaction.dto.InteractionDTO;

import java.util.List;

public interface InteractionService {
    List<InteractionDTO> getInteractionList(Integer elderId);
}

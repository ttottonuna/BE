package org.kdt.mooluck.interaction.mapper;

import org.kdt.mooluck.interaction.dto.InteractionDTO;

import java.util.List;

public interface InteractionMapper {
    // 1. 노인 id에 대한 list 출력
    List<InteractionDTO> getInteractionList(Integer elderId);
}

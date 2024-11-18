package org.kdt.mooluck.domain.elder.service;

import org.kdt.mooluck.domain.elder.dto.ElderDTO;

public interface ElderService {
    //boolean validateMember(ElderDTO elderDto);
    String getMemberByMemberId(ElderDTO elderDTO);
}

package org.kdt.mooluck.elder.service;

import org.kdt.mooluck.elder.dto.ElderDTO;

public interface ElderService {
    //boolean validateMember(ElderDTO elderDto);
    String getMemberByMemberId(ElderDTO elderDTO);
}

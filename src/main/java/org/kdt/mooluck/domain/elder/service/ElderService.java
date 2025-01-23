package org.kdt.mooluck.domain.elder.service;

import org.kdt.mooluck.domain.elder.dto.ElderDTO;

public interface ElderService {

    String getMemberByMemberId(ElderDTO elderDTO);
    boolean validateMember(ElderDTO elderDTO);

}

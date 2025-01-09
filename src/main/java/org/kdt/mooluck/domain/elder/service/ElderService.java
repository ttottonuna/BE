package org.kdt.mooluck.domain.elder.service;

import org.kdt.mooluck.domain.elder.dto.ElderDTO;

public interface ElderService {

    // Elder ID 조회
    String getMemberByMemberId(ElderDTO elderDTO);
    boolean validateMember(ElderDTO elderDTO);

}

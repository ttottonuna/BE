package org.kdt.mooluck.domain.admin.service;

import org.kdt.mooluck.domain.admin.dto.AgencyStaffDTO;
import org.kdt.mooluck.domain.admin.dto.AgencyTableDTO;
import org.kdt.mooluck.domain.admin.dto.ElderDTO;

import java.util.List;

public interface AgencyStaffService {
    void register(AgencyStaffDTO staff);

    String login(String email, String password);

    void logout(String token); // 로그아웃 메서드

    List<AgencyTableDTO> getMembersByStaffId(Long staffId);

    // elder 회원가입 (관리자 권한)
    void registerElder(ElderDTO elder);
}

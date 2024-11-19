package org.kdt.mooluck.domain.admin.service;

import org.kdt.mooluck.domain.admin.dto.AgencyStaffDTO;
import org.kdt.mooluck.domain.admin.dto.AgencyTableDTO;

public interface AgencyStaffService {
    void register(AgencyStaffDTO staff);

    String login(String email, String password);

    void logout(String token); // 로그아웃 메서드

    AgencyTableDTO getMemberById(Long elderId);
}

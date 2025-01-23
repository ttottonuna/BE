package org.kdt.mooluck.domain.admin.service;

import org.kdt.mooluck.domain.admin.dto.AgencyStaffDTO;
import org.kdt.mooluck.domain.admin.dto.AgencyTableDTO;
import org.kdt.mooluck.domain.admin.dto.ElderDTO;

import java.util.List;

public interface AgencyStaffService {
    void register(AgencyStaffDTO staff);

    String login(String email, String password);

    void logout(String token);

    List<AgencyTableDTO> getMembersByStaffId(Long staffId);

    void registerElder(ElderDTO elder);

    // elder 정보 수정 (관리자 권한)
    void updateElder(ElderDTO elder);

    // elder 삭제 (관리자 권한)
    void deleteElder(Long elderId);
}

package org.kdt.mooluck.domain.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kdt.mooluck.domain.admin.dto.AgencyStaffDTO;
import org.kdt.mooluck.domain.admin.dto.ElderDTO;


@Mapper
public interface AgencyStaffMapper {
    void insertStaff(AgencyStaffDTO staff);

    AgencyStaffDTO findByEmail(@Param("staff_email") String staff_email);

    // elder 회원가입 (관리자 권한)
    void insertElder(ElderDTO elder);
}

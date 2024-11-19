package org.kdt.mooluck.domain.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kdt.mooluck.domain.admin.dto.AgencyStaffDTO;


@Mapper
public interface AgencyStaffMapper {
    void insertStaff(AgencyStaffDTO staff);

    AgencyStaffDTO findByEmail(@Param("staff_email") String staff_email);
}

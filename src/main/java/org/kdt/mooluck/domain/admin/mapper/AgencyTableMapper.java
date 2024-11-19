package org.kdt.mooluck.domain.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kdt.mooluck.domain.admin.dto.AgencyTableDTO;

import java.util.List;

@Mapper
public interface AgencyTableMapper {
    List<AgencyTableDTO> getMembersByStaffId(@Param("staffId") Long staffId);
}
package org.kdt.mooluck.domain.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kdt.mooluck.domain.admin.dto.AgencyTableDTO;

import java.util.List;

@Mapper
public interface AgencyTableMapper {
    AgencyTableDTO getMemberByMemberId (AgencyTableDTO agencyTableDTO);
}

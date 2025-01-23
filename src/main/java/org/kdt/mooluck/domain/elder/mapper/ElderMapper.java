package org.kdt.mooluck.domain.elder.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kdt.mooluck.domain.elder.dto.ElderDTO;

@Mapper
public interface ElderMapper {
    String getMemberByMemberId(ElderDTO elderDTO);
    String getPasswordByAccount(String elderAccount);
}

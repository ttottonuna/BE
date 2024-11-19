package org.kdt.mooluck.domain.elder.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kdt.mooluck.domain.elder.dto.ElderDTO;

@Mapper
public interface ElderMapper {
    String getMemberByMemberId(ElderDTO elderDTO);
//    ElderDTO getMemberByMemberId(ElderDTO elderDto);
}

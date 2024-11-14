package org.kdt.mooluck.elder.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kdt.mooluck.elder.dto.ElderDTO;

import java.util.List;

@Mapper
public interface ElderMapper {
    ElderDTO getMemberByMemberId(ElderDTO elderDto);
}
